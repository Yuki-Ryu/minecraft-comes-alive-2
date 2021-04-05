package com.minecraftcomesalive.mca.data.client;

import com.minecraftcomesalive.mca.MCAMod;
import com.minecraftcomesalive.mca.core.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, MCAMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.ROSE_GOLD_ORE.get());
        simpleBlock(ModBlocks.ROSE_GOLD_BLOCK.get());

    }
}
