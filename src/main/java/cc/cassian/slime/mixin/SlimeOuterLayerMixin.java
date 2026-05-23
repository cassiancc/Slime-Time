package cc.cassian.slime.mixin;

import cc.cassian.slime.util.SlimeHelpers;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.client.renderer.entity.state.SlimeRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SlimeOuterLayer.class)
public abstract class SlimeOuterLayerMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/rendertype/RenderTypes;outline(Lnet/minecraft/resources/Identifier;)Lnet/minecraft/client/renderer/rendertype/RenderType;"), method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/SlimeRenderState;FF)V")
	private RenderType outline(Identifier texture, Operation<RenderType> original, @Local(argsOnly = true, name = "state") SlimeRenderState state) {
		return original.call(SlimeHelpers.getVariatedSlimeTexture(state, texture));
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/rendertype/RenderTypes;entityTranslucent(Lnet/minecraft/resources/Identifier;)Lnet/minecraft/client/renderer/rendertype/RenderType;"), method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/SlimeRenderState;FF)V")
	private RenderType translucent(Identifier texture, Operation<RenderType> original, @Local(argsOnly = true, name = "state") SlimeRenderState state) {
		return original.call(SlimeHelpers.getVariatedSlimeTexture(state, texture));
	}

}