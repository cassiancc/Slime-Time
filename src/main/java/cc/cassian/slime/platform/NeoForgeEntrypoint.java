package cc.cassian.slime.platform;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.registry.SlimeDataComponents;
import cc.cassian.slime.registry.SlimeItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.Objects;

@EventBusSubscriber()
public class NeoForgeEntrypoint {

	@SubscribeEvent
	public static void modifyTabs(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey().equals(CreativeModeTabs.COMBAT)) {
			event.insertAfter(Items.TURTLE_HELMET.getDefaultInstance(), SlimeItems.SLIME_BOOTS.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			if (SlimeTime.CONFIG.slimeTime.addSlimeBallToCombatTab)
				event.insertAfter(Items.SNOWBALL.getDefaultInstance(), Items.SLIME_BALL.getDefaultInstance(),  CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		}
		else if (event.getTabKey().equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
			event.insertAfter(Items.TADPOLE_BUCKET.getDefaultInstance(), SlimeItems.SLIME_BUCKET.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertAfter(SlimeItems.SLIME_BUCKET.getDefaultInstance(), SlimeItems.MAGMA_CUBE_BUCKET.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
			event.insertBefore(Items.SADDLE.getDefaultInstance(), SlimeItems.SLIME_SLING.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		}
	}

	@SubscribeEvent
	public static void registerTooltip(ItemTooltipEvent event) {
		if (event.getItemStack().has(SlimeDataComponents.FORCE_MULTIPLIER)) {
			Objects.requireNonNull(event.getItemStack().get(SlimeDataComponents.FORCE_MULTIPLIER)).addToTooltip(event.getContext(), (component)->event.getToolTip().add(component), event.getFlags(), event.getItemStack().getComponents());
		}
	}

}
