package cc.cassian.slime.mixin;

import cc.cassian.slime.api.VariatedSlimeAccess;
import cc.cassian.slime.api.VariatedSlimeRenderStateAccess;
import cc.cassian.slime.util.SlimeHelpers;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.client.renderer.entity.state.SlimeRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlimeRenderer.class)
public abstract class SlimeRendererMixin {

	//? if <26.2 {
	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/monster/Slime;Lnet/minecraft/client/renderer/entity/state/SlimeRenderState;F)V", at = @At(value = "RETURN"))
	private void setVariantOnRenderState(Slime entity, SlimeRenderState state, float partialTicks, CallbackInfo ci) {
		VariatedSlimeAccess variatedSlime = (VariatedSlimeAccess) entity;
		VariatedSlimeRenderStateAccess variatedSlimeState = (VariatedSlimeRenderStateAccess) state;
		if (variatedSlime.slimeTime$getVariant() != null) {
			variatedSlimeState.slimeTime$setVariant(variatedSlime.slimeTime$getVariant());
		}
	}
	//?}

	@ModifyReturnValue(at = @At("RETURN"), method = "getTextureLocation(Lnet/minecraft/client/renderer/entity/state/SlimeRenderState;)Lnet/minecraft/resources/Identifier;")
	private Identifier init(Identifier original, SlimeRenderState state) {
		return SlimeHelpers.getVariatedSlimeTexture(state, original);
	}

}