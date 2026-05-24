//? fabric {
package cc.cassian.slime.platform;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.api.SlimeColor;
import cc.cassian.slime.registry.SlimeDataComponents;
import cc.cassian.slime.registry.SlimeItems;
import cc.cassian.slime.util.SlimeHelpers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Objects;

import java.util.ArrayList;
import java.util.List;

import static cc.cassian.slime.SlimeTime.MOD_ID;
import static cc.cassian.slime.registry.SlimeBlocks.SLIME_BLOCKS;
import static cc.cassian.slime.registry.SlimeBlocks.asListOfStacks;
import static cc.cassian.slime.util.SlimeHelpers.addDyedItems;
import static net.fabricmc.fabric.api.resource.ResourcePackActivationType.DEFAULT_ENABLED;

public class FabricEntrypoint implements ModInitializer {

	public static final AttachmentType<SlimeColor> SLIME_STATE = AttachmentRegistry.create(
			SlimeTime.of("slime_variant"),
			builder -> builder
					.persistent(SlimeColor.CODEC)
					.syncWith(SlimeColor.STREAM_CODEC, AttachmentSyncPredicate.all())
	);

	@Override
	public void onInitialize() {
		SlimeTime.onInitialize();
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(event -> {
			event.addAfter(Items.TURTLE_HELMET, addDyedItems(SlimeItems.SLIME_BOOTS.getDefaultInstance()));
			if (SlimeTime.CONFIG.slimeTime.addSlimeBallToCombatTab)
				event.addAfter(Items.SNOWBALL, addDyedItems(Items.SLIME_BALL.getDefaultInstance()));
		});
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(event -> {
			List<ItemStack> newStacks = new ArrayList<>(addDyedItems(SlimeItems.SLIME_BUCKET.getDefaultInstance()));
			newStacks.add(SlimeItems.MAGMA_CUBE_BUCKET.getDefaultInstance());
			event.insertAfter(Items.TADPOLE_BUCKET, newStacks);
			event.insertAfter(Items.SADDLE, addDyedItems(SlimeItems.SLIME_SLING.getDefaultInstance()));
		});
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COLORED_BLOCKS).register(event -> {
			event.acceptAll(asListOfStacks(SLIME_BLOCKS));
		});
		ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, lines) -> {
			if (stack.has(SlimeDataComponents.FORCE_MULTIPLIER)) {
				Objects.requireNonNull(stack.get(SlimeDataComponents.FORCE_MULTIPLIER)).addToTooltip(tooltipContext, lines::add, tooltipType);
			}
		});
		ItemTooltipCallback.EVENT.register(SlimeHelpers::addDyeTooltip);
		if (SlimeTime.CONFIG.slimeTime.colourfulSlimes) {
			ResourceManagerHelper.registerBuiltinResourcePack(
					SlimeTime.of("colourful_slimes"),
					FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(),
					DEFAULT_ENABLED);
		}
	}


}
//?}