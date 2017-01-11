package novous.api.mod;

import com.google.common.collect.ImmutableList;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Represents an object that loads mods into {@link ModContainer}.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public interface ModLoader {

    /**
     * Represents the instance of the {@link ModLoader}. Implementations
     * fill this field when an ModLoader is initialised.
     *
     * Array is to avoid final in interfaces. Access [0] in the array
     * to access the instance.
     */
    ModLoader[] INSTANCE = new ModLoader[1];

    /**
     * Loads the specified mod file
     * @param file
     */
    void loadMod(File file) throws IOException;

    /**
     * Loads the specified mod file safely, returns a false or true if success.
     * @param file
     * @return the boolean
     */
    default boolean safeLoadMod(File file) {
        try {
            loadMod(file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Loads a mod directory
     * @param dir
     */
    default void loadModDirectory(File dir) throws IOException {
        Arrays.asList(dir.listFiles((fDir, name) -> name.endsWith(".jar"))).forEach(this::safeLoadMod);
    }

    /**
     * Retrieves loaded mods from the loader
     * @return
     */
    ImmutableList<ModContainer> getLoadedMods();

}
