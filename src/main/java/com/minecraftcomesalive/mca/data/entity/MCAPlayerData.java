package com.minecraftcomesalive.mca.data.entity;

import com.minecraftcomesalive.mca.api.nbt.CANBT;
import com.minecraftcomesalive.mca.core.Constants;
import com.minecraftcomesalive.mca.world.WorldMCA;
import com.minecraftcomesalive.mca.world.storage.WorldSavedDataMCA;
import net.minecraft.nbt.CompoundNBT;

import java.util.UUID;

public class MCAPlayerData extends WorldSavedDataMCA {
    public static final String PREFIX = "MCA-Player-V2-";

    private UUID spouseUUID = Constants.ZERO_UUID;
    private String spouseName = "";
    private boolean babyPresent = false;

    protected MCAPlayerData(String id) {
        super(id);
    }

    @Override
    public void load(CompoundNBT nbt) {

    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        return nbt;
    }

    public static MCAPlayerData get(WorldMCA world, UUID uuid) {
        return (MCAPlayerData) WorldSavedDataMCA.get(PREFIX, MCAPlayerData.class, world, uuid);
    }

    public boolean isMarried() {
        return !spouseUUID.equals(Constants.ZERO_UUID);
    }

    @Override
    public CANBT save(CANBT nbt) {
        nbt.setUUID("spouseUUID", spouseUUID);
        nbt.setString("spouseName", spouseName);
        nbt.setBoolean("babyPresent", babyPresent);
        return nbt;
    }

    @Override
    public void load(CANBT nbt) {
        spouseUUID = nbt.getUUID("spouseUUID");
        spouseName = nbt.getString("spouseName");
        babyPresent = nbt.getBoolean("babyPresent");
    }

    public void marry(UUID uuid, String name) {
        spouseUUID = uuid;
        spouseName = name;
        setDirty();
    }

    public void endMarriage() {
        spouseUUID = Constants.ZERO_UUID;
        spouseName = "";
        setDirty();
    }

    public void setBabyPresent(boolean value) {
        this.babyPresent = value;
        setDirty();
    }

    public void reset() {
        endMarriage();
        setBabyPresent(false);
        setDirty();
    }

}
