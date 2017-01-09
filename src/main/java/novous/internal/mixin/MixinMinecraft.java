package novous.internal.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import novous.api.PhysicalRegistry;

/**
 * This mixin identifies the {@link Minecraft} constructor/etc and injects a hook
 * to allow registering custom blocks and other stuff.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Inject(method = "<init>*", at = @At("RETURN"))
    public void onConstructorEnd(CallbackInfo callbackInfo) {
        Logger novousBootstrapLogger = LogManager.getLogger("NovousBlock");
        novousBootstrapLogger.info("Registering custom blocks...");
        PhysicalRegistry.instance().getRegisteredBlocks().forEach((abstractBlock) -> {
            Block nativeBlock = new Block(Material.IRON, Material.IRON.getMaterialMapColor()).setUnlocalizedName
                    (abstractBlock.getUnlocalizedName());
            novousBootstrapLogger.info("Registering " + abstractBlock.getRegistryPath() + "...");
            Block.REGISTRY.register(abstractBlock.getNumericalId(), new ResourceLocation
                    (abstractBlock.getRegistryPath().toString()), nativeBlock);
        });
    }

}