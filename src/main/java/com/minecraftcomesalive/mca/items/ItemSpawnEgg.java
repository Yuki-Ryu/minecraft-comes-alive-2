package com.minecraftcomesalive.mca.items;

import com.minecraftcomesalive.mca.api.ItemGroupMCA;
import com.minecraftcomesalive.mca.core.Registration;
import com.minecraftcomesalive.mca.enums.EnumGender;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;


public class ItemSpawnEgg {

    private boolean isMale;

    public ItemSpawnEgg(boolean isMale) {
        this.isMale = isMale;
    }

    public void spawnHuman(World world, double posX, double posY, double posZ)
    {

    }

    public static final RegistryObject<Item> EGG_MALE = Registration.ITEMS.register("egg_male", () ->
            new Item(new Item.Properties().group(ItemGroupMCA.MCA_MODS_TAB)));
    public static final RegistryObject<Item> EGG_FEMALE = Registration.ITEMS.register("egg_female", () ->
            new Item(new Item.Properties().group(ItemGroupMCA.MCA_MODS_TAB)));

}
