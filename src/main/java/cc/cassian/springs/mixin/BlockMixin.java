package cc.cassian.springs.mixin;

import cc.cassian.springs.SpringyThings;
import cc.cassian.springs.api.SpringyBlock;
import cc.cassian.springs.tags.SpringyBlockTags;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin implements SpringyBlock {
	@Inject(at = @At("HEAD"), method = "updateEntityMovementAfterFallOn", cancellable = true)
	private void init(CallbackInfo info, @Local(argsOnly = true) Entity entity) {
		if (!entity.isSuppressingBounce() && !SpringyThings.CONFIG.advancedVerticalBounciness) {
			this.springs$bounceUp(entity);
			info.cancel();
		}
	}

	@Unique
	private void springs$bounceUp(final Entity entity) {
		Vec3 movement = entity.getDeltaMovement();
		if (movement.y < 0.0) {
			double factor = springthings$getBounceRestitution();
			entity.setDeltaMovement(movement.x, -movement.y * factor, movement.z);
		}
	}

	@Override
	public float springthings$getBounceRestitution() {
		var block = (Block) (Object) this;
		if (block instanceof BedBlock) return 0.66f;
		else if (block instanceof SlimeBlock) return 1f;
		else if (block.defaultBlockState().is(SpringyBlockTags.BOUNCY)) return 1f;
		return 0;
	}
}