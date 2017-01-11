package novous.api.mod;

/**
 * An required callback for mods to allow access to core events.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public interface ModCallback {

    /**
     * Called during net.minecraft.client.Minecraft's constructor.
     */
    void preInit();

    /**
     * Called after net.minecraft.client.Minecraft.init.
     */
    void init();

}