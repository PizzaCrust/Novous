package novous.api.util;

import java.util.List;

/**
 * Represents a registry. A register is a key-to-value map wrapper/list and allows
 * other classes to determine a class is a registry/or not. Instead of having
 * a superclass of Object.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public interface Registry<TYPE> {

    /**
     * Registers an object.
     * @param type
     */
    default void register(TYPE type) {
        if (!getRegistrations().contains(type)) {
            getRegistrations().add(type);
        }
    }

    /**
     * Retrieves the registered objects.
     * @return
     */
    List<TYPE> getRegistrations();

}