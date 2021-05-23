package com.minecraftcomesalive.mca.entity;

import com.minecraftcomesalive.mca.items.ItemStackMCA;
import com.minecraftcomesalive.mca.util.EHandMCA;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.Objects;

public class LivingEntityMCA extends EntityMCA{
    private static AttributeModifierManager attributes;
    private LivingEntity mcEntity;
    private World mcWorld;


    protected LivingEntityMCA(LivingEntity entity, AttributeModifierManager attributes) {
        super(entity);
        this.mcEntity = entity;
        this.mcWorld = entity.level;
        LivingEntityMCA.attributes = attributes;
    }

    // FIXME
    /*public LivingEntityMCA(EntityType<? extends LivingEntity> player, World worldIn) {
        super(player, worldIn);
        attributes = new AttributeModifierManager(GlobalEntityTypeAttributes.getSupplier(player));

    }*/

    public LivingEntityMCA(PlayerEntity player) {
        super(player);
    }

    public static LivingEntityMCA fromMC(LivingEntity entity) {
        return new LivingEntityMCA((PlayerEntity) entity);
    }
    public ItemStackMCA getHeldItem(EHandMCA hand) {
        return ItemStackMCA.fromMC(mcEntity.getItemInHand(hand.getMcHand()));
    }

    public LivingEntity getMcEntity() {
        return this.mcEntity;
    }
    public World getMcWorld()  {
        return this.mcWorld;
    }
}
