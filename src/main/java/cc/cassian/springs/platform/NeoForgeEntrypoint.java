package cc.cassian.springs.platform;

import cc.cassian.springs.registry.SpringyItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber()
public class NeoForgeEntrypoint {
	@SubscribeEvent
	public static void modifyTabs(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey().equals(CreativeModeTabs.COMBAT)) {
			event.accept(SpringyItems.SPRING_BOOTS);
		}
	}
}
