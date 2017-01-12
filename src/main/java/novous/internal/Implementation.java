package novous.internal;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

import novous.api.PhysicalRegistry;
import novous.api.mod.ModLoader;
import novous.api.util.PairedRegistry;
import novous.api.util.ResourceLinker;

/**
 * Represents the implementation of the *.api.* package in *.internal.*.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public class Implementation {

    /**
     * Handles block registration at init.
     * @param novousBootstrapLogger
     */
    private static void handleBlockRegistration(Logger novousBootstrapLogger) {
        novousBootstrapLogger.info("Registering custom blocks...");
        PhysicalRegistry.instance().getRegisteredBlocks().forEach((abstractBlock) -> {
            Block nativeBlock = new Block(Material.IRON, Material.IRON.getMaterialMapColor()).setUnlocalizedName
                    (abstractBlock.getUnlocalizedName());
            novousBootstrapLogger.info("Registering " + abstractBlock.getRegistryPath() + "...");
            Block.REGISTRY.register(abstractBlock.getNumericalId(), new ResourceLocation
                    (abstractBlock.getRegistryPath().toString()), nativeBlock);
        });
    }

    /**
     * Registers domains to resource manager from resource listeners.
     * @param novousLogger
     */
    private static void handleResourceManager(Logger novousLogger) {
        novousLogger.info("Registering resource manager test...");
        ResourceLinker.REGISTERED_RESOURCE_LINKERS.add(new TestResourceLinker());
        novousLogger.info("Registering abstract resource linked manager...");
        PairedRegistry<String, FallbackResourceManager> resourceManagerRegistry =
                (PairedRegistry<String, FallbackResourceManager>) Minecraft
                .getMinecraft().getResourceManager();
        for (ResourceLinker linker : ResourceLinker.REGISTERED_RESOURCE_LINKERS) {
            linker.getLinkedDomains().forEach((domain) -> resourceManagerRegistry.register
                (domain, new
                    LinkedResourceManager()));
        }
        novousLogger.info("Testing resource linked manager...");
        TestResourceLinker.checkTestSuccess();
    }

    /**
     * Loads all the mods in the mods directory in Minecraft's data directory.
     * @param logger
     */
    private static void handleModLoading(Logger logger) {
        ModLoader.INSTANCE[0] = new SimpleModLoader();
        ModLoader loader = ModLoader.INSTANCE[0];
        File modsDir = new File(Minecraft.getMinecraft().mcDataDir, "novous-mods");
        logger.info("Using " + modsDir.getAbsolutePath() + " as a mods directory for Novous!");
        if (!modsDir.exists()) {
            modsDir.mkdir();
        }
        logger.info("Loading mods in the directory...");
        try {
            loader.loadModDirectory(modsDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles mods when Minecraft reaches preInit()
     * @param logger
     */
    private static void handleModPreInit(Logger logger) {
        logger.info("Calling ModCallback::preInit()");
        ModLoader loader = ModLoader.INSTANCE[0];
        loader.getLoadedMods().forEach(modContainer -> modContainer.getModInstance().preInit());
    }

    /**
     * Handles mods when Minecraft reaches init()
     * @param logger
     */
    private static void handleModInit(Logger logger) {
        logger.info("Calling ModCallback:init()");
        ModLoader loader = ModLoader.INSTANCE[0];
        loader.getLoadedMods().forEach(modContainer -> modContainer.getModInstance().init());
    }

    private static void handleLoggingImplementation(Logger logger) {
        logger.info("Registering logging implementation...");
        novous.api.util.Logger.Provider.INSTANCE[0] = new SimpleLoggerProvider();
    }

    /**
     * Handles minecraft starting.
     */
    public static void handleMinecraftStart() {
        Logger novousStart = LogManager.getLogger("NovousStart");
        novousStart.info("Reached Implementation::handleMinecraftStart()");
        handleLoggingImplementation(novousStart);
        handleModLoading(novousStart);
        handleModPreInit(novousStart);
        handleBlockRegistration(novousStart);
    }

    /**
     * Handles minecraft init.
     */
    public static void handleMinecraftInit() {
        Logger novousInit = LogManager.getLogger("NovousInit");
        novousInit.info("Reached Implementation::handleMinecraftInit()");
        handleResourceManager(novousInit);
        handleModInit(novousInit);
    }

}
