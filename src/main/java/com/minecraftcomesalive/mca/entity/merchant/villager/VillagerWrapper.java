package com.minecraftcomesalive.mca.entity.merchant.villager;

import com.minecraftcomesalive.mca.api.nbt.CANBT;
import com.minecraftcomesalive.mca.entity.EntityMCA;
import com.minecraftcomesalive.mca.entity.player.PlayerMCA;
import com.minecraftcomesalive.mca.inventory.EEquipmentSlotType;
import com.minecraftcomesalive.mca.items.ItemStackMCA;
import com.minecraftcomesalive.mca.util.DamageSourceMCA;
import com.minecraftcomesalive.mca.util.EHandMCA;
import com.minecraftcomesalive.mca.util.math.BlockPosMCA;
import com.minecraftcomesalive.mca.world.WorldMCA;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public abstract class VillagerWrapper extends VillagerEntity {
    public WorldMCA world;

    public VillagerWrapper(EntityType<? extends VillagerWrapper> entityType, World worldIn) {
        super(entityType, worldIn);
        this.world = WorldMCA.fromMC(worldIn);
    }

    // Implementation of methods not reliant on villager-specific information.
    @Override public final boolean hasCustomName() {
        return true;
    }

    // Superclass callers with renamed overriding methods
    @Override protected final void defineSynchedData() {
        super.defineSynchedData();
        initialize();
    }

    @Override public final void tick() {
        super.tick();
        onUpdate();
    }

    @Override
    protected final void actuallyHurt(DamageSource damageSrc, float damageAmount) {
        DamageSourceMCA wrappedSource = DamageSourceMCA.fromMC(damageSrc);
        damageAmount = beforeDamaged(wrappedSource, damageAmount);
        super.actuallyHurt(damageSrc, damageAmount);
        afterDamaged(wrappedSource, damageAmount);
    }

    @Override
    public final void swing(Hand hand) {
        this.startUsingItem(hand);
        super.swing(hand);
        swing(EHandMCA.fromMC(hand));
    }

    @Override
    public final boolean hasRestriction() {
        // no-op, skip EntityVillager's detaching homes which messes up MoveTowardsRestriction.
        return false;
    }

    @Override
    public final boolean doHurtTarget(Entity e) {
        return this.attack(EntityMCA.fromMC(e));
    }

    @Override
    public final void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        load(CANBT.fromMC(nbt));
    };

    @Override
    public final void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        save(CANBT.fromMC(nbt));
    };

    @Override
    protected final SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected final SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.GENERIC_HURT;
    }

    @Override
    public final ActionResultType mobInteract(PlayerEntity player, @Nonnull Hand hand) { // interact
        onRightClick(PlayerMCA.fromMC(player), EHandMCA.fromMC(hand));
        return ActionResultType.sidedSuccess(this.level.isClientSide);
    }

    @Override
    public final BlockPos getRestrictCenter() {
        return getHomePos().getMcPos();
    }

    @Override
    public final ItemStack getItemBySlot(EquipmentSlotType type) {
        return getEquipmentOfType(EEquipmentSlotType.fromMC(type)).getMcItemStack();
    }

    @Override
    public final SoundEvent getDeathSound() {
        return SoundEvents.PLAYER_DEATH;
    }

    @Override public final void die(DamageSource source) {
        handleDeath(DamageSourceMCA.fromMC(source));
    }

    @Override public final ITextComponent getDisplayName() {
        return new StringTextComponent(getNameForDisplay());
    }

    public final TranslationTextComponent getCareerName() {
        return (TranslationTextComponent) this.getTypeName();
    }

    public final VillagerProfession getProfession() {
        return this.getVillagerData().getProfession();
    }

    public final void setProfession(VillagerProfession profession) {
        this.setVillagerData(this.getVillagerData().setProfession(profession));
    }

    @Override
    public final ITextComponent getCustomName() {
        return new StringTextComponent(getVillagerName());
    }

    // Methods required to be overridden
    protected abstract void initialize();
    public abstract void onUpdate();
    public abstract void swing(EHandMCA hand);
    protected abstract void initializeAI();
    protected abstract float beforeDamaged(DamageSourceMCA source, float value);
    protected abstract void afterDamaged(DamageSourceMCA source, float value);
    public abstract ItemStackMCA getEquipmentOfType(EEquipmentSlotType type);
    @Override protected abstract void ageBoundaryReached();
    public abstract String getNameForDisplay();
    public abstract String getVillagerName();
    public abstract void handleDeath(DamageSourceMCA source);
    public abstract void onRightClick(PlayerMCA player, EHandMCA hand);
    public abstract boolean attack(EntityMCA e);
    public abstract void load(CANBT nbt);
    public abstract void save(CANBT nbt);
    public abstract BlockPosMCA getHomePos();
}
