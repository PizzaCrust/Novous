package novous.api.mod;

/**
 * Represents a container that holds an Mod's Metadata, {@link ModMetadata}, and the Mod's
 * instance.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public class ModContainer {

    private final ModCallback instance;
    private final ModMetadata modMetadata;

    public ModContainer(Class<?> clazz) throws Exception {
        instance = (ModCallback) clazz.newInstance();
        modMetadata = clazz.getAnnotation(ModMetadata.class);
    }

    public ModMetadata getModMetadata() {
        return modMetadata;
    }

    public ModCallback getModInstance() {
        return instance;
    }

}
