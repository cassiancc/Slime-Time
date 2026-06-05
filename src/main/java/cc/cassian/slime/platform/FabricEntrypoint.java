//? fabric {
package cc.cassian.slime.platform;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.api.SlimeColor;
import cc.cassian.slime.registry.SlimeDataComponents;
import cc.cassian.slime.registry.SlimeItems;
import cc.cassian.slime.registry.SlimeRecipes;
import cc.cassian.slime.util.SlimeHelpers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.item.v1.ItemComponentTooltipProviderRegistry;
import net.fabricmc.fabric.api.recipe.v1.sync.RecipeSynchronization;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.DyeRecipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import java.util.ArrayList;
import java.util.List;

import static cc.cassian.slime.SlimeTime.MOD_ID;
import static cc.cassian.slime.registry.SlimeBlocks.SLIME_BLOCKS;
import static cc.cassian.slime.registry.SlimeBlocks.asListOfStacks;
import static cc.cassian.slime.util.SlimeHelpers.addDyedItems;
import static net.fabricmc.fabric.api.resource.v1.pack.PackActivationType.ALWAYS_ENABLED;
import static net.fabricmc.fabric.api.resource.v1.pack.PackActivationType.DEFAULT_ENABLED;

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
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(event -> {
			event.insertAfter(Items.TURTLE_HELMET, addDyedItems(SlimeItems.SLIME_BOOTS.getDefaultInstance()));
			if (SlimeTime.CONFIG.slimeTime.addSlimeBallToCombatTab)
				event.insertAfter(Items.SNOWBALL, addDyedItems(Items.SLIME_BALL.getDefaultInstance()));
		});
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(event -> {
			List<ItemStack> newStacks = new ArrayList<>(addDyedItems(SlimeItems.SLIME_BUCKET.getDefaultInstance()));
			newStacks.add(SlimeItems.MAGMA_CUBE_BUCKET.getDefaultInstance());
			event.insertAfter(Items.TADPOLE_BUCKET, newStacks);
			event.insertAfter(Items.SADDLE, addDyedItems(SlimeItems.SLIME_SLING.getDefaultInstance()));
		});
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COLORED_BLOCKS).register(event -> {
			event.acceptAll(asListOfStacks(SLIME_BLOCKS));
		});
		ItemComponentTooltipProviderRegistry.addAfter(DataComponents.ATTRIBUTE_MODIFIERS, SlimeDataComponents.FORCE_MULTIPLIER);
		ItemTooltipCallback.EVENT.register(SlimeHelpers::addDyeTooltip);
		RecipeSynchronization.synchronizeRecipeSerializer(SlimeRecipes.SLIME_DYE_RECIPE_SERIALIZER);
		RecipeSynchronization.synchronizeRecipeSerializer(SlimeRecipes.SLIME_SHAPED_RECIPE_SERIALIZER);
		RecipeSynchronization.synchronizeRecipeSerializer(ShapelessRecipe.SERIALIZER);
		RecipeSynchronization.synchronizeRecipeSerializer(ShapedRecipe.SERIALIZER);
		RecipeSynchronization.synchronizeRecipeSerializer(DyeRecipe.SERIALIZER);
		if (SlimeTime.CONFIG.colorfulSlimes.colourfulSlimes) {
			ResourceLoader.registerBuiltinPack(
					SlimeTime.of("colourful_slimes"),
					FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow(),
					ALWAYS_ENABLED);
		}
	}


}
//?}