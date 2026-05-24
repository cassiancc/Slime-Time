package cc.cassian.slime.client;

import cc.cassian.slime.api.SlimeColor;
import cc.cassian.slime.registry.SlimeDataComponents;
import cc.cassian.slime.registry.SlimeEntityTypes;
import cc.cassian.slime.registry.SlimeItems;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class SlimeTimeClient {

	public static void onInitializeClient() {
		//? neoforge
		//EntityRendererRegistry.register(SlimeEntityTypes.SLIMEBALL, ThrownItemRenderer::new);
	}

	public static int calculateTinting(final ItemStack itemStack, int tintIndex) {
		if (tintIndex == 0) {
			if (itemStack.has(SlimeDataComponents.DYED_COLOR))
				return itemStack.get(SlimeDataComponents.DYED_COLOR).argb();
			if (itemStack.is(SlimeItems.SLIME_BUCKET) && itemStack.has(DataComponents.BUCKET_ENTITY_DATA)) {
				var tag = itemStack.get(DataComponents.BUCKET_ENTITY_DATA).copyTag();
				SlimeColor slimeTimeColor = SlimeColor.decode(tag);
				if (slimeTimeColor != null)
					return slimeTimeColor.argb();
			}
			return -7274619;
		}
		return -1;
	}
}
