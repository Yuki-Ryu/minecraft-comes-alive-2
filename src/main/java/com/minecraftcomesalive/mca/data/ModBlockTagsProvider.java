package com.minecraftcomesalive.mca.data;

import com.minecraftcomesalive.mca.MCAMod;
import com.minecraftcomesalive.mca.core.ModBlocks;
import com.minecraftcomesalive.mca.core.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, MCAMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModTags.Blocks.ORES_ROSE_GOLD).add(ModBlocks.ROSE_GOLD_ORE.get());
        tag(Tags.Blocks.ORES).addTag(ModTags.Blocks.ORES_ROSE_GOLD);
        tag(ModTags.Blocks.STORAGE_BLOCKS_ROSE_GOLD).add(ModBlocks.ROSE_GOLD_BLOCK.get());
        tag(Tags.Blocks.STORAGE_BLOCKS).addTag(ModTags.Blocks.STORAGE_BLOCKS_ROSE_GOLD);
        tag(ModTags.Blocks.ORES_SILVER).add(ModBlocks.SILVER_ORE.get());
        tag(Tags.Blocks.ORES).addTag(ModTags.Blocks.ORES_SILVER);
        tag(ModTags.Blocks.STORAGE_BLOCKS_SILVER).add(ModBlocks.SILVER_BLOCK.get());
        tag(Tags.Blocks.STORAGE_BLOCKS).addTag(ModTags.Blocks.STORAGE_BLOCKS_SILVER);
        tag(ModTags.Blocks.STORAGE_BLOCKS_VILLAGER_SPAWNER).add(ModBlocks.VILLAGER_SPAWNER.get());
        tag(Tags.Blocks.STORAGE_BLOCKS).addTag(ModTags.Blocks.STORAGE_BLOCKS_VILLAGER_SPAWNER);
    }
}
