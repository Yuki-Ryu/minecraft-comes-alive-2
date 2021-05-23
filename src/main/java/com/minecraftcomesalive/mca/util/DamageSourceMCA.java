package com.minecraftcomesalive.mca.util;

import com.minecraftcomesalive.mca.entity.player.PlayerMCA;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;

import java.util.Optional;

public class DamageSourceMCA extends DamageSource{
    DamageSource mcSource;

    private DamageSourceMCA(DamageSource source) {
        super(String.valueOf(source));
        this.mcSource = source;
    }

    public static DamageSourceMCA fromMC(DamageSource source) {
        return new DamageSourceMCA(source);
    }

    public String getCauseName(LivingEntity entity) {
        return mcSource.getLocalizedDeathMessage(entity).getString();
    }

    public Optional<PlayerMCA> getPlayer() {
        if (mcSource.getEntity() instanceof PlayerEntity) {
            return Optional.of(PlayerMCA.fromMC((PlayerEntity)mcSource.getEntity()));
        } else {
            return Optional.empty();
        }
    }

    public boolean isZombie() {
        return mcSource.getDirectEntity() instanceof ZombieEntity;
    }

}
