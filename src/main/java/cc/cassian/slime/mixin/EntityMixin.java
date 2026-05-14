package cc.cassian.slime.mixin;

import cc.cassian.slime.api.Bounciness;
import cc.cassian.slime.api.SlimeEntity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import static cc.cassian.slime.SlimeTime.CONFIG;

@Mixin(Entity.class)
public abstract class EntityMixin implements SlimeEntity {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;updateEntityAfterFallOn(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/world/entity/Entity;)V"), method = "move")
	private void vertical(Block instance, BlockGetter level, Entity entity, Operation<Void> original, MoverType type, Vec3 pos, @Local BlockState effectState, @Share("movement") LocalRef<Vec3> arg) {
		Vec3 movement = arg.get();
		boolean xCollision = !Mth.equal(pos.x, movement.x);
		boolean zCollision = !Mth.equal(pos.z, movement.z);
		if (CONFIG.bounciness.verticalBounciness)
			Bounciness.restituteMovementAfterCollisions(entity, effectState, xCollision, zCollision, movement);
		else original.call(instance, level, entity);
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setDeltaMovement(DDD)V"), method = "move")
	private void horizontal(Entity entity, double xd, double yd, double zd, Operation<Void> original, MoverType type, Vec3 pos, @Local BlockState effectState, @Share("movement") LocalRef<Vec3> arg) {
		if (CONFIG.bounciness.horizontalBounciness) {
			Vec3 movement = arg.get();
			boolean xCollision = !Mth.equal(pos.x, movement.x);
			boolean zCollision = !Mth.equal(pos.z, movement.z);
			var bounceFactor = Bounciness.getMovementAfterCollisions(entity, effectState, xCollision, zCollision, movement);
            original.call(entity, bounceFactor.x, bounceFactor.y, bounceFactor.z);
        } else original.call(entity, xd, yd, zd);
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;collide(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;"), method = "move")
	private Vec3 shareCollide(Entity instance, Vec3 vec, Operation<Vec3> original, @Share("movement") LocalRef<Vec3> arg) {
		var o = original.call(instance, vec);
		arg.set(o);
		return o;
	}

	@Override
	public double slime$getEntityBounciness() {
		return 0.0;
	}

}