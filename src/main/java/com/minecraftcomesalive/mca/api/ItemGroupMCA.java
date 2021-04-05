package com.minecraftcomesalive.mca.api;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemGroupMCA extends ItemGroup{
    public static final ItemGroup MCA = new ItemGroup( getGroupCountSafe(), "mca") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(Items.LAVA_BUCKET);
        }
    };

    public ItemGroupMCA(int p_i1853_1_, String p_i1853_2_) {
        super(p_i1853_1_, p_i1853_2_);
    }

    static void register() {}

    @Override
    public ItemStack makeIcon() {
        return null;
    }
}
