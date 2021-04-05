package com.minecraftcomesalive.mca.core;

import com.minecraftcomesalive.mca.MCAMod;
import com.minecraftcomesalive.mca.api.ItemGroupMCA;
import com.minecraftcomesalive.mca.items.ItemSpawnEgg;
import com.minecraftcomesalive.mca.items.ItemVillagerEditor;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;


public class ModItems {
    public static final RegistryObject<Item> ROSE_GOLD_INGOT = Registration.ITEMS.register("rose_gold_ingot", () ->
            new Item(new Item.Properties().tab(ItemGroupMCA.MCA)));

    //private static final ArrayList<Item> BLOCKS = new ArrayList<Item>();
    static final ItemVillagerEditor VILLAGER_EDITOR = new ItemVillagerEditor();

    //public final ItemSpawnEgg EGG_MALE = new ItemSpawnEgg();
    //public final ItemSpawnEgg EGG_FEMALE = new ItemSpawnEgg();
    //public static final Item EGG_MALE = register("egg_male", new SpawnEggItem(EntityType.VILLAGER, 5651507, 12422002, (new Item.Properties()).tab(ItemGroupMCA.MCA)));
    //public static final Item EGG_FEMALE = register("egg_female", new SpawnEggItem(EntityType.VILLAGER, 5651507, 12422002, (new Item.Properties()).tab(ItemGroupMCA.MCA)));

    /*
        private static Item register(Block blockIn) {
            return register(new BlockItem(blockIn, new Item.Properties()));
        }

        private static Item register(Block blockIn, ItemGroup itemGroupIn) {
            return register(new BlockItem(blockIn, (new Item.Properties()).group(itemGroupIn)));
        }

        private static Item register(BlockItem blockItemIn) {
            return register(blockItemIn.getBlock(), blockItemIn);
        }

        protected static Item register(Block blockIn, Item itemIn) {
            return register(Registry.BLOCK.getKey(blockIn), itemIn);
        }

        private static Item register(String key, Item itemIn) {
            return register(new ResourceLocation(key), itemIn);
        }

        private static Item register(ResourceLocation key, Item itemIn) {
            if (itemIn instanceof BlockItem) {
                ((BlockItem)itemIn).addToBlockToItemMap(Item.BLOCK_TO_ITEM, itemIn);
            }

            return Registry.register(Registry.ITEM, key, itemIn);
        }*/
    static void register() {
        ModItems.VILLAGER_EDITOR.getClass();
        //ItemSpawnEgg.EGG_MALE.getClass();
        //ItemSpawnEgg.EGG_FEMALE.getClass();
        //ModItems.EGG_MALE.getClass();
        //ModItems.EGG_FEMALE.getClass();
    }
}
