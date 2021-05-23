package com.minecraftcomesalive.mca.data.entity;

import com.minecraftcomesalive.mca.api.nbt.CANBT;
import com.minecraftcomesalive.mca.entity.merchant.villager.EntityVillagerMCA;
import com.minecraftcomesalive.mca.world.WorldMCA;
import com.minecraftcomesalive.mca.world.storage.WorldSavedDataMCA;
import net.minecraft.nbt.CompoundNBT;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SaveVillagersData extends WorldSavedDataMCA {
    private static final String DATA_ID = "MCA-Villagers-V2";
    private final Map<String, CANBT> villagerData = new HashMap<>();

    public SaveVillagersData(String id) {
        super(id);
    }


    public static SaveVillagersData get(WorldMCA world) {
        SaveVillagersData data = (SaveVillagersData) world.loadData(SaveVillagersData.class, DATA_ID);
        if (data == null) {
            data = new SaveVillagersData(DATA_ID);
            world.setData(DATA_ID, data);
        }
        return data;
    }

    public void saveVillager(EntityVillagerMCA villager) {
        villagerData.put(villager.getUUID().toString(), villager.getVillagerParams());
        setDirty();
    }


    public void removeVillager(UUID uuid) {
        villagerData.remove(uuid.toString());
        setDirty();
    }

    public CANBT getVillagerByUUID(UUID uuid) {
        return villagerData.get(uuid.toString());
    }

    public Map<String, CANBT> getVillagerData(){
        return this.villagerData;
    }

    @Override
    public CANBT save(CANBT nbt) {
        villagerData.forEach(nbt::setTag);
        return nbt;
    }

    @Override
    public void load(CANBT nbt) {
        nbt.getKeySet().forEach((k) -> villagerData.put(k, nbt.getCompoundTag(k)));
    }

}
