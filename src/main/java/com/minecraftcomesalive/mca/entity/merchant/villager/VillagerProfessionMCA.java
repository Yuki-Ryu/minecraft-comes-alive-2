package com.minecraftcomesalive.mca.entity.merchant.villager;

import com.google.common.collect.ImmutableSet;
import com.minecraftcomesalive.mca.MCAMod;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.SoundEvent;
import net.minecraft.village.PointOfInterestType;

import java.lang.reflect.Constructor;

public class VillagerProfessionMCA {
    private final VillagerProfession mcProfession;
    private VillagerProfessionMCA(VillagerProfession profession) {
        this.mcProfession = profession;
    }

    public static VillagerProfessionMCA fromMC(VillagerProfession profession) {
        return new VillagerProfessionMCA(profession);
    }

    public static VillagerProfessionMCA createNew(String name, PointOfInterestType poiType, SoundEvent sound) {
        // Creating a new villager profession is private to VillagerProfession. Don't know whose bright idea that
        // was, either Mojang or Forge. Either way, doesn't matter to us, we'll crack it open by reflection.
        try {
            Constructor<VillagerProfession> constructor = VillagerProfession.class.getDeclaredConstructor(String.class, PointOfInterestType.class, ImmutableSet.class, ImmutableSet.class, SoundEvent.class);
            constructor.setAccessible(true);
            return fromMC(constructor.newInstance(name, poiType, ImmutableSet.of(), ImmutableSet.of(), sound));
        } catch (Exception e) {
            MCAMod.getLog().fatal("Unable to create new profession!", e);
            return null;
        }
    }

    public VillagerProfession getMcProfession(){
        return this.mcProfession;
    }
}
