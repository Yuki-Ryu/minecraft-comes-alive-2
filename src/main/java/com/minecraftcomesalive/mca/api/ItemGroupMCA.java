package com.minecraftcomesalive.mca.api;

import com.minecraftcomesalive.mca.core.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemGroupMCA extends ItemGroup{
    public static final ItemGroup MCA = new ItemGroup( getGroupCountSafe(), "mca") {
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.VILLAGER_SPAWNER.get());
        }
    };

    public ItemGroupMCA(int addIdFolder, String itemsFolderName) {
        super(addIdFolder, itemsFolderName);
    }

    @Override
    public ItemStack makeIcon() {
        return null;
    }
}
