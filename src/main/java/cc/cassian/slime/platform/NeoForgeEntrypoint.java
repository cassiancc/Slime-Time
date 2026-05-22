package cc.cassian.slime.platform;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.registry.SlimeDataComponents;
import cc.cassian.slime.registry.SlimeItems;
import cc.cassian.slime.util.SlimeHelpers;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;
import java.util.Objects;

import static cc.cassian.slime.registry.SlimeBlocks.SLIME_BLOCKS;
import static cc.cassian.slime.registry.SlimeBlocks.asListOfStacks;
import static cc.cassian.slime.util.SlimeHelpers.addDyedItems;

@EventBusSubscriber(modid = SlimeTime.MOD_ID)
public class NeoForgeEntrypoint {

	public static BuildCreativeModeTabContentsEvent event;

	@SubscribeEvent
	public static void modifyTabs(BuildCreativeModeTabContentsEvent event) {
		NeoForgeEntrypoint.event = event;
		ResourceKey<CreativeModeTab> tab = event.getTabKey();
		if (tab.equals(CreativeModeTabs.COMBAT)) {
			insertAfter(Items.TURTLE_HELMET, SlimeItems.SLIME_BOOTS);
			if (SlimeTime.CONFIG.slimeTime.addSlimeBallToCombatTab)
				insertAfter(Items.SNOWBALL, addDyedItems(Items.SLIME_BALL.getDefaultInstance()));
		}
		else if (tab.equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
			insertAfter(Items.TADPOLE_BUCKET, SlimeItems.SLIME_BUCKET);
			insertAfter(SlimeItems.SLIME_BUCKET, SlimeItems.MAGMA_CUBE_BUCKET);
			insertBefore(Items.SADDLE, SlimeHelpers.addDyedItems(SlimeItems.SLIME_SLING.getDefaultInstance()));
		} else if (tab.equals(CreativeModeTabs.COLORED_BLOCKS)) {
//			acceptAll(asListOfStacks(SLIME_BLOCKS));
		}
	}

	private static void acceptAll(List<ItemStack> newItems) {
		event.acceptAll(newItems, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
	}

	private static void insertAfter(ItemStack anchor, ItemStack newItem) {
		event.insertAfter(anchor, newItem, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
	}

	private static void insertAfter(Item anchor, Item newItem) {
		event.insertAfter(anchor.getDefaultInstance(), newItem.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
	}

	private static void insertBefore(Item anchor, Item newItem) {
		event.insertAfter(anchor.getDefaultInstance(), newItem.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
	}

    private static void insertAfter(Item anchor, List<ItemStack> newItems) {
        newItems.reversed().forEach(stack -> {
			event.insertAfter(anchor.getDefaultInstance(), stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		});
    }

	private static void insertBefore(Item anchor, List<ItemStack> newItems) {
		newItems.reversed().forEach(stack -> {
			event.insertBefore(anchor.getDefaultInstance(), stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		});
	}

	@SubscribeEvent
	public static void sendRecipes(OnDatapackSyncEvent event) {
		event.sendRecipes(RecipeType.CRAFTING);
	}

}
