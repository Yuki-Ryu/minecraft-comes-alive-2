package com.minecraftcomesalive.mca.client.renderer.entity;

import com.minecraftcomesalive.mca.client.renderer.entity.model.ModelVillagerMCA;
import com.minecraftcomesalive.mca.entity.merchant.villager.EntityVillagerMCA;
import com.minecraftcomesalive.mca.util.ResourceLocationCache;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class RenderVillagerMCA extends BipedRenderer<EntityVillagerMCA, ModelVillagerMCA<EntityVillagerMCA>> {
    public RenderVillagerMCA(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ModelVillagerMCA<>(), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityVillagerMCA entity) {
        return ResourceLocationCache.get(entity.getTexture());
    }

    @Override
    protected void scale(EntityVillagerMCA villager, MatrixStack matrixStackIn, float partialTickTime) {
        float scale = 0.9375F;
        matrixStackIn.scale(scale, scale, scale);
    }
}
