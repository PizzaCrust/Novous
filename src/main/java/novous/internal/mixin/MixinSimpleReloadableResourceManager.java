package novous.internal.mixin;

import net.minecraft.client.resources.FallbackResourceManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;

import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import novous.api.util.PairedRegistry;

/**
 * Patches the default Minecraft resource manager to allow registering custom resource managers.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
@Mixin(SimpleReloadableResourceManager.class)
public abstract class MixinSimpleReloadableResourceManager implements PairedRegistry<String,
        FallbackResourceManager> {

    @Shadow @Final private Map<String, FallbackResourceManager> domainResourceManagers;

    @Override
    public void register(String s, FallbackResourceManager fallbackResourceManager) {
        domainResourceManagers.put(s, fallbackResourceManager);
    }

    @Override
    public Pair<String, FallbackResourceManager>[] getRegistrationPairs() {
        List<Pair<String, FallbackResourceManager>> pairs = new ArrayList<>();
        domainResourceManagers.forEach((key, fallbackManager) -> pairs.add(Pair.of(key, fallbackManager)));
        return pairs.toArray(new Pair[pairs.size()]);
    }
}
