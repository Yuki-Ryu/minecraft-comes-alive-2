package com.minecraftcomesalive.mca.data;

import com.minecraftcomesalive.mca.MCAMod;
import com.minecraftcomesalive.mca.core.ModItems;
import com.minecraftcomesalive.mca.core.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, MCAMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        copy(ModTags.Blocks.ORES_ROSE_GOLD, ModTags.Items.ORES_ROSE_GOLD);
        copy(Tags.Blocks.ORES, Tags.Items.ORES);
        copy(ModTags.Blocks.STORAGE_BLOCKS_ROSE_GOLD, ModTags.Items.STORAGE_BLOCKS_ROSE_GOLD);
        //TODO copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);
        copy(ModTags.Blocks.STORAGE_BLOCKS_VILLAGER_SPAWNER, ModTags.Items.STORAGE_BLOCKS_VILLAGER_SPAWNER);
        copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);


        tag(ModTags.Items.INGOTS_ROSE_GOLD).add(ModItems.ROSE_GOLD_INGOT.get());
        tag(Tags.Items.INGOTS).addTag(ModTags.Items.INGOTS_ROSE_GOLD);
    }
}
