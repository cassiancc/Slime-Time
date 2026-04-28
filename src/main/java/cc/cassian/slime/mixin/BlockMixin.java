package cc.cassian.slime.mixin;

import cc.cassian.slime.api.SlimeBlock;
import cc.cassian.slime.tags.SlimeBlockTags;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public class BlockMixin implements SlimeBlock {
	@Override
	public float slime$getBounceRestitution() {
		var block = (Block) (Object) this;
		if (block instanceof BedBlock) return 0.66f;
		else if (block instanceof net.minecraft.world.level.block.SlimeBlock) return 1f;
		else if (block.defaultBlockState().is(SlimeBlockTags.BOUNCY)) return 1f;
		return 0;
	}
}