package com.minecraftcomesalive.mca.api;

import com.google.gson.Gson;
import com.minecraftcomesalive.mca.MCAMod;
import com.minecraftcomesalive.mca.client.gui.widget.button.ButtonMCA;
import com.minecraftcomesalive.mca.client.particle.Gift;
import com.minecraftcomesalive.mca.client.resources.SkinsGroup;
import com.minecraftcomesalive.mca.entity.merchant.villager.EntityVillagerMCA;
import com.minecraftcomesalive.mca.entity.merchant.villager.VillagerProfessionMCA;
import com.minecraftcomesalive.mca.enums.EGender;
import com.minecraftcomesalive.mca.items.ItemStackMCA;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.StringUtils;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class API {
    private static final String RESOURCE_PREFIX = "assets/mca/";

    private static Map<String, Gift> giftMap = new HashMap<>();
    private static Map<String, ButtonMCA[]> buttonMap = new HashMap<>();
    private static List<String> maleNames = new ArrayList<>();
    private static List<String> femaleNames = new ArrayList<>();
    private static List<SkinsGroup> skinGroups = new ArrayList<>();
    private static Random rng;

    /**
     * Performs initialization of the API
     */
    public static void init() {
        rng = new Random();

        // Load skins
        SkinsGroup[] skins = API.readResourceAsJSON("api/skins.json", SkinsGroup[].class);
        Collections.addAll(skinGroups, skins);

        // Load names
        InputStream namesStream = StringUtils.class.getResourceAsStream("/assets/mca/lang/names.lang");
        try {
            // read in all names and process into the correct list
            List<String> lines = IOUtils.readLines(namesStream, Charsets.UTF_8);
            lines.stream().filter((l) -> l.contains("name.male")).forEach((l) -> maleNames.add(l.split("\\=")[1]));
            lines.stream().filter((l) -> l.contains("name.female")).forEach((l) -> femaleNames.add(l.split("\\=")[1]));
        } catch (Exception e) {
            MCAMod.logAndThrow("Failed to read all NPC names from file.", e);
        }

        // Read in buttons
        buttonMap.put("main", API.readResourceAsJSON("api/gui/main.json", ButtonMCA[].class));
        buttonMap.put("interact", API.readResourceAsJSON("api/gui/interact.json", ButtonMCA[].class));
        buttonMap.put("debug", API.readResourceAsJSON("api/gui/debug.json", ButtonMCA[].class));
        buttonMap.put("editor", API.readResourceAsJSON("api/gui/editor.json", ButtonMCA[].class));
        buttonMap.put("work", API.readResourceAsJSON("api/gui/work.json", ButtonMCA[].class));
        buttonMap.put("location", API.readResourceAsJSON("api/gui/location.json", ButtonMCA[].class));

        // Load gifts and assign to the appropriate map with a key value pair and print warnings on potential issues
        Gift[] gifts = API.readResourceAsJSON("api/gifts.json", Gift[].class);
        for (Gift gift : gifts) {
            if (!gift.exists()) {
                MCAMod.log("Could not find gift item or block in registry: " + gift.getName());
            } else {
                giftMap.put(gift.getName(), gift);
            }
        }
    }

    /**
     * Returns a random skin based on the profession and gender provided.
     *
     * @param villager The villager who will be assigned the random skin.
     * @return String location of the random skin
     */
    public static String getRandomSkin(EntityVillagerMCA villager) {
        VillagerProfession profession = villager.getProfession();
        EGender gender = villager.getGender();
        String name = villager.getVillagerName();

        //Special-case skins
        if (gender == EGender.MALE) {
            switch (name.toLowerCase()) {
                case "pewdiepie": return "mca:skins/male/special/pewdiepie_boy.png";
                case "sven": return "mca:skins/male/special/sven.png";
                case "noob":
                case "noober":
                case "neeber": return "mca:skins/male/special/noob.png";
                case "shepard": return "mca:skins/male/special/shepard.png";
                case "minsc": return "mca:skins/male/special/minsc.png";
            }
        } else if (gender == EGender.FEMALE) {
            switch (name.toLowerCase()) {
                case "pewdiepie": return "mca:skins/female/special/pewdiepie_girl.png";
            }
        }

        //Default skin behavior
        Optional<SkinsGroup> group = skinGroups.stream()
                .filter(g -> g.getGender() == gender && profession.getRegistryName() != null && g.getProfession().equals(profession.getRegistryName().toString()))
                .findFirst();

        return group.map(g -> g.getPaths()[rng.nextInt(g.getPaths().length - 1)]).orElseGet(() -> {
            MCAMod.log("No skin found for profession: `" + profession.getRegistryName() + "`. A random skin will be generated.");
            SkinsGroup randomGroup = null;
            while (randomGroup == null || randomGroup.getGender() != gender) {
                randomGroup = skinGroups.get(rng.nextInt(skinGroups.size() - 1));
            }
            return randomGroup.getPaths()[rng.nextInt(randomGroup.getPaths().length)];
        });
    }

    /**
     * Returns the value of a gift from an ItemStack
     *
     * @param stack ItemStack containing the gift item
     * @return int value determining the gift value of a stack
     */
    public static int getGiftValueFromStack(ItemStackMCA stack) {
        if (stack.getItem().getRegistryName() == null) return 0;

        String name = stack.getItem().getRegistryName().toString();
        return giftMap.containsKey(name) ? giftMap.get(name).getValue() : -5;
    }

    /**
     * Returns the proper response type based on a gift provided
     *
     * @param stack ItemStack containing the gift item
     * @return String value of the appropriate response type
     */
    public static String getResponseForGift(ItemStackMCA stack) {
        int value = getGiftValueFromStack(stack);
        return "gift." + (value <= 0 ? "fail" : value <= 5 ? "good" : value <= 10 ? "better" : "best");
    }

    /**
     * Gets a random name based on the gender provided.
     *
     * @param gender The gender the name should be appropriate for.
     * @return A gender appropriate name based on the provided gender.
     */
    public static String getRandomName(@Nonnull EGender gender) {
        if (gender == EGender.MALE) return maleNames.get(rng.nextInt(maleNames.size()));
        else if (gender == EGender.FEMALE) return femaleNames.get(rng.nextInt(femaleNames.size()));
        return "";
    }

    public static String readResource(String path) {
        String data = "";
        String location = RESOURCE_PREFIX + path;

        try {
            data = IOUtils.toString(new InputStreamReader(MCAMod.class.getClassLoader().getResourceAsStream(location)));
        } catch (IOException e) {
            MCAMod.logAndThrow("Failed to read resource from JAR: " + location, e);
        }

        return data;
    }

    public static <T> T readResourceAsJSON(String path, Class<T> type) {
        Gson gson = new Gson();
        T data = gson.fromJson(API.readResource(path), type);
        return data;
    }

    public static VillagerProfessionMCA randomProfession() {
        return VillagerProfessionMCA.fromMC(MCAMod.PROFESSION_GUARD.get());
    }
}

