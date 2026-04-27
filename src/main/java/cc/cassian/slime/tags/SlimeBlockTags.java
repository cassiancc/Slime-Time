package cc.cassian.slime.tags;

import cc.cassian.slime.SlimeTime;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class SlimeBlockTags {
	public static final TagKey<Block> SUPPRESSES_BOUNCE = getTagKey("suppresses_bounce");
	public static final TagKey<Block> BOUNCY = getTagKey("bouncy");

	private static TagKey<Block> getTagKey(String id) {
		return TagKey.create(Registries.BLOCK, SlimeTime.of(id));
	}
}
