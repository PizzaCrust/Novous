package novous.api.util;

/**
 * Represents a path to a object that is in a certain domain.
 * Similar to Minecraft's ResourceLocation.
 * This class is immutable and requires set, another object containing the different data.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public class DomainPath {

    private final String domain;
    private final String path;

    /**
     * Constructs a {@link DomainPath} from a domain and a path.
     * @param domain
     * @param path
     */
    public DomainPath(String domain, String path) {
        this.domain = domain;
        this.path = path;
    }

    /**
     * Constructs a {@link DomainPath} via arguments.
     * Respectively, the first argument is the domain and the second is the path.
     * @param args
     */
    public DomainPath(String[] args) {
        this(args[0], args[1]);
    }

    /**
     * Takes in a raw resource line, such as in native minecraft: "example_mod:example_resource"
     * and parses it into a {@link DomainPath}
     * @param resourceLine
     */
    public DomainPath(String resourceLine) {
        this(resourceLine.split(":"));
    }

    public String getDomain() {
        return domain;
    }

    public String getPath() {
        return path;
    }

    public DomainPath setDomain(String domain) {
        return new DomainPath(domain, getPath());
    }

    public DomainPath setPath(String path) {
        return new DomainPath(domain, getPath());
    }

    @Override
    public String toString() {
        return domain + ":" + path;
    }

}
