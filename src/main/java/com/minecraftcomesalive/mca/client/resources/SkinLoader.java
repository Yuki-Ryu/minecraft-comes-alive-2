package com.minecraftcomesalive.mca.client.resources;

import com.minecraftcomesalive.mca.MCAMod;
import com.minecraftcomesalive.mca.enums.EProfessionSkinGroup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class SkinLoader {

    public static void loadSkins()
    {
        try
        {
            String runLocation = MCAMod.class.getProtectionDomain().getCodeSource().getLocation().toString();

            if (runLocation.endsWith(".class"))
            {
                String assetsFolder = runLocation.replace("/mca/MCAMod.class", "/assets/").replace("file:/", "");
                loadSkinsFromFolder(assetsFolder);
            }
            else
            {
                loadSkinsFromJar(runLocation);
            }
        }
        catch (IOException e)
        {
            MCAMod.getLog().error(e);
            throw new RuntimeException("Failed to load MCA NPC skins.", e);
        }
    }

    private static void loadSkinsFromFolder(String folder)
    {
        MCAMod.getLog().info("Loading NPC skins from folder: " + folder);
        MCAMod.getLog().warn("---------------------------------------------------");
        MCAMod.getLog().warn("MCA skins should only be loading from a folder in a development environment.");
        MCAMod.getLog().warn("If you have unzipped MCA's .jar file, download MCA again and place the");
        MCAMod.getLog().warn("intact .jar file into your mods folder. DO NOT UNZIP.");
        MCAMod.getLog().warn("---------------------------------------------------");
        processFileList(getFilesInFolder(folder));
    }

    private static void loadSkinsFromJar(String jarFile) throws IOException
    {
        MCAMod.getLog().info("Loading NPC skins from JAR file: " + jarFile);
        processFileList(getFilesInJar(jarFile));
    }

    private static List<File> getFilesInFolder(String folder)
    {
        File directory = new File(folder);
        List<File> resultList = new ArrayList<>();

        File[] fileList = directory.listFiles();
        resultList.addAll(Arrays.asList(fileList));

        for (File file : fileList)
        {
            if (file.isDirectory())
            {
                resultList.addAll(getFilesInFolder(file.getAbsolutePath()));
            }
        }

        return resultList;
    }

    private static List<File> getFilesInJar(String jarFile) throws IOException
    {
        final ZipFile jar = new ZipFile(jarFile);
        final Enumeration enumerator = jar.entries();
        final List<File> files = new ArrayList<>();

        while (enumerator.hasMoreElements())
        {
            final ZipEntry file = (ZipEntry) enumerator.nextElement();
            files.add(new File(file.getName()));
        }

        jar.close();
        return files;
    }

    private static void processFileList(List<File> files)
    {
        int counter = 0;

        for (File file : files)
        {
            String absolutePath = file.getAbsolutePath().replace("\\", "/");

            if (absolutePath.contains("/textures/skins") && !absolutePath.contains("/sleeping/"))
            {
                String relativePath = absolutePath.substring(absolutePath.indexOf("/assets"));

                for (EProfessionSkinGroup skinGroup : EProfessionSkinGroup.values())
                {
                    if (absolutePath.contains(skinGroup.toString().toLowerCase()))
                    {

                        skinGroup.addSkin(relativePath);
                        counter++;
                    }
                }
            }
        }

        if (counter != 0)
        {
            MCAMod.getLog().info("MCA has successfully loaded " + counter + " skins.");
        }
        else
        {
            throw new RuntimeException("Failed to load any NPC skins.");
        }
    }
}
