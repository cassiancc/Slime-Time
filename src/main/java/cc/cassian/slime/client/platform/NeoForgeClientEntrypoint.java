//? neoforge {
/*package cc.cassian.slime.client.platform;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.client.SlimeTimeClient;
import cc.cassian.slime.registry.SlimeDataComponents;
import cc.cassian.slime.registry.SlimeEntityTypes;
import cc.cassian.slime.registry.SlimeItems;
import cc.cassian.slime.util.SlimeHelpers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.Objects;
import java.util.Optional;

@EventBusSubscriber(modid = SlimeTime.MOD_ID, value = Dist.CLIENT)
public class NeoForgeClientEntrypoint {

	@SubscribeEvent
	public static void renderer(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(SlimeEntityTypes.SLIMEBALL, ThrownItemRenderer::new);
	}

	@SubscribeEvent
	public static void modifyTintColour(RegisterColorHandlersEvent.Item event) {
		event.register(SlimeTimeClient::calculateTinting, Items.SLIME_BALL, SlimeItems.SLIME_BOOTS, SlimeItems.SLIME_SLING, SlimeItems.SLIME_BUCKET);
	}

	@SubscribeEvent
	public static void modifyTintColour(RegisterClientExtensionsEvent event) {
		event.registerItem(new IClientItemExtensions() {
			@Override
			public int getArmorLayerTintColor(ItemStack itemStack, LivingEntity entity, ArmorMaterial.Layer layer, int layerIdx, int fallbackColor) {
				if (itemStack.has(SlimeDataComponents.DYED_COLOR)) {
					return itemStack.get(SlimeDataComponents.DYED_COLOR).argb();
				}
				return IClientItemExtensions.super.getArmorLayerTintColor(itemStack, entity, layer, layerIdx, fallbackColor);
			}
		}, SlimeItems.SLIME_BOOTS);
	}

	@SubscribeEvent
	public static void registerTooltip(ItemTooltipEvent event) {
		if (event.getItemStack().has(SlimeDataComponents.FORCE_MULTIPLIER)) {
			Objects.requireNonNull(event.getItemStack().get(SlimeDataComponents.FORCE_MULTIPLIER)).addToTooltip(event.getContext(), (component)->event.getToolTip().add(component), event.getFlags());
		}
		SlimeHelpers.addDyeTooltip(event.getItemStack(), event.getContext(), event.getFlags(), event.getToolTip());
	}
}
*///?}