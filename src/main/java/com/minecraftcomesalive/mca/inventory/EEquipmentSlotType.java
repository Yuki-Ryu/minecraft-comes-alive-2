package com.minecraftcomesalive.mca.inventory;

import net.minecraft.inventory.EquipmentSlotType;

import java.util.Arrays;

public enum EEquipmentSlotType {
    UNKNOWN(null),
    MAINHAND(EquipmentSlotType.MAINHAND),
    OFFHAND(EquipmentSlotType.OFFHAND),
    FEET(EquipmentSlotType.FEET),
    LEGS(EquipmentSlotType.LEGS),
    CHEST(EquipmentSlotType.CHEST),
    HEAD(EquipmentSlotType.HEAD);

    private final EquipmentSlotType mcType;
    EEquipmentSlotType(EquipmentSlotType type) {
        this.mcType = type;
    }

    public static EEquipmentSlotType fromMC(EquipmentSlotType type) {
        return Arrays.stream(values()).filter(t -> t.mcType == type).findFirst().orElse(UNKNOWN);
    }

    public EquipmentSlotType getMcType() {
        return mcType;
    }
}
