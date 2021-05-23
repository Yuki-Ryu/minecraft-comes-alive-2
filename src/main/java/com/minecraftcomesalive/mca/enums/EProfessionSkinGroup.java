package com.minecraftcomesalive.mca.enums;

import com.minecraftcomesalive.mca.MCAMod;

import java.util.ArrayList;
import java.util.List;

public enum EProfessionSkinGroup
{
    Unassigned(-1),
    Farmer(0),
    Baker(0),
    Butcher(4),
    Guard(3),
    Child(0),
    Librarian(1),
    Miner(3),
    Priest(2),
    Smith(3),
    Warrior(3);

    private List<String> maleSkinList;
    private List<String> femaleSkinList;
    private int vanillaId;

    private EProfessionSkinGroup(int vanillaId)
    {
        this.maleSkinList = new ArrayList<String>();
        this.femaleSkinList = new ArrayList<String>();
        this.vanillaId = vanillaId;
    }

    public void addSkin(String locationInJAR)
    {
        String resourceLocation = locationInJAR.replace("/assets/mca/", "mca:");
        String genderChar = resourceLocation.replace("mca:textures/skins/" + this.toString().toLowerCase(), "").substring(0, 1);

        if (genderChar.equals("m")) {
            maleSkinList.add(resourceLocation);
        }

        else if (genderChar.equals("f")) {
            femaleSkinList.add(resourceLocation);
        }

        else {
            MCAMod.getLog().warn("Unable to determine gender of skin: " + resourceLocation);
            MCAMod.getLog().warn("Expected m or f, instead got: " + genderChar);
        }
    }

    private String getSkin(boolean isMale)
    {
        List<String> skinList = isMale ? maleSkinList : femaleSkinList;

        try
        {
            /*return skinList.get(RadixMath.getNumberInRange(0, skinList.size() - 1));*/
            return skinList.get(Math.subtractExact(0, skinList.size() - 1));
        }

        catch (Exception e)
        {
            MCAMod.getLog().error("Unable to generate random skin for skin group <" + this.toString() + ">" + "!");
            MCAMod.getLog().error(e);

            return "";
        }
    }

    public List<String> getSkinList(boolean isMale)
    {
        return isMale ? maleSkinList : femaleSkinList;
    }

   /* public CyclicIntList getListOfSkinIDs(boolean isMale)
    {
        List<String> textureList = getSkinList(isMale);
        List<Integer> ids = new ArrayList<Integer>();

        for (String texture : textureList)
        {
            int id = Integer.parseInt(texture.replaceAll("[^\\d]", ""));
            ids.add(id);
        }

        return CyclicIntList.fromList(ids);
    }*/

    public String getRandomMaleSkin()
    {
        return getSkin(true);
    }

    public String getRandomFemaleSkin()
    {
        return getSkin(false);
    }

    public int getVanillaProfessionId()
    {
        return vanillaId;
    }
}
