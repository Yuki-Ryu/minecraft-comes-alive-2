package com.minecraftcomesalive.mca.world.storage;

import com.minecraftcomesalive.mca.api.nbt.CANBT;
import com.minecraftcomesalive.mca.world.WorldMCA;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.storage.WorldSavedData;

import java.util.UUID;

public abstract class WorldSavedDataMCA extends WorldSavedData {
    protected WorldSavedDataMCA(String id) {
        super(id);
    }

    @Override
    public void load(CompoundNBT nbt) {
        load(CANBT.fromMC(nbt));
    }
    //@Override public void read(CompoundNBT nbt) {
    //    load(CANBT.fromMC(nbt));
    //}


    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        return save(CANBT.fromMC(nbt)).getMcCompound();
    }
    //@Override public CompoundNBT write(CompoundNBT compound) {
    //    return save(CANBT.fromMC(compound)).getMcCompound();
    //}

    public abstract CANBT save(CANBT canbt);
    public abstract void load(CANBT canbt);

    protected static WorldSavedDataMCA get(String prefix, Class<? extends WorldSavedDataMCA> clazz, WorldMCA world, UUID uuid) {
        String dataId = prefix + uuid.toString();
        WorldSavedDataMCA data = world.loadData(clazz, dataId);
        if (data == null) {
            try {
                data = clazz.getDeclaredConstructor(String.class).newInstance(dataId);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create new player data instance.", e);
            }

            world.setData(dataId, data);
        }

        return data;
    }
}
