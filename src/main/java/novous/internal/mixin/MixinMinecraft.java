package novous.internal.mixin;

import net.minecraft.client.Minecraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import novous.internal.Implementation;

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
        Implementation.handleMinecraftStart();
    }

    @Inject(method = "init", at = @At("RETURN"))
    public void onInitEnd(CallbackInfo callbackInfo) {
        Implementation.handleMinecraftInit();
    }

}