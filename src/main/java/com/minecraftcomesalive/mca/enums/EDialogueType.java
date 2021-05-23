package com.minecraftcomesalive.mca.enums;

import java.util.Arrays;
import java.util.Optional;

public enum EDialogueType
{
    UNASSIGNED(-1, "unassigned"),
    CHILDP(0, "childp"),
    CHILD(1, "child"),
    ADULT(2, "adult"),
    SPOUSE(3, "spouse");

    private int id;
    private String name;

    EDialogueType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static EDialogueType byId(int idIn) {
        Optional<EDialogueType> state = Arrays.stream(values()).filter((e) -> e.id == idIn).findFirst();
        return state.orElse(UNASSIGNED);
    }

    public int getId() { return id; }
}
