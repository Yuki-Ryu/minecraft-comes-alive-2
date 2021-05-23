package com.minecraftcomesalive.mca.core;

import com.minecraftcomesalive.mca.api.ItemGroupMCA;
import com.minecraftcomesalive.mca.items.ItemVillagerEditor;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class ModItems {
    public static final RegistryObject<Item> ROSE_GOLD_INGOT = Registration.ITEMS.register("rose_gold_ingot", () ->
            new Item(new Item.Properties().tab(ItemGroupMCA.MCA)));

    public static final RegistryObject<Item> SILVER_INGOT = Registration.ITEMS.register("silver_ingot", () ->
            new Item(new Item.Properties().tab(ItemGroupMCA.MCA)));


    static final ItemVillagerEditor VILLAGER_EDITOR = new ItemVillagerEditor();


    //public final ItemSpawnEgg EGG_MALE = new ItemSpawnEgg();
    //public final ItemSpawnEgg EGG_FEMALE = new ItemSpawnEgg();
    //public static final Item EGG_MALE = register("egg_male", new SpawnEggItem(EntityType.VILLAGER, 5651507, 12422002, (new Item.Properties()).tab(ItemGroupMCA.MCA)));
    //public static final Item EGG_FEMALE = register("egg_female", new SpawnEggItem(EntityType.VILLAGER, 5651507, 12422002, (new Item.Properties()).tab(ItemGroupMCA.MCA)));


    static void register() {
        ModItems.VILLAGER_EDITOR.getClass();
        //ItemSpawnEgg.EGG_MALE.getClass();
        //ItemSpawnEgg.EGG_FEMALE.getClass();
        //ModItems.EGG_MALE.getClass();
        //ModItems.EGG_FEMALE.getClass();
    }
}
