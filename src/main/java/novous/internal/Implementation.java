package novous.internal;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import novous.api.PhysicalRegistry;
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
     * Handles minecraft starting.
     */
    public static void handleMinecraftStart() {
        Logger novousStart = LogManager.getLogger("NovousStart");
        novousStart.info("Reached Implementation::handleMinecraftStart()");
        handleBlockRegistration(novousStart);
    }

    /**
     * Handles minecraft init.
     */
    public static void handleMinecraftInit() {
        Logger novousInit = LogManager.getLogger("NovousInit");
        novousInit.info("Reached Implementation::handleMinecraftInit()");
        handleResourceManager(novousInit);
    }

}
