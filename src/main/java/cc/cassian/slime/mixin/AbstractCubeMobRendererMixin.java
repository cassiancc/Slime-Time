package cc.cassian.slime.mixin;

//? if >26.1 {
/*import cc.cassian.slime.api.VariatedSlimeAccess;
import cc.cassian.slime.api.VariatedSlimeRenderStateAccess;
import cc.cassian.slime.util.SlimeHelpers;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.monster.cubemob.AbstractCubeMob;
import net.minecraft.client.renderer.entity.AbstractCubeMobRenderer;
import net.minecraft.client.renderer.entity.state.SlimeRenderState;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
*///?}
import net.minecraft.client.renderer.entity.SlimeRenderer;
import org.spongepowered.asm.mixin.Mixin;

//? if >26.1 {
/*@Mixin(AbstractCubeMobRenderer.class)
*///?} else {
@Mixin(SlimeRenderer.class)
//?}
public abstract class AbstractCubeMobRendererMixin {

    //? if >26.1 {
    /*@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/monster/cubemob/AbstractCubeMob;Lnet/minecraft/client/renderer/entity/state/SlimeRenderState;F)V", at = @At(value = "RETURN"))
	private void setVariantOnRenderState(AbstractCubeMob entity, SlimeRenderState state, float partialTicks, CallbackInfo ci) {
        if (entity instanceof VariatedSlimeAccess variatedSlime) {
            VariatedSlimeRenderStateAccess variatedSlimeState = (VariatedSlimeRenderStateAccess) state;
            if (variatedSlime.slimeTime$getVariant() != null) {
                variatedSlimeState.slimeTime$setVariant(variatedSlime.slimeTime$getVariant());
            }
        }
	}
    *///?}


}