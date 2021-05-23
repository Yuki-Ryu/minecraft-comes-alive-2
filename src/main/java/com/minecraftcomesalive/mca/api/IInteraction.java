package com.minecraftcomesalive.mca.api;

import com.minecraftcomesalive.mca.entity.merchant.villager.EntityVillagerMCA;
import com.minecraftcomesalive.mca.core.Memories;
import com.minecraftcomesalive.mca.entity.player.PlayerMCA;

public interface IInteraction {
    void run(EntityVillagerMCA villager, Memories memories, PlayerMCA player);
    boolean isValidFor(EntityVillagerMCA villager, PlayerMCA player);
}

