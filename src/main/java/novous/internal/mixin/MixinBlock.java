package novous.internal.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import novous.api.PhysicalRegistry;

/**
 * This mixin identifies the {@link Block#registerBlocks()} method and injects a hook
 * to allow registering custom blocks.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
@Mixin(Block.class)
public abstract class MixinBlock {

    @Inject(method = "registerBlocks", at = @At("HEAD"))
    public void onRegisterBlockHead(CallbackInfo callbackInfo) {
        Logger novousBootstrapLogger = LogManager.getLogger("NovousBootstrap");
        novousBootstrapLogger.info("Registering custom blocks...");
        PhysicalRegistry.instance().getRegisteredBlocks().forEach((abstractBlock) -> {
            Block nativeBlock = getBlockFromAbstraction(abstractBlock);
            novousBootstrapLogger.info("Registering " + abstractBlock.getRegistryPath());
            Block.REGISTRY.register(abstractBlock.getNumericalId(), new ResourceLocation
                    (abstractBlock.getRegistryPath().toString()), nativeBlock);
        });
    }

    private static Block getBlockFromAbstraction(novous.api.block.Block block) {
        return new Block(Material.IRON, Material.IRON.getMaterialMapColor()).setUnlocalizedName
                (block.getUnlocalizedName());
    }

}