package com.minecraftcomesalive.mca.enums;

import java.util.Arrays;
import java.util.Optional;

public enum EMoveState
{
    MOVE(0),
    FOLLOW(1),
    STAY(2);

    private int id;
    EMoveState(int id) {
        this.id = id;
    }

    public static EMoveState byId(int idIn) {
        Optional<EMoveState> state = Arrays.stream(values()).filter((e) -> e.id == idIn).findFirst();
        return state.orElse(MOVE);
    }

    public Object getId() {
        return this.id;
    }
}
