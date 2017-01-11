package novous.internal;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import novous.api.util.DomainPath;
import novous.api.util.ResourceLinker;

/**
 * A test implementation of {@link ResourceLinker}. Static method asks internally for resource,
 * if it is not null; then the test is a success.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public class TestResourceLinker implements ResourceLinker {
    @Override
    public boolean exists(DomainPath resourcePath) {
        return resourcePath.equals(new DomainPath("novous_test_linker:test_resource"));
    }

    @Override
    public List<String> getLinkedDomains() {
        return Arrays.asList("novous_test_linker");
    }

    @Override
    public InputStream getResourceAsStream(DomainPath resourcePath) {
        return new ByteArrayInputStream("yes".getBytes());
    }

    /**
     * Checks if the test resource is registered, if not stop the game and throw a
     * {@link RuntimeException}.
     */
    public static void checkTestSuccess() {
        try {
            IResource resource = Minecraft.getMinecraft().getResourceManager().getResource(new
                    ResourceLocation
                    ("novous_test_linker:test_resource"));
            if (resource instanceof LinkedResourceManager.LinkedStreamResource) {
                InputStream stream = resource.getInputStream();
                String yesString = IOUtils.toString(stream);
                if (!yesString.equals("yes")) {
                    throw new RuntimeException();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
