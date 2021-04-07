package com.minecraftcomesalive.mca.items;

import com.google.common.collect.Maps;
import com.minecraftcomesalive.mca.api.ItemGroupMCA;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;


import java.util.Map;


public class ItemSpawnEgg extends Item{
    private static final Map<EntityType<?>, SpawnEggItem> EGGS = Maps.newIdentityHashMap();
    private final EntityType<?> typeIn;
    private final int primaryColor;
    private final int secondaryColor;
    //private final Object gender;

    public static final Item EGG_MALE = register("egg_male", new SpawnEggItem(EntityType.VILLAGER, 5651507, 12422002, (new Item.Properties()).tab(ItemGroupMCA.MCA)));
    public static final Item EGG_FEMALE = register("egg_female", new SpawnEggItem(EntityType.VILLAGER, 5651507, 12422002, (new Item.Properties()).tab(ItemGroupMCA.MCA)));



    public ItemSpawnEgg(EntityType<?> typeIn, int primaryColorIn, int secondaryColorIn, Item.Properties builder) {
        super(builder);
        this.typeIn = typeIn;
        this.primaryColor = primaryColorIn;
        this.secondaryColor = secondaryColorIn;
        //this.gender = gender;
        //EGGS.put(typeIn, this);
    }



    private static Item register(Block blockIn) {
        return register(new BlockItem(blockIn, new Item.Properties()));
    }

    private static Item register(Block blockIn, ItemGroup itemGroupIn) {
        return register(new BlockItem(blockIn, (new Item.Properties()).tab(itemGroupIn)));
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
            ((BlockItem)itemIn).removeFromBlockToItemMap(Item.BY_BLOCK, itemIn);
        }

        return Registry.register(Registry.ITEM, key, itemIn);
    }

    /*private final EnumGender gender;

    public ItemSpawnEgg(EnumGender gender, Item.Properties properties) {
        super(properties);
        this.gender = gender;
    }

    @Override
    public ActionResultType handleUseOnBlock(CItemUseContext context) {
        if (!context.getWorld().isRemote && context.getDirection() == Direction.UP) {
            VillagerFactory.newVillager(context.getWorld())
                    .withGender(gender)
                    .withPosition(context.getPos())
                    .spawn();
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    private boolean isMale;

    public ItemSpawnEgg(boolean isMale) {
        this.isMale = isMale;
    }

    public void spawnHuman(World world, double posX, double posY, double posZ)
    {

    }

    public ItemSpawnEgg(RegistryObject<Item> isMale) {
    }

    public static final RegistryObject<Item> EGG_MALE = Registration.ITEMS.register("egg_male", () ->
            new Item(new Item.Properties().group(ItemGroupMCA.MCA)));
    public static final RegistryObject<Item> EGG_FEMALE = Registration.ITEMS.register("egg_female", () ->
            new Item(new Item.Properties().group(ItemGroupMCA.MCA)));

    static void register() {}*/

}
