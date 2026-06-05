package cc.cassian.slime.client;

import cc.cassian.slime.api.SlimeColor;
import cc.cassian.slime.registry.SlimeDataComponents;
import cc.cassian.slime.registry.SlimeEntityTypes;
import cc.cassian.slime.registry.SlimeItems;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class SlimeTimeClient {

	public static void onInitializeClient() {
		ItemTintSources.ID_MAPPER.put(SlimeTime.of("dye"), SlimeDyeTintSource.MAP_CODEC); //fixme neo
		SelectItemModelProperties.ID_MAPPER.put(SlimeTime.of("dye"), SlimeDyeSelectProperty.TYPE);
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
			if (itemStack.is(Items.SLIME_BALL))
				return -1;
			return -7274619;
		}
		return -1;
	
	}
}
