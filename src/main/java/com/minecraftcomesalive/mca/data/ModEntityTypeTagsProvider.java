package com.minecraftcomesalive.mca.data;

import com.minecraftcomesalive.mca.MCAMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.EntityTypeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public ModEntityTypeTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, MCAMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {

    }
}
