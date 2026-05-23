package cc.cassian.slime.mixin;

import cc.cassian.slime.api.SlimeColor;
import cc.cassian.slime.api.VariatedSlimeRenderStateAccess;
import net.minecraft.client.renderer.entity.state.SlimeRenderState;
import net.minecraft.world.item.DyeColor;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SlimeRenderState.class)
public class SlimeRenderStateMixin implements VariatedSlimeRenderStateAccess {
    @Unique
    public @Nullable SlimeColor slimeTime$variant;

    public SlimeRenderStateMixin() {
        slimeTime$variant = null;
    }
    @Override
    public @Nullable SlimeColor slimeTime$getVariant() {
        return slimeTime$variant;
    }

    @Override
    public void slimeTime$setVariant(SlimeColor variant) {
        slimeTime$variant = variant;
    }
}
