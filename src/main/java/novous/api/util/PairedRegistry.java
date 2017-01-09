package novous.api.util;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Represents a registry that is paired with two registries, a key and value.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public interface PairedRegistry<KEY, VALUE> {

    /**
     * Registers a key and value pair into the map.
     * @param key
     * @param value
     */
    void register(KEY key, VALUE value);

    /**
     * Returns pairs registered to the registry.
     * @return
     */
    Pair<KEY, VALUE>[] getRegistrationPairs();

}
