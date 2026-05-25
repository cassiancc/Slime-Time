package cc.cassian.slime.registry;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.api.SlimeColor;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static cc.cassian.slime.registry.SlimeItems.keyOfItem;

public interface SlimeBlocks {

	Map<SlimeColor, SlimeBlock> SLIME_BLOCKS = registerDyedBlocks(
			"slime_block",
			SlimeBlock::new,
			BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK)
	);

	static <T extends Block> HashMap<SlimeColor, T> registerDyedBlocks(String name, Function<BlockBehaviour.Properties, T> blockFactory, BlockBehaviour.Properties settings) {
		LinkedHashMap<SlimeColor, T> map = LinkedHashMap.newLinkedHashMap(SlimeColor.values().length);
		for (SlimeColor value : SlimeColor.values()) {
			if (!SlimeColor.isDefault(value)) {
				String dyedBlockName = "%s_%s".formatted(value.getName(), name);
				map.put(value, registerBlock(dyedBlockName, blockFactory, settings.mapColor(value.getDyeColor()), true));
			}
		}
		return map;
	}

	static <T extends Block> List<ItemStack> asListOfStacks(Map<SlimeColor, T> blocks) {
		return blocks.values().stream().map(b->b.asItem().getDefaultInstance()).toList();
	}

	static <T extends Block> T registerBlock(String name, Function<BlockBehaviour.Properties, T> blockFactory, BlockBehaviour.Properties settings, boolean shouldRegisterItem) {
		// Create a registry key for the block
		ResourceKey<Block> blockKey = keyOfBlock(name);
		// Create the block instance
		T block = blockFactory.apply(settings.setId(blockKey));

		// Sometimes, you may not want to register an item for the block.
		// Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
		if (shouldRegisterItem) {
			// Items need to be registered with a different type of registry key, but the ID
			// can be the same.
			ResourceKey<Item> itemKey = keyOfItem(name);

			BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
			Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
		}

		return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
	}

	static ResourceKey<Block> keyOfBlock(String name) {
		return ResourceKey.create(Registries.BLOCK, SlimeTime.of(name));
	}

	static void touch() {

	}
}
