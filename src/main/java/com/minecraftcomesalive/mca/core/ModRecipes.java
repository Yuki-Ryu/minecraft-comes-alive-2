package com.minecraftcomesalive.mca.core;

import com.minecraftcomesalive.mca.MCAMod;
import com.minecraftcomesalive.mca.core.Registration;
import com.minecraftcomesalive.mca.crafting.recipe.PressingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;

public final class ModRecipes {
    public static final class Types {
        public static final IRecipeType<PressingRecipe> PRESSING = IRecipeType.register(
                MCAMod.MOD_ID + "pressing");

        private Types() {}
    }

    public static final class Serializers {
        public static final RegistryObject<IRecipeSerializer<?>> PRESSING = Registration.RECIPE_SERIALIZERS.register(
                "pressing", PressingRecipe.Serializer::new);

        private Serializers() {}
    }

    private ModRecipes() {}

    static void register() {}
}
