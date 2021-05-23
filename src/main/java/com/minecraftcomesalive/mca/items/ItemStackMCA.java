package com.minecraftcomesalive.mca.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStackMCA {
    private final ItemStack mcItemStack;


    public static final ItemStackMCA EMPTY = new ItemStackMCA(ItemStack.EMPTY);

    private ItemStackMCA(ItemStack stack) {
        this.mcItemStack = stack;
    }

    public static ItemStackMCA fromMC(ItemStack stack) {
        return new ItemStackMCA(stack);
    }

    public Item getItem() {
        return mcItemStack.getItem();
    }

    public ItemStack getMcItemStack() {
        return this.mcItemStack;
    }

    public void decrStackSize() {
        mcItemStack.setCount(mcItemStack.getCount() - 1);
    }
}
