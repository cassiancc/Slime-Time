package cc.cassian.slime.mixin;

import cc.cassian.slime.util.SlimeHelpers;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Slime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SlimeRenderer.class)
public abstract class SlimeRendererMixin {

	@ModifyReturnValue(at = @At("RETURN"), method = "getTextureLocation(Lnet/minecraft/world/entity/monster/Slime;)Lnet/minecraft/resources/ResourceLocation;")
	private ResourceLocation init(ResourceLocation original, Slime state) {
		return SlimeHelpers.getVariatedSlimeTexture(state, original);
	}

}