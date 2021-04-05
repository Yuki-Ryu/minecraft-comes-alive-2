package com.minecraftcomesalive.mca.data;

import com.minecraftcomesalive.mca.MCAMod;
import com.minecraftcomesalive.mca.core.ModBlocks;
import com.minecraftcomesalive.mca.core.ModItems;
import net.minecraft.data.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(ModItems.ROSE_GOLD_INGOT.get(), 9)
                .requires(ModBlocks.ROSE_GOLD_BLOCK.get())
                .unlockedBy("has_item", has(ModItems.ROSE_GOLD_INGOT.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ModBlocks.ROSE_GOLD_BLOCK.get())
                .define('#', ModItems.ROSE_GOLD_INGOT.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_item", has(ModItems.ROSE_GOLD_INGOT.get()))
                .save(consumer);

        CookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.ROSE_GOLD_ORE.get()), ModItems.ROSE_GOLD_INGOT.get(), 0.7f, 200)
                .unlockedBy("has_item", has(ModBlocks.ROSE_GOLD_ORE.get()))
                .save(consumer, modId("rose_gold_ingot_smelting"));
        CookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.ROSE_GOLD_ORE.get()), ModItems.ROSE_GOLD_INGOT.get(), 0.7f, 100)
                .unlockedBy("has_item", has(ModBlocks.ROSE_GOLD_ORE.get()))
                .save(consumer, modId("rose_gold_ingot_blasting"));
    }

    private static ResourceLocation modId(String path) {
        return new ResourceLocation(MCAMod.MOD_ID, path);
    }
}
