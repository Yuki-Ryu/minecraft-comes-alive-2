package com.minecraftcomesalive.mca;

import com.minecraftcomesalive.mca.client.resources.Localizer;
import com.minecraftcomesalive.mca.core.Registration;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(MCAMod.MOD_ID)
public class MCAMod
{
    public static final String MOD_ID = "mca";
    private static Localizer localizer;
    //protected Logger logger;
    //protected Localizer localizer;



    public static void log(String message) {
        LOGGER.info(message);
    }
    public static void log(String message, Exception e) {
        LOGGER.fatal(e);
    }

    public static void logAndThrow(String message, Exception e) {
        LOGGER.fatal(message, e);
        throw new RuntimeException(e);
    }

    public static String localize(String key, String... vars) {
        return localizer.localize(key, vars);
    }

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger("mca");
    public static Logger getLog() {
        return LOGGER;
    }
    public String getModId() {
        return "mca";
    }

    public MCAMod() {
        // Register MCA mod game
        Registration.register();

        localizer = new Localizer();

        // Register the setu method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

     private void setup(final FMLCommonSetupEvent event)
    {
        this.localizer.registerVarParser(str -> str.replaceAll("%Supporter%", getRandomSupporter()));

        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private String getRandomSupporter() {
        return "";
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("MCAMod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});

        // TODO add Villager Profession
        //PROFESSION_GUARD = registerProfession("guard", PointOfInterestType.ARMORER, SoundEvents.ENTITY_VILLAGER_WORK_ARMORER);
        //PROFESSION_CHILD = registerProfession("child", PointOfInterestType.HOME, SoundEvents.ENTITY_VILLAGER_WORK_FARMER);
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }/*
*/
    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
    //public void register() {
        //ROSE_GOLD_INGOT = register("rose_gold_ingot", new Item(new Item.Properties().tab(ItemGroupMCA.MCA)));
    //}

    private void register(FMLServerStartingEvent event) {
    }


    //public static RegistryObject<Item> ROSE_GOLD_INGOT;
    public static RegistryObject<VillagerProfession> PROFESSION_CHILD;
    public static RegistryObject<VillagerProfession> PROFESSION_GUARD;

}
