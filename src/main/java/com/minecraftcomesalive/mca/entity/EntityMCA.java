package com.minecraftcomesalive.mca.entity;

import com.minecraftcomesalive.mca.entity.player.PlayerMCA;
import com.minecraftcomesalive.mca.util.math.BlockPosMCA;
import com.minecraftcomesalive.mca.world.WorldMCA;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.UUID;


public class EntityMCA{
    private final Entity mcEntity;
    private final WorldMCA world;



    public EntityMCA(EntityType<? extends Entity> entityType, World world, Entity entity) {
        this.mcEntity = entity;
        this.world = entity != null ? WorldMCA.fromMC(entity.level) : null;
    }

    public EntityMCA(Entity entity) {
        this.mcEntity = entity;
        this.world = entity != null ? WorldMCA.fromMC(entity.level) : null;
    }


    public Entity getMcEntity() {
        return this.mcEntity;
    }
    public WorldMCA getWorld() {
        return this.world;
    }



    public double getPosX() {
        return mcEntity.getX();
    }
    public double getPosY() {
        return mcEntity.getY();
    }
    public double getPosZ() {
        return mcEntity.getZ();
    }

    public String getName() {
        return mcEntity.getName().getString();
    }

    public UUID getUniqueID() {
        return mcEntity.getUUID();
    }
    public UUID uuid() {
        return mcEntity.getUUID();
    }

    public BlockPosMCA getPosition() {
        return new BlockPosMCA(getPosX(), getPosY(), getPosZ());
    }



    public static EntityMCA fromMC(Entity entity) {
        return new EntityMCA(entity);
    }

    public void sendMessage(String message) {
        mcEntity.sendMessage(new StringTextComponent(message), UUID.fromString(""));
    }

    public boolean attackFrom(DamageSource source, float amount) {
        return mcEntity.hurt(source, amount);
    }


    public void dropItem(ItemStack stack, float offsetY) {
        mcEntity.spawnAtLocation(stack, offsetY);
    }

    public boolean isPlayer() {
        return this.mcEntity instanceof PlayerEntity;
    }

    public Optional<PlayerMCA> asPlayer() {
        if (this.mcEntity instanceof PlayerEntity) {
            return Optional.of((PlayerMCA) this);
        } else {
            return Optional.empty();
        }
    }
}
