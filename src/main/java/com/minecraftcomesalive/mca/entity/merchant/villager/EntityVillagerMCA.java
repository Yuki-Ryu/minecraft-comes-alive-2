package com.minecraftcomesalive.mca.entity.merchant.villager;

import com.minecraftcomesalive.mca.MCAMod;
import com.minecraftcomesalive.mca.api.API;
import com.minecraftcomesalive.mca.api.nbt.CANBT;
import com.minecraftcomesalive.mca.core.Config;
import com.minecraftcomesalive.mca.core.Constants;
import com.minecraftcomesalive.mca.core.Memories;
import com.minecraftcomesalive.mca.data.entity.MCAPlayerData;
import com.minecraftcomesalive.mca.data.entity.ParentPair;
import com.minecraftcomesalive.mca.data.entity.SaveVillagersData;
import com.minecraftcomesalive.mca.entity.EntityMCA;
import com.minecraftcomesalive.mca.entity.player.PlayerMCA;
import com.minecraftcomesalive.mca.enums.EAgeState;
import com.minecraftcomesalive.mca.enums.EDialogueType;
import com.minecraftcomesalive.mca.enums.EGender;
import com.minecraftcomesalive.mca.enums.EMoveState;
import com.minecraftcomesalive.mca.inventory.EEquipmentSlotType;
import com.minecraftcomesalive.mca.inventory.InventoryMCA;
import com.minecraftcomesalive.mca.items.ItemStackMCA;
import com.minecraftcomesalive.mca.pathfinding.PathNavigatorMCA;
import com.minecraftcomesalive.mca.util.DamageSourceMCA;
import com.minecraftcomesalive.mca.util.EHandMCA;
import com.minecraftcomesalive.mca.util.math.BlockPosMCA;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraft.pathfinding.PathNavigator;

import javax.annotation.Nullable;
import java.util.*;

public class EntityVillagerMCA extends VillagerWrapper implements IEntityAdditionalSpawnData {
    public static final DataParameter<CompoundNBT> VILLAGER_PARAMS = EntityDataManager.defineId(EntityVillagerMCA.class, DataSerializers.COMPOUND_TAG);
    public final InventoryMCA inventory;

    // Non-persistent
    private float swingProgressTicks;
    private int startingAge;

    // Client-side fields
    private Vector3f renderOffset;

    public EntityVillagerMCA(EntityType<? extends EntityVillagerMCA> type, World world) {
        super(type, world);
        inventory = new InventoryMCA(EntityMCA.fromMC(this), 27);
    }

    @Override
    public void load(CANBT nbt) {
        setVillagerParams(nbt.getCompoundTag("params"));

        this.startingAge = nbt.getInteger("startingAge");
        setAge(nbt.getInteger("Age"));
        inventory.load(nbt.getList("inventory"));
    }

    @Override
    public void save(CANBT nbt) {
        nbt.setTag("params", getVillagerParams());
        nbt.setInteger("startingAge", this.startingAge);
        nbt.setList("inventory", inventory.save());
    }

    @Override
    public BlockPosMCA getHomePos() {
        return getVillagerParams().getBPosM("homePos");
    }

    public void setHomePos(BlockPosMCA value) {
        getVillagerParams().setBPosM("homePos", value);
    }

    @Override
    public float beforeDamaged(DamageSourceMCA source, float amount) {
        if (this.getProfession() == MCAMod.PROFESSION_GUARD.get()) {
            amount /= 2; // Guards take half damage
        }
        return amount;
    }

    @Override
    public void afterDamaged(DamageSourceMCA source, float amount) {
        source.getPlayer().ifPresent(e -> {
            e.sendMessage(MCAMod.localize("villager.hurt"));
        });

        if (source.isZombie() && Config.enableInfection && getRandom().nextFloat() < Config.infectionChance / 100) {
            setIsInfected(true);
        }
    }

    @Override
    public ItemStackMCA getEquipmentOfType(EEquipmentSlotType type) {
        //TODO clean this up
        return ItemStackMCA.fromMC(inventory.getBestArmorOfType(type.getMcType()));
    }

