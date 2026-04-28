package cc.cassian.slime.mixin;

import cc.cassian.slime.api.SlimeBlock;
import cc.cassian.slime.tags.SlimeBlockTags;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin implements SlimeBlock {
	@Inject(method = "fallOn", at = @At("HEAD"), cancellable = true)
	private void init(CallbackInfo info, @Local(argsOnly = true) Entity entity) {
		if (entity.slime$getEntityBounciness() > 0.0) info.cancel();
	}

	@Override
	public float slime$getBounceRestitution() {
		var block = (Block) (Object) this;
		if (block instanceof BedBlock) return 0.66f;
		else if (block instanceof net.minecraft.world.level.block.SlimeBlock) return 1f;
		else if (block.defaultBlockState().is(SlimeBlockTags.BOUNCY)) return 1f;
		return 0;
	}
}