package com.minecraftcomesalive.mca.items;

import com.minecraftcomesalive.mca.core.Registration;
import net.minecraft.item.Item;
import com.minecraftcomesalive.mca.core.ModItems;
import com.minecraftcomesalive.mca.api.ItemGroupMCA;
import net.minecraftforge.fml.RegistryObject;

public class ItemVillagerEditor {

    public static final RegistryObject<Item> VILLAGER_EDITOR = Registration.ITEMS.register("villager_editor", () ->
            new Item(new Item.Properties().group(ItemGroupMCA.MCA)));

    static void register() {}
}