    @Override
    protected void initialize() {
        this.entityData.define(VILLAGER_PARAMS, new CompoundNBT());
        this.setTexture("");
        this.setSilent(true);
    }

    @Override
    public void onUpdate() {
    }

    @Override
    public void swing(EHandMCA hand) {
        if (!getIsSwinging() || swingProgressTicks >= 4 || swingProgressTicks < 0) {
            swingProgressTicks = -1;
            setIsSwinging(true);
        }
    }

    @Override
    protected void initializeAI() {

    }

    @Override
    protected void ageBoundaryReached() {
        setAgeState(EAgeState.ADULT);

        // Notify player parents of the age up and set correct dialogue type.
        EntityMCA[] parents = getParents().getBothParentEntities(this.world);
        Arrays.stream(parents).forEach(e -> e.asPlayer().ifPresent(p -> {
            getMemoriesForPlayer(p).setDialogueType(EDialogueType.ADULT);
            p.sendMessage(MCAMod.localize("notify.child.grownup", this.getVillagerName()));
        }));

        // Change profession away from child for villager children.
        if (getProfession() == MCAMod.PROFESSION_CHILD.get()) {
            setProfession(API.randomProfession().getMcProfession());
        }
    }

    @Override
    public void handleDeath(DamageSourceMCA cause) {
        // Play zombie death sound when infected.
        if (getIsInfected()) {
            this.playSound(SoundEvents.ZOMBIE_DEATH, this.getSoundVolume(), this.getVoicePitch());
        }

        // Notify spouse and reset marriage
        if (isMarried()) {
            Optional<EntityVillagerMCA> spouse = getSpouseEntity();
            MCAPlayerData playerData = MCAPlayerData.get(world, getSpouseUUID());

            if (spouse.isPresent()) {
                this.setSpouse(null);
            } else if (playerData != null) {
                playerData.endMarriage();
                world.getPlayerEntityByUUID(getSpouseUUID()).ifPresent(p -> p.sendMessage(Color.fromLegacyFormat(TextFormatting.RED) + MCAMod.localize("notify.spousedied", getVillagerName(), cause.getCauseName(this))));
            }
        }

        // Notify parents
        ParentPair parents = getParents();
        parents.sendMessage(world, (TextFormatting.RED) + MCAMod.localize("notify.childdied", getVillagerName(), cause.getCauseName(this)));

        // Save so that the villager persists
        SaveVillagersData.get(world).saveVillager(this);

        // Write to log if configured
        if (Config.logVillagerDeaths) {
            MCAMod.log(String.format("Villager death: %s. Caused by: %s, UUID: %s", this.getVillagerName(), cause.getCauseName(this), this.getUUID().toString()));
        }

        // Drop everything.
        inventory.dropAllItems();
    }

    @Override
    public void onRightClick(PlayerMCA player, EHandMCA hand) {
        // open gui -> display buttons -> on click button, send click event to server
        // -> trigger interaction func with IInteraction on server.
    }

    @Override
    public String getNameForDisplay() {
        String professionName = getAgeState() == EAgeState.ADULT ? getCareerName().getString() : getAgeState().localizedName();
        String color = this.getProfession() == MCAMod.PROFESSION_GUARD.get() ? String.valueOf(TextFormatting.GREEN) :"";
        return String.format("%1$s%2$s%3$s (%4$s)", color, Config.villagerChatPrefix, getVillagerName(), professionName);
    }

    @Override
    public boolean attack(EntityMCA e) {
        return false;
    }

    public void setStartingAge(int value) {
        this.startingAge = value;
        setAge(value);
    }

    public void reset() {
        setMemories(CANBT.createNew());
        setHealth(20.0F);
        setSpouse(null);
        setBabyGender(EGender.UNASSIGNED);
    }

    public void goHome(PlayerMCA player) {
        if (getHomePos().equals(BlockPosMCA.ORIGIN)) {
            say(player, "interaction.gohome.fail");
        } else {
            PathNavigatorMCA nav = PathNavigatorMCA.fromMC(this.getNavigation());
            if (!nav.tryGoTo(getHomePos())) {
                randomTeleport(getHomePos().getX(), getHomePos().getY(), getHomePos().getZ(), false);
            }
            say(player, "interaction.gohome.success");
        }
    }

