package novous.api.mod;

/**
 * Represents a container that holds an Mod's Metadata, {@link Mod}, and the Mod's instance.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public class ModContainer {

    private final Object instance;
    private final Mod mod;

    public ModContainer(Class<?> clazz) throws Exception {
        instance = clazz.newInstance();
        mod = clazz.getAnnotation(Mod.class);
    }

    public Mod getModMetadata() {
        return mod;
    }

    public Object getModInstance() {
        return instance;
    }

}
