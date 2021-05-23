package com.minecraftcomesalive.mca.client.particle;

import com.minecraftcomesalive.mca.entity.merchant.villager.EntityVillagerMCA;

public class Gift {
    private String type;
    private String name;
    private int value;
    private int entityId;
    private int slot;


    public Gift(EntityVillagerMCA human, int slot){
        this.entityId = human.getEntityId();
        this.slot = slot;
    }


    public boolean exists() {
        return false; //FIXME
    }


    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public int getSlot() {
        return this.slot;
    }
}
