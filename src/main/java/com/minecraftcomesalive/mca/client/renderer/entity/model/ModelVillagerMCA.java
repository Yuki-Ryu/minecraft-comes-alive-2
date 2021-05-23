package com.minecraftcomesalive.mca.client.renderer.entity.model;

import com.minecraftcomesalive.mca.entity.merchant.villager.EntityVillagerMCA;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ModelVillagerMCA<T extends EntityVillagerMCA> extends BipedModel<T> {
    private final ModelRenderer breasts;

    public ModelVillagerMCA() {
        super(0.0F, 0.0F, 64, 64);
        breasts = new ModelRenderer(this, 18, 21);
        breasts.addBox(-3F, 0F, -1F, 6, 3, 3);
        breasts.setPos(0F, 3.5F, -3F);
        breasts.setTexSize(64, 64);
        breasts.mirror = true;
    }
}
