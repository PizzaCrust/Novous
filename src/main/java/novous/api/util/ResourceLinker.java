package novous.api.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Represents a linker that allows access to specific items via {@link DomainPath}.
 * This is usually used to link JAR mods into native code. Mods should setup
 * a resource linker to link assets, since Novous doesn't support retrieving
 * assets manually.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public interface ResourceLinker {

    /**
     * This field is used to process resources, please register {@link ResourceLinker}
     * to make domains visible and allow Minecraft to grab assets.
     */
    List<ResourceLinker> REGISTERED_RESOURCE_LINKERS = new ArrayList<>();

    /**
     * Retrieves if the specific domain path given has a resource pertaining to it.
     * @param resourcePath
     * @return
     */
    boolean exists(DomainPath resourcePath);

    /**
     * Retrieves domains that are linked inside of the linker.
     * @return
     */
    List<String> getLinkedDomains();

    /**
     * Retrieves a resource as a stream.
     * @param resourcePath the resource path
     * @return
     */
    InputStream getResourceAsStream(DomainPath resourcePath);

    /**
     * An implementation that is easily implemented by providing lambadas and a varargs statement.
     *
     * @since 1.0-SNAPSHOT
     * @author PizzaCrust
     */
    class SimpleLinker implements ResourceLinker {

        private final Function<DomainPath, Boolean> existsFunc;
        private final Function<DomainPath, InputStream> resourceStreamFunc;

        private final List<String> linkedDomains;

        public SimpleLinker(Function<DomainPath, Boolean> existsFunc, Function<DomainPath,
                InputStream> resourceStreamFunc, String... linkedDomains) {
            this.existsFunc = existsFunc;
            this.resourceStreamFunc = resourceStreamFunc;
            this.linkedDomains = Arrays.asList(linkedDomains);
        }

        public SimpleLinker(Function<DomainPath, InputStream> resourceStreamFunc, String...
                linkedDomains) {
            this.existsFunc = (domainPath) -> resourceStreamFunc.apply(domainPath) != null;
            this.resourceStreamFunc = resourceStreamFunc;
            this.linkedDomains = Arrays.asList(linkedDomains);
        }

        @Override
        public boolean exists(DomainPath resourcePath) {
            return existsFunc.apply(resourcePath);
        }

        @Override
        public List<String> getLinkedDomains() {
            return linkedDomains;
        }

        @Override
        public InputStream getResourceAsStream(DomainPath resourcePath) {
            return resourceStreamFunc.apply(resourcePath);
        }

    }

}
