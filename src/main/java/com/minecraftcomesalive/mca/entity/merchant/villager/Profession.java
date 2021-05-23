package com.minecraftcomesalive.mca.entity.merchant.villager;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.ResourceLocation;

public class Profession {
    private VillagerProfession vanillaProfession;

    public Profession (VillagerProfession vanillaProfession) {
        this.vanillaProfession = vanillaProfession;
    }

    public ResourceLocation getResourceName() {
        return this.vanillaProfession.getRegistryName();
    }

    public VillagerProfession getVanillaProfession() {
        return this.vanillaProfession;
    }
}

