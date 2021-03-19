package com.minecraftcomesalive.mca.api;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemGroupMCA {
    public static final ItemGroup MCA_MODS_TAB = (new ItemGroup(12, "MCA") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(Items.SILVERFISH_SPAWN_EGG);
        }
    });
}
