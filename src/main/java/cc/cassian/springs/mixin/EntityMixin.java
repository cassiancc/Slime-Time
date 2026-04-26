package cc.cassian.springs.mixin;

import cc.cassian.springs.api.SpringLogic;
import cc.cassian.springs.api.SpringyEntity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static cc.cassian.springs.SpringyThings.CONFIG;

@Mixin(Entity.class)
public abstract class EntityMixin implements SpringyEntity {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;updateEntityMovementAfterFallOn(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;)V"), method = "move")
	private void vertical(Block instance, BlockGetter level, Entity entity, Operation<Void> original, @Local(name = "effectState") BlockState effectState, @Local(name = "xCollision") boolean xCollision, @Local(name = "zCollision") boolean zCollision, @Local(name = "movement") Vec3 movement) {
		if (CONFIG.advancedVerticalBounciness)
			SpringLogic.restituteMovementAfterCollisions(entity, effectState, xCollision, zCollision, movement);
		else original.call(instance, level, entity);
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setDeltaMovement(DDD)V"), method = "move")
	private void horizontal(Entity entity, double xd, double yd, double zd, Operation<Void> original, @Local(name = "effectState") BlockState effectState, @Local(name = "xCollision") boolean xCollision, @Local(name = "zCollision") boolean zCollision, @Local(name = "movement") Vec3 movement) {
		if (CONFIG.advancedHorizontalBounciness)
			SpringLogic.restituteMovementAfterCollisions(entity, effectState, xCollision, zCollision, movement);
		else original.call(entity, xd, yd, zd);
	}

	@Override
	public double springthings$getEntityBounciness() {
		return 0.0;
	}

}