package cc.cassian.slime.block;

import cc.cassian.slime.tags.SlimeBlockTags;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.state.BlockState;

public class DyedSlimeBlock extends SlimeBlock {
	public DyedSlimeBlock(Properties properties) {
		super(properties);
	}

	//? neoforge {
	/*@Override
	public boolean isStickyBlock(BlockState state) {
		return true;
	}

	@Override
	public boolean canStickTo(BlockState state1, BlockState state2) {
		if (state1.is(SlimeBlockTags.SLIME_BLOCKS) || state2.is(SlimeBlockTags.SLIME_BLOCKS))
			return state1.getBlock() == state2.getBlock();
		return super.canStickTo(state1, state2);
	}
	*///?}
}
