package cc.cassian.slime.tags;

import cc.cassian.slime.SlimeTime;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class SlimeItemTags {
	public static final TagKey<Item> SLIME_BALLS = getTagKey("c","slime_balls");
	public static final TagKey<Item> THROWABLE_SLIME_BALLS = getTagKey("throwable_slime_balls");
	public static final TagKey<Item> SLIMY_ARMOR = getTagKey("slimy_armor");

	private static TagKey<Item> getTagKey(String id) {
		return getTagKey(SlimeTime.MOD_ID, id);
	}

	private static TagKey<Item> getTagKey(String namespace, String id) {
		return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(namespace, id));
	}
}
