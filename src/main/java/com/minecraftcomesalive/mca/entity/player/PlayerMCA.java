package com.minecraftcomesalive.mca.entity.player;

import com.minecraftcomesalive.mca.entity.LivingEntityMCA;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class PlayerMCA extends LivingEntityMCA {
    private final PlayerEntity mcPlayer;

    public PlayerMCA(PlayerEntity player) {
        super(player);
        mcPlayer = player;
    }

    /*protected PlayerMCA(World worldIn, PlayerEntity player) {
        super(EntityType.PLAYER,worldIn);
        this.mcPlayer = player;
    }*/

    public static PlayerMCA fromMC(PlayerEntity player) {
        return new PlayerMCA(player);
    }

    public boolean isCreativeMode() {
        return mcPlayer.isCreative();
    }

    public PlayerEntity getMcPlayer() {
        return this.mcPlayer;
    }
}
