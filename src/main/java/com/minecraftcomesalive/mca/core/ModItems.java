package com.minecraftcomesalive.mca.core;

import com.minecraftcomesalive.mca.MCAMod;
import com.minecraftcomesalive.mca.api.ItemGroupMCA;
import com.minecraftcomesalive.mca.items.ItemSpawnEgg;
import com.minecraftcomesalive.mca.items.ItemVillagerEditor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;


public class ModItems {
    //private static final ArrayList<Item> BLOCKS = new ArrayList<Item>();
    static final ItemVillagerEditor VILLAGER_EDITOR = new ItemVillagerEditor();

    public static final ItemSpawnEgg EGG_MALE = new ItemSpawnEgg(true);
    public static final ItemSpawnEgg EGG_FEMALE = new ItemSpawnEgg(false);

    public static final RegistryObject<Item> VILLAGER_SPAWNER = Registration.ITEMS.register("villager_spawner",() ->
            new Item(new Item.Properties().group(ItemGroupMCA.MCA_MODS_TAB)));

    static void register() {
        ModItems.VILLAGER_EDITOR.getClass();
        ModItems.EGG_MALE.getClass();
        ModItems.EGG_FEMALE.getClass();
    }

    //static void register() {}
}
