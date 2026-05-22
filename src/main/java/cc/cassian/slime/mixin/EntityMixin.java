package cc.cassian.slime.mixin;

import cc.cassian.slime.api.Bounciness;
import cc.cassian.slime.api.SlimeEntity;
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

import static cc.cassian.slime.SlimeTime.CONFIG;

@Mixin(Entity.class)
public abstract class EntityMixin implements SlimeEntity {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;updateEntityMovementAfterFallOn(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;)V"), method = "move")
	private void vertical(Block instance, BlockGetter level, Entity entity, Operation<Void> original, @Local(name = "effectState") BlockState effectState, @Local(name = "xCollision") boolean xCollision, @Local(name = "zCollision") boolean zCollision, @Local(name = "movement") Vec3 movement) {
		if (CONFIG.bounciness.verticalBounciness)
			Bounciness.restituteMovementAfterCollisions(entity, effectState, xCollision, zCollision, movement);
		else original.call(instance, level, entity);
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setDeltaMovement(DDD)V"), method = "move")
	private void horizontal(Entity entity, double xd, double yd, double zd, Operation<Void> original, @Local(name = "effectState") BlockState effectState, @Local(name = "xCollision") boolean xCollision, @Local(name = "zCollision") boolean zCollision, @Local(name = "movement") Vec3 movement) {
		if (CONFIG.bounciness.horizontalBounciness) {
			var bounceFactor = Bounciness.getMovementAfterCollisions(entity, effectState, xCollision, zCollision, movement);
            original.call(entity, bounceFactor.x, bounceFactor.y, bounceFactor.z);
        } else original.call(entity, xd, yd, zd);
	}

	@Override
	public double slime$getEntityBounciness() {
		return 0.0;
	}

}