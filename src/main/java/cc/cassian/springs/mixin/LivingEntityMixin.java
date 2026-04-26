package cc.cassian.springs.mixin;

import cc.cassian.springs.api.SpringLogic;
import cc.cassian.springs.api.SpringyEntity;
import cc.cassian.springs.registry.SpringyAttributes;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static cc.cassian.springs.SpringyThings.CONFIG;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements SpringyEntity {

	@Shadow
	public abstract double getAttributeValue(Holder<Attribute> attribute);

	@Inject(method = "createLivingAttributes", at = @At(value = "RETURN"), cancellable = true)
	private static void vertical(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
		cir.setReturnValue(cir.getReturnValue().add(SpringyAttributes.BOUNCINESS));
	}

	@Override
	public double springthings$getEntityBounciness() {
		return getAttributeValue(SpringyAttributes.BOUNCINESS);
	}

}