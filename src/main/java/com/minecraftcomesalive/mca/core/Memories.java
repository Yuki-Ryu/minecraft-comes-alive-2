package com.minecraftcomesalive.mca.core;

import com.minecraftcomesalive.mca.api.nbt.CANBT;
import com.minecraftcomesalive.mca.entity.merchant.villager.EntityVillagerMCA;
import com.minecraftcomesalive.mca.enums.EDialogueType;

import java.util.UUID;

public class Memories {
    private int hearts;
    private UUID playerUUID;
    private int interactionFatigue;

    private int dialogueType;

    private EntityVillagerMCA villager;

    private Memories() {
        hearts = 0;
        playerUUID = CANBT.ConstantsMCA.ZERO_UUID;
        interactionFatigue = 0;
        dialogueType = EDialogueType.UNASSIGNED.getId();
    }

    public static Memories getNew(EntityVillagerMCA villager, UUID uuid) {
        Memories history = new Memories();
        history.villager = villager;
        history.playerUUID = uuid;
        history.interactionFatigue = 0;
        history.dialogueType = EDialogueType.ADULT.getId();
        return history;
    }

    public Object getPlayerUUID() {
        return this.playerUUID;
    }

    public CANBT toCANBT() {
        CANBT nbt = CANBT.createNew();
        nbt.setUUID("playerUUID", playerUUID);
        nbt.setInteger("hearts", hearts);
        nbt.setInteger("interactionFatigue", interactionFatigue);
        nbt.setInteger("dialogueType", dialogueType);
        return nbt;
    }

    public static Memories fromCNBT(EntityVillagerMCA villager, CANBT cnbt) {
        if (cnbt == null) {
            return null;
        }

        Memories memories = getNew(villager, cnbt.getUUID("playerUUID"));

        memories.hearts = cnbt.getInteger("hearts");
        memories.interactionFatigue = cnbt.getInteger("interactionFatigue");
        memories.dialogueType = cnbt.getInteger("dialogueType");

        return memories;
    }

    public void setHearts(int value) {
        this.hearts = value;
        villager.updateMemories(this);
    }

    public void modHearts(int value) {
        this.hearts += value;
        villager.updateMemories(this);
    }

    public void setInteractionFatigue(int value) {
        this.interactionFatigue = value;
        villager.updateMemories(this);
    }

    public void modInteractionFatigue(int value) {
        this.interactionFatigue += value;
        villager.updateMemories(this);
    }

    public EDialogueType getDialogueType() {
        return EDialogueType.byId(this.dialogueType);
    }

    public void setDialogueType(EDialogueType value) {
        this.dialogueType = value.getId();
        villager.updateMemories(this);
    }


}
