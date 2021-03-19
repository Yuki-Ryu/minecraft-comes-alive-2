package com.minecraftcomesalive.mca.items;

import net.minecraft.item.Item;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;

public class ItemSpawnEgg {
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
    }*/
}
