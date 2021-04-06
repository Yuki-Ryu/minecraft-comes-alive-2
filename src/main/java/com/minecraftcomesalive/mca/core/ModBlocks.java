package com.minecraftcomesalive.mca.core;

import com.minecraftcomesalive.mca.api.ItemGroupMCA;
import com.minecraftcomesalive.mca.blocks.BlockVillagerSpawner;
import com.minecraftcomesalive.mca.blocks.metalpress.MetalPressBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.forgespi.Environment;

import java.util.function.Supplier;

public class ModBlocks {
    public static final RegistryObject<Block> VILLAGER_SPAWNER = register("villager_spawner",() ->
            new Block(AbstractBlock.Properties.of(Material.METAL)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL)));
    //BlockVillagerSpawner VILLAGER_SPAWNER = new BlockVillagerSpawner();

    public static final RegistryObject<Block> ROSE_GOLD_ORE = register("rose_gold_ore", () ->
            new Block(AbstractBlock.Properties.of(Material.STONE)
                    .strength(3, 10)
                    .harvestLevel(2)
                    .harvestTool(ToolType.PICKAXE)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final RegistryObject<Block> ROSE_GOLD_BLOCK = register("rose_gold_block", () ->
            new Block(AbstractBlock.Properties.of(Material.METAL)
                    .strength(3, 10)
                    .sound(SoundType.METAL)));

    public static final RegistryObject<MetalPressBlock> METAL_PRESS = register("metal_press", () ->
            new MetalPressBlock(AbstractBlock.Properties.of(Material.METAL)
                    .strength(4, 20)
                    .sound(SoundType.METAL)));


    static void register() {}

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return Registration.BLOCKS.register(name,block);
    }
    protected static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        RegistryObject<T> ret = registerNoItem(name, block);
        Registration.ITEMS.register(name,() -> new BlockItem(ret.get(), new Item.Properties().tab(ItemGroupMCA.MCA)));
        return ret;
    }
}
