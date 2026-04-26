package cc.cassian.springs.tags;

import cc.cassian.springs.SpringyThings;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class SpringyBlockTags {
	public static final TagKey<Block> SUPPRESSES_BOUNCE = getTagKey("suppresses_bounce");
	public static final TagKey<Block> BOUNCY = getTagKey("bouncy");

	private static TagKey<Block> getTagKey(String id) {
		return TagKey.create(Registries.BLOCK, SpringyThings.of(id));
	}
}
