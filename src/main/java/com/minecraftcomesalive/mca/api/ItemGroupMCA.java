package com.minecraftcomesalive.mca.api;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemGroupMCA {

    public static final ItemGroup MCA = new ItemGroup(12, "mca") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() { return new ItemStack(Items.SILVERFISH_SPAWN_EGG); }
    };

    static void register() {}
}
