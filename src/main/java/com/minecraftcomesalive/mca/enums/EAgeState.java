package com.minecraftcomesalive.mca.enums;

import com.minecraftcomesalive.mca.MCAMod;

import java.util.Arrays;
import java.util.Optional;

public enum EAgeState
{
    UNASSIGNED(-1, 0.8f, 2.0f, 1.5f),
    BABY(0, 0.3f, 0.5f, 0.4f),
    TODDLER(1, 0.3f, 0.6f, 0.5f),
    CHILD(2, 0.5f, 1.1f, 1f),
    TEEN(3, 0.6f, 1.6f, 1.35f),
    ADULT(4, 0.8f, 2f, 1.45f);

    int id;
    float width;
    float height;
    float scaleForAge;

    EAgeState(int id, float width, float height, float scaleForAge) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.scaleForAge = scaleForAge;
    }

    public static EAgeState byId(int id) {
        Optional<EAgeState> state = Arrays.stream(values()).filter((e) -> e.id == id).findFirst();
        return state.orElse(UNASSIGNED);
    }

    public static EAgeState byCurrentAge(int startingAge, int growingAge) {
        int step = startingAge / 4;
        if (growingAge >= step) return EAgeState.TEEN;
        else if (growingAge >= step * 2) return EAgeState.CHILD;
        else if (growingAge >= step * 3 && growingAge < step * 2) return EAgeState.TODDLER;
        else if (growingAge >= step * 4 && growingAge < step * 3) return EAgeState.BABY;
        return EAgeState.ADULT;
    }

    public String localizedName() {
        return MCAMod.localize("enum.agestate." + name().toLowerCase());
    }

    public Object getId() {
        return this.id;
    }
    public Object getWidth() {
        return this.width;
    }
    public Object getHeight() {
        return this.height;
    }
    public Object getScaleForAge() {
        return this.scaleForAge;
    }
}
