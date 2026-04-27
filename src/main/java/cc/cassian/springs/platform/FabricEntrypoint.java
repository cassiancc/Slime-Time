package cc.cassian.springs.platform;

import cc.cassian.springs.registry.SpringyItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.world.item.CreativeModeTabs;

public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(fabricCreativeModeTabOutput -> {
			fabricCreativeModeTabOutput.accept(SpringyItems.SPRING_BOOTS);
		});
	}
}
