package cc.cassian.slime.platform;

import cc.cassian.slime.registry.SlimeItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;

public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(event -> {
			event.insertAfter(Items.TURTLE_HELMET, SlimeItems.SLIME_BOOTS);
		});
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(event -> {
			event.insertAfter(Items.TADPOLE_BUCKET, SlimeItems.SLIME_BUCKET);
			event.insertAfter(SlimeItems.SLIME_BUCKET.getDefaultInstance(), SlimeItems.MAGMA_CUBE_BUCKET);
		});
	}
}
