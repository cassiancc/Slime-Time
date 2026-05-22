package cc.cassian.slime.api;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.registry.SlimeSoundEvents;
import cc.cassian.slime.tags.SlimeBlockTags;
import cc.cassian.slime.registry.SlimeGameEvents;
import cc.cassian.slime.tags.SlimeItemTags;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Bounciness {
	public static void restituteMovementAfterCollisions(Entity entity, final BlockState effectState, final boolean xCollision, final boolean zCollision, final Vec3 movement) {
		entity.setDeltaMovement(getMovementAfterCollisions(entity, effectState, xCollision, zCollision, movement));
	}

	public static Vec3 getMovementAfterCollisions(Entity entity, final BlockState effectState, final boolean xCollision, final boolean zCollision, final Vec3 movement) {
		double restitution = entity.isSuppressingBounce() ? 0.0 : entity.slime$getEntityBounciness();
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
				restitution = !(-currentMovement.y < getGravity(entity)) && !entity.isInWater() && !entity.isSuppressingBounce() && !effectState.is(SlimeBlockTags.SUPPRESSES_BOUNCE)
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

		if (movementAfterBounce.y < 0.4 && restitution > 0.0) {
			movementAfterBounce = new Vec3(movementAfterBounce.x, 0.0, movementAfterBounce.z);
		}


		if (bounced && restitution > 0.1) {
			entity.gameEvent(SlimeGameEvents.BOUNCE);
			if (SlimeTime.CONFIG.client.slimyBounceSound && entity instanceof LivingEntity livingEntity) {
				for (EquipmentSlot value : EquipmentSlot.values()) {
					if (livingEntity.getItemBySlot(value).is(SlimeItemTags.SLIMY_ARMOR)) {
						playBounceSound(livingEntity);
					}
				}
			}
		}

		return movementAfterBounce;
	}

	public static void playBounceSound(LivingEntity livingEntity) {
		livingEntity.playSound(
				SlimeSoundEvents.SLIMY_BOUNCE,
				1.0F,
				1.0F / (livingEntity.getRandom().nextFloat() * 0.4F + 1.2F) * 0.5F
		);
	}

	private static double getGravity(Entity entity) {
		if (entity instanceof LivingEntity livingEntity) {
			boolean isFalling = entity.getDeltaMovement().y <= 0.0;
			return isFalling && livingEntity.hasEffect(MobEffects.SLOW_FALLING) ? Math.min(entity.getGravity(), 0.01) : entity.getGravity();
		}
		return entity.getGravity();
	}

	private static double getBlockBounciness(Entity entity, final Block onBlock) {
		float blockBounciness = onBlock.slime$getBounceRestitution();
		if (!(entity instanceof LivingEntity)) {
			blockBounciness *= 0.8F;
		}

		return blockBounciness;
	}
}