    public void setHomeSafely(PlayerMCA player) {
        if (randomTeleport(player.getPosX(), player.getPosY(), player.getPosZ(), true)) {
            say(player, "interaction.sethome.success");
            setHomePos(player.getPosition());
            restrictTo(player.getPosition().getMcPos(), 32);

            //TODO sleeping/find bed
        } else {
            say(player, "interaction.sethome.fail");
        }
    }

    public void say(PlayerMCA target, String phraseId, String... params) {
        ArrayList<String> paramList = new ArrayList<>();
        if (params != null) Collections.addAll(paramList, params);

        // Player is always first in params passed to localizer for say().
        paramList.add(0, target.getName());

        String chatPrefix = Config.villagerChatPrefix + getDisplayName().getVisualOrderText() + ": ";
        if (getIsInfected()) { // Infected villagers do not speak
            target.sendMessage(chatPrefix + "???");
            playSound(SoundEvents.ZOMBIE_AMBIENT, this.getSoundVolume(), this.getVoicePitch());
        } else {
            EDialogueType dialogueType = getMemoriesForPlayer(target).getDialogueType();
            target.sendMessage(chatPrefix + MCAMod.localize(dialogueType + "." + phraseId, params));
        }
    }

    /*******************************************************************************************************************
     *                                         Attribute getters and setters
     ******************************************************************************************************************/
    public CANBT getVillagerParams() {
        return CANBT.fromMC(entityData.get(VILLAGER_PARAMS));
    }

    public void setVillagerParams(CANBT params) {
        entityData.set(VILLAGER_PARAMS, params.getMcCompound());
    }

    public void updateAttribute(String key, Object value) {
        CANBT attrs = getVillagerParams();
        attrs.set(key, value);
        entityData.set(VILLAGER_PARAMS, attrs.getMcCompound());
    }

    public boolean hasBaby() { return getBabyGender() != EGender.UNASSIGNED; }
    public boolean isMarried() { return !getSpouseUUID().equals(CANBT.ConstantsMCA.ZERO_UUID); }
    public boolean isMarriedTo(UUID uniqueID) { return getSpouseUUID().equals(uniqueID); }
    public boolean playerIsParent(PlayerMCA player) { return getParents().isParent(player); }
    public boolean playerIsSpouse(PlayerMCA player) { return getSpouseUUID().equals(player.getUniqueID()); }

    public String getVillagerName() { return getVillagerParams().getString("name"); }
    public void setVillagerName(String value) {
        updateAttribute("name", value);
    }

    public String getTexture() { return getVillagerParams().getString("texture"); }
    public void setTexture(String value) {
        updateAttribute("texture", value);
    }

    public EGender getGender() { return EGender.byId(getVillagerParams().getInteger("genderId")); }
    public void setGender(EGender value) {
        updateAttribute("genderId", value.getId());
    }

    public float getGirth() { return getVillagerParams().getFloat("girth"); }
    public void setGirth(float value) {
        updateAttribute("girth", value);
    }

    public float getTallness() { return getVillagerParams().getFloat("tallness"); }
    public void setTallness(float value) {
        updateAttribute("tallness", value);
    }

    public EMoveState getMoveState() { return EMoveState.byId(getVillagerParams().getInteger("moveStateId")); }
    public void setMoveState(EMoveState value) {
        updateAttribute("moveStateId", value.getId());
    }

    public EAgeState getAgeState() { return EAgeState.byId(getVillagerParams().getInteger("ageStateId")); }
    public void setAgeState(EAgeState value) {
        updateAttribute("ageStateId", getId());
    }

    public String getSpouseName() { return getVillagerParams().getString("spouseName"); }
    public UUID getSpouseUUID() { return getVillagerParams().getUUID("spouseUUID"); }
    public void setSpouse(@Nullable EntityMCA entity) {
        if (entity == null) {
            updateAttribute("spouseName", "");
            updateAttribute("spouseUUID", Constants.ZERO_UUID);
        } else {
            updateAttribute("spouseName", entity.getName());
            updateAttribute("spouseUUID", entity.getUniqueID());
        }
    }

