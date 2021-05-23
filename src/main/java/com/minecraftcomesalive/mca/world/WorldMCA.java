package com.minecraftcomesalive.mca.world;

import com.minecraftcomesalive.mca.MCAMod;
import com.minecraftcomesalive.mca.entity.EntityMCA;
import com.minecraftcomesalive.mca.entity.player.PlayerMCA;
import com.minecraftcomesalive.mca.world.storage.WorldSavedDataMCA;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class WorldMCA {
    private final World mcWorld;

    public final boolean isRemote;
    public final Random rand;

    private WorldMCA(World world) {
        this.mcWorld = world;
        this.isRemote = world.isClientSide;
        this.rand = world.random;
    }

    public static WorldMCA fromMC(World world) {
        return new WorldMCA(world);
    }

    public WorldSavedDataMCA loadData(Class<? extends WorldSavedData> clazz, String dataId) {
        DimensionSavedDataManager dm = ((ServerWorld) mcWorld).getDataStorage();
        return dm.computeIfAbsent(() -> {
            try {
                return (WorldSavedDataMCA)clazz.getDeclaredConstructor(String.class).newInstance(dataId);
            } catch (Exception e) {
                MCAMod.getLog().info(e);
                return null;
            }}, dataId);
    }

    public void setData(String dataId, WorldSavedData data) {
        DimensionSavedDataManager dm = ((ServerWorld) mcWorld).getDataStorage();
        dm.set(data);
    }

    public Optional<PlayerMCA> getPlayerEntityByUUID(UUID uuid) {
        PlayerEntity player = mcWorld.getPlayerByUUID(uuid);
        if (player != null) {
            return Optional.of(PlayerMCA.fromMC(player));
        } else {
            return Optional.empty();
        }
    }

    public Optional<EntityMCA> getEntityByUUID(UUID uuid) {
        return Optional.of(EntityMCA.fromMC(((ServerWorld)mcWorld).getEntity(uuid)));
    }

    public void spawnEntity(EntityMCA entity) {
        mcWorld.addFreshEntity(entity.getMcEntity());
    }
}
