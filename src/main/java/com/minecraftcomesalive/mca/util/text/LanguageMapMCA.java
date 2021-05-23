package com.minecraftcomesalive.mca.util.text;

import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.LanguageMap;

import java.util.HashMap;
import java.util.Map;

public class LanguageMapMCA extends LanguageMap {
    private Map<String, String> localizerMap = new HashMap<>();

    @Override
    public String getOrDefault(String p_230503_1_) {
        return null;
    }

    @Override
    public boolean has(String p_230506_1_) {
        return false;
    }

    @Override
    public boolean isDefaultRightToLeft() {
        return false;
    }

    @Override
    public IReorderingProcessor getVisualOrder(ITextProperties p_241870_1_) {
        return null;
    }
}
