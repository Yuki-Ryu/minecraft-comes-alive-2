package com.minecraftcomesalive.mca.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.Arrays;

public enum EHandMCA {
    UNKNOWN(null),
    MAIN_HAND(Hand.MAIN_HAND),
    OFF_HAND(Hand.OFF_HAND);

    EHandMCA(Hand hand) {
        this.mcHand = hand;
    }

    private Hand mcHand;

    public static EHandMCA fromMC(Hand hand) {
        return Arrays.stream(values()).filter(h -> h.mcHand == hand).findFirst().orElse(UNKNOWN);
    }

    public Hand getMcHand() {
        return this.mcHand;
    }

    public Hand setMcHand(Hand hand) {
        return this.mcHand;
    }
}
