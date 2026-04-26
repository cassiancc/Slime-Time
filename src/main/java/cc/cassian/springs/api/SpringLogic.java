package cc.cassian.springs.api;

import cc.cassian.springs.tags.SpringyBlockTags;
import cc.cassian.springs.registry.SpringyGameEvents;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SpringLogic {
	public static void restituteMovementAfterCollisions(Entity entity, final BlockState effectState, final boolean xCollision, final boolean zCollision, final Vec3 movement) {
		double restitution = entity.isSuppressingBounce() ? 0.0 : entity.springthings$getEntityBounciness();
		Vec3 currentMovement = entity.getDeltaMovement();
		Vec3 movementAfterBounce = currentMovement;
		if (xCollision) {
			movementAfterBounce = currentMovement.with(Direction.Axis.X, -currentMovement.x * restitution);
		}

		if (zCollision) {
			movementAfterBounce = movementAfterBounce.with(Direction.Axis.Z, -currentMovement.z * restitution);
		}

		boolean bounced = restitution > 0.0 && (xCollision || zCollision);
		if (entity.verticalCollision) {
			if (entity.verticalCollisionBelow) {
				restitution = !(-currentMovement.y < getGravity(entity)) && !entity.isSuppressingBounce() && !effectState.is(SpringyBlockTags.SUPPRESSES_BOUNCE)
						? Math.max(restitution, getBlockBounciness(entity, effectState.getBlock()))
						: 0.0;
			}

			double gravityCompensation;
			if (restitution > 0.0) {
				double portionWithMovement = movement.y / currentMovement.y;
				gravityCompensation = portionWithMovement * getGravity(entity);
				bounced = true;
			} else {
				gravityCompensation = 0.0;
			}

			movementAfterBounce = movementAfterBounce.with(Direction.Axis.Y, (gravityCompensation - currentMovement.y) * restitution);
		}

		if (bounced) {
			entity.gameEvent(SpringyGameEvents.BOUNCE);
		}

		entity.setDeltaMovement(movementAfterBounce);
	}

	private static double getGravity(Entity entity) {
		if (entity instanceof LivingEntity livingEntity) {
			boolean isFalling = entity.getDeltaMovement().y <= 0.0;
			return isFalling && livingEntity.hasEffect(MobEffects.SLOW_FALLING) ? Math.min(entity.getGravity(), 0.01) : entity.getGravity();
		}
		return entity.getGravity();
	}

	private static double getBlockBounciness(Entity entity, final Block onBlock) {
		float blockBounciness = onBlock.springthings$getBounceRestitution();
		if (!(entity instanceof LivingEntity)) {
			blockBounciness *= 0.8F;
		}

		return blockBounciness;
	}
}
