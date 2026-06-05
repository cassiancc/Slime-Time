package cc.cassian.slime.mixin.modefite;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.api.SlimeEntity;
import cc.cassian.slime.client.SlimeDyeSelectProperty;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import timmychips.modefiteitemdefinitions.property.handler.SelectPropertyHandler;
import timmychips.modefiteitemdefinitions.property.registry.SelectPropertyRegistry;

@Pseudo
@Mixin({SelectPropertyRegistry.class})
public abstract class SelectPropertyRegistryMixin {
	@Shadow
	private static void register(ResourceLocation id, SelectPropertyHandler handler) {}

	@Inject(method = "init", at = @At("HEAD"), cancellable = true)
	private static void init(CallbackInfo info) {
		register(SlimeTime.of("dye"), new SlimeDyeSelectProperty());
	}
}
