package cc.cassian.slime.platform;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.registry.SlimeDataComponents;
import cc.cassian.slime.registry.SlimeItems;
import cc.cassian.slime.registry.SlimeRecipes;
import cc.cassian.slime.util.SlimeHelpers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.item.v1.ItemComponentTooltipProviderRegistry;
import net.fabricmc.fabric.api.recipe.v1.sync.RecipeSynchronization;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import static cc.cassian.slime.registry.SlimeBlocks.SLIME_BLOCKS;
import static cc.cassian.slime.registry.SlimeBlocks.asListOfStacks;

public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(event -> {
			event.insertAfter(Items.TURTLE_HELMET, SlimeItems.SLIME_BOOTS);
			if (SlimeTime.CONFIG.slimeTime.addSlimeBallToCombatTab)
				event.insertAfter(Items.SNOWBALL, Items.SLIME_BALL);
		});
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(event -> {
			event.insertAfter(Items.TADPOLE_BUCKET, SlimeItems.SLIME_BUCKET);
			event.insertAfter(SlimeItems.SLIME_BUCKET.getDefaultInstance(), SlimeItems.MAGMA_CUBE_BUCKET);
			event.insertBefore(Items.SADDLE, SlimeItems.SLIME_SLING);
		});
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COLORED_BLOCKS).register(event -> {
			event.acceptAll(asListOfStacks(SLIME_BLOCKS));
		});
		ItemComponentTooltipProviderRegistry.addFirst(SlimeDataComponents.FORCE_MULTIPLIER);
		ItemTooltipCallback.EVENT.register(SlimeHelpers::addDyeTooltip);
		RecipeSynchronization.synchronizeRecipeSerializer(SlimeRecipes.SLIME_DYE_RECIPE_SERIALIZER); //fixme neo
	}
}
