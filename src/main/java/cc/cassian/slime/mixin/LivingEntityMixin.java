package cc.cassian.slime.mixin;

import cc.cassian.slime.api.SlimeEntity;
import cc.cassian.slime.registry.SlimeAttributes;
import cc.cassian.slime.registry.SlimeEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements SlimeEntity {

	public LivingEntityMixin(EntityType<?> type, Level level) {
		super(type, level);
	}

	@Shadow
	public abstract double getAttributeValue(Holder<Attribute> attribute);

	@Shadow
	public abstract boolean isAffectedByPotions();

	@Shadow
	public abstract boolean hasEffect(Holder<MobEffect> effect);

	@Shadow
	public abstract boolean removeEffect(Holder<MobEffect> effect);

	@Inject(method = "createLivingAttributes", at = @At(value = "RETURN"), cancellable = true)
	private static void addBouncinessAttribute(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
		cir.setReturnValue(cir.getReturnValue().add(SlimeAttributes.BOUNCINESS));
	}

	@Inject(method = "travel", at = @At(value = "RETURN"))
	private void removeSlimeTime(Vec3 travelVector, CallbackInfo ci) {
		if (this.isAffectedByPotions() && isInWater() && this.hasEffect(SlimeEffects.SLIME_TIME)) {
			this.removeEffect(SlimeEffects.SLIME_TIME);
		}
	}

	@Override
	public double slime$getEntityBounciness() {
		return getAttributeValue(SlimeAttributes.BOUNCINESS);
	}

}