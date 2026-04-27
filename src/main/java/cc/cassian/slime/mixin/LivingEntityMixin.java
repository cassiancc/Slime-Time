package cc.cassian.slime.mixin;

import cc.cassian.slime.api.SlimeEntity;
import cc.cassian.slime.registry.SlimeAttributes;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements SlimeEntity {

	@Shadow
	public abstract double getAttributeValue(Holder<Attribute> attribute);

	@Inject(method = "createLivingAttributes", at = @At(value = "RETURN"), cancellable = true)
	private static void vertical(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
		cir.setReturnValue(cir.getReturnValue().add(SlimeAttributes.BOUNCINESS));
	}

	@Override
	public double slime$getEntityBounciness() {
		return getAttributeValue(SlimeAttributes.BOUNCINESS);
	}

}