package com.minecraftcomesalive.mca.core;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import org.lwjgl.system.CallbackI;

public class ModItems {
    public static final RegistryObject<Item> VILLAGER_SPAWNER = Registration.ITEMS.register("villager_spawner",() ->
            new Item(new Item.Properties().group(ItemGroup.MATERIALS)));

    static void register() {}
}
