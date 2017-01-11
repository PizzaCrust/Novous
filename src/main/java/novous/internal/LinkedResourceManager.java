package novous.internal;

import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import novous.api.util.DomainPath;
import novous.api.util.ResourceLinker;

/**
 * Allows Minecraft to grab linked resources from abstraction.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public class LinkedResourceManager extends FallbackResourceManager {

    private final List<ResourceLinker> linkers;

    public LinkedResourceManager(List<ResourceLinker> linkers) {
        super(null);
        this.linkers = linkers;
    }

    public LinkedResourceManager() {
        this(ResourceLinker.REGISTERED_RESOURCE_LINKERS);
    }

    @Override
    public Set<String> getResourceDomains() {
        HashSet<String> domains = new HashSet<>();
        linkers.forEach((resourceLinker) -> domains.addAll(resourceLinker.getLinkedDomains()));
        return domains;
    }

    private DomainPath fromNative(ResourceLocation resourceLocation) {
        return new DomainPath(resourceLocation.toString());
    }

    @Override
    public IResource getResource(ResourceLocation location) throws IOException {
        final IResource[] resource = {null};
        linkers.forEach((resourceLinker) -> {
            if (resourceLinker.exists(fromNative(location))) {
                  resource[0] = new LinkedStreamResource(fromNative(location), resourceLinker);
            }
        });
        if (resource[0] == null) {
            throw new IOException();
        }
        return resource[0];
    }

    @Override
    public List<IResource> getAllResources(ResourceLocation location) throws IOException {
        return Collections.singletonList(getResource(location));
    }

    public static class LinkedStreamResource implements IResource {

        private final ResourceLocation resourceLocation;
        private final DomainPath abstractPath;

        private final ResourceLinker linker;

        public LinkedStreamResource(DomainPath domainPath, ResourceLinker linker) {
            this.resourceLocation = new ResourceLocation(domainPath.getDomain(), domainPath
                    .getPath());
            this.abstractPath = domainPath;
            this.linker = linker;
        }

        @Override
        public ResourceLocation getResourceLocation() {
            return this.resourceLocation;
        }

        @Override
        public InputStream getInputStream() {
            return linker.getResourceAsStream(abstractPath);
        }

        @Override
        public boolean hasMetadata() {
            return false;
        }

        @Nullable
        @Override
        public <T extends IMetadataSection> T getMetadata(String sectionName) {
            return null;
        }

        @Override
        public String getResourcePackName() {
            return linker.getClass().getName();
        }

        @Override
        public void close() throws IOException {

        }
    }

}
