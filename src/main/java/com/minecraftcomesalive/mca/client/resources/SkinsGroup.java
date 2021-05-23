package com.minecraftcomesalive.mca.client.resources;

import com.minecraftcomesalive.mca.enums.EGender;

public class SkinsGroup {
    private final String gender;
    private final String profession;
    private final String[] paths;

    public SkinsGroup(String gender, String profession, String[] paths) {
        this.gender = gender;
        this.profession = profession;
        this.paths = paths;
    }

    public String getProfession() {
        return this.profession;
    }

    public String[] getPaths(){
        return this.paths;
    }

    public EGender getGender() {
        return EGender.byName(gender);
    }
}
