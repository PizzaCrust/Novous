package novous.internal.mixin;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import novous.api.util.PairedRegistry;

/**
 * Mixin transformer to transform {@link ModelBakery} to load custom ModelBlock
 * models.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
@Mixin(ModelBakery.class)
public abstract class MixinModelBakery implements PairedRegistry<ResourceLocation, ModelBlock> {

    @Shadow @Final private Map<ResourceLocation, ModelBlock> models;

    @Override
    public void register(ResourceLocation resourceLocation, ModelBlock modelBlock) {
        models.put(resourceLocation, modelBlock);
    }

    @Override
    public Pair<ResourceLocation, ModelBlock>[] getRegistrationPairs() {
        List<Pair<ResourceLocation, ModelBlock>> entries = new ArrayList<>();
        models.forEach(((resourceLocation, modelBlock) -> {
            entries.add(Pair.of(resourceLocation, modelBlock));
        }));
        return entries.toArray(new Pair[entries.size()]);
    }

}