    public boolean getIsProcreating() { return getVillagerParams().getBoolean("isProcreating"); }
    public void setIsProcreating(boolean value) {
        updateAttribute("isProcreating", value);
    }

    public boolean getIsInfected() { return getVillagerParams().getBoolean("isInfected"); }
    public void setIsInfected(boolean value) {
        updateAttribute("isInfected", value);
    }

    public int getActiveChore() { return getVillagerParams().getInteger("activeChore"); }
    public void setActiveChore(int value) {
        updateAttribute("activeChore", value);
    }

    public boolean getIsSwinging() { return getVillagerParams().getBoolean("isSwinging"); }
    public void setIsSwinging(boolean value) {
        updateAttribute("isSwinging", value);
    }

    public EGender getBabyGender() { return EGender.byId(getVillagerParams().getInteger("babyGenderId")); }
    public void setBabyGender(EGender value) {
        updateAttribute("babyGenderId", value.getId());
    }

    public int getBabyAge() { return getVillagerParams().getInteger("babyAge"); }
    public void setBabyAge(int value) {
        updateAttribute("babyAge", value);
    }

    public UUID getChoreAssigningPlayer() { return getVillagerParams().getUUID("choreAssigningPlayer"); }
    public void setChoreAssigningPlayer(UUID uuid) {
        updateAttribute("choreAssigningPlayer", uuid);
    }

    public BlockPosMCA getBedPos() { return getVillagerParams().getBPosM("bedPos"); }
    public void setBedPos(BlockPosMCA pos) {
        updateAttribute("bedPos", pos);
    }

    public BlockPosMCA getWorkplacePos() { return getVillagerParams().getBPosM("workPos"); }
    public void setWorkplacePos(BlockPosMCA pos) {
        updateAttribute("workPos", pos);
    }

    public BlockPosMCA getHangoutPos() { return getVillagerParams().getBPosM("hangoutPos"); }
    public void setHangoutPos(BlockPosMCA pos) {
        updateAttribute("hangoutPos", pos);
    }

    public boolean getIsSleeping() { return getVillagerParams().getBoolean("isSleeping"); }
    public void setSleeping(boolean value) {
        updateAttribute("isSleeping", value);
    }

    public ParentPair getParents() { return ParentPair.fromNBT(getVillagerParams().getCompoundTag("parents")); }
    public void setParents(ParentPair value) {
        updateAttribute("parents", value.toNBT());
    }

    public UUID getFollowingPlayer() {
        return getVillagerParams().getUUID("followingPlayer");
    }

    public void setFollowingPlayer(PlayerMCA uuid) {
        updateAttribute("followingPlayer", uuid);
    }

    public CANBT getMemories() {
        return getVillagerParams().getCompoundTag("memories");
    }

    public void setMemories(CANBT cnbt) {
        updateAttribute("memories", cnbt);
    }

    public Memories getMemoriesForPlayer(PlayerMCA player) {
        Memories returnMemories = Memories.fromCNBT(this, getMemories().getCompoundTag(player.getUniqueID().toString()));
        if (returnMemories == null) {
            returnMemories = Memories.getNew(this, player.getUniqueID());
            setMemories(getMemories().setTag(player.getUniqueID().toString(), returnMemories.toCANBT()));
        }
        return returnMemories;
    }

    public void updateMemories(Memories memories) {
        CANBT nbt = getMemories();
        nbt.setTag(memories.getPlayerUUID().toString(), memories.toCANBT());
        setMemories(nbt);
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        System.out.println("Write spawn data");
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        System.out.println("Read spawn data");
    }

    private Optional<EntityVillagerMCA> getSpouseEntity() {
        Optional<EntityMCA> entity = this.world.getEntityByUUID(getSpouseUUID());
        if (entity.isPresent() && entity.get().getMcEntity() instanceof EntityVillagerMCA) {
            return Optional.of((EntityVillagerMCA)entity.get().getMcEntity());
        }
        return Optional.empty();
    }

    public int getEntityId() {
        return this.getId();
    }
}
