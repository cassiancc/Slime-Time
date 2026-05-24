//? neoforge {
/*package cc.cassian.slime.client.platform;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.client.SlimeTimeClient;
import cc.cassian.slime.client.particle.TintedSlimeParticle;
import cc.cassian.slime.registry.SlimeDataComponents;
import cc.cassian.slime.registry.SlimeItems;
import cc.cassian.slime.registry.SlimeParticleTypes;
import cc.cassian.slime.util.SlimeHelpers;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.Objects;
import java.util.Optional;

@EventBusSubscriber(modid = SlimeTime.MOD_ID, value = Dist.CLIENT)
public class NeoForgeClientEntrypoint {

	@SubscribeEvent
	public static void modifyTintColour(FMLClientSetupEvent event) {
		SlimeTimeClient.onInitializeClient();
	}

	@SubscribeEvent
	public static void modifyTintColour(RegisterParticleProvidersEvent event) {
		event.registerSpecial(SlimeParticleTypes.TINTED_SLIME, new TintedSlimeParticle.TintedSlimeProvider());
	}

	@SubscribeEvent
	public static void modifyTintColour(RegisterClientExtensionsEvent event) {
		event.registerItem(new IClientItemExtensions() {
			@Override
			public int getArmorLayerTintColor(ItemStack itemStack, EquipmentClientInfo.Layer layer, int layerIdx, int fallbackColor) {
				if (itemStack.has(SlimeDataComponents.DYED_COLOR)) {
					return itemStack.get(SlimeDataComponents.DYED_COLOR).argb();
				}
				return IClientItemExtensions.super.getArmorLayerTintColor(itemStack, layer, layerIdx, fallbackColor);
			}
		}, SlimeItems.SLIME_BOOTS);
	}

	@SubscribeEvent
	public static void registerTooltip(ItemTooltipEvent event) {
		if (event.getItemStack().has(SlimeDataComponents.FORCE_MULTIPLIER)) {
			Objects.requireNonNull(event.getItemStack().get(SlimeDataComponents.FORCE_MULTIPLIER)).addToTooltip(event.getContext(), (component)->event.getToolTip().add(component), event.getFlags(), event.getItemStack().getComponents());
		}
		SlimeHelpers.addDyeTooltip(event.getItemStack(), event.getContext(), event.getFlags(), event.getToolTip());
	}

	static RecipeMap recipeMap;

	@SubscribeEvent
	public static void receiveRecipes(RecipesReceivedEvent event) {
		recipeMap = event.getRecipeMap();
	}

    public static Optional<RecipeHolder<CraftingRecipe>> getSynchronizedRecipes(Level level, CraftingInput input) {
        return recipeMap.getRecipesFor(RecipeType.CRAFTING, input, level).findFirst();
    }
}
*///?}