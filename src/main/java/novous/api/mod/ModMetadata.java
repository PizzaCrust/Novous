package novous.api.mod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represents a mod annotation that declares a mod's metadata.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModMetadata {

    /**
     * The name of the mod.
     * @return
     */
    String name();

    /**
     * The version. Versions must be consistent to a double, to enable easy native comparision
     * and less of a mess of version numbers.
     * @return
     */
    double version();

}
