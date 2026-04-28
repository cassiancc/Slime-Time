package cc.cassian.slime.platform;

import cc.cassian.rrv.api.ReliableRecipeViewerClientPlugin;
import cc.cassian.rrv.api.recipe.ItemView;
import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.common.builtin.info.InfoClientRecipe;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import cc.cassian.slime.registry.SlimeItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

public class RrvEntrypoint implements ReliableRecipeViewerClientPlugin {
	@Override
	public void onIntegrationInitialize() {
		ItemView.addClientRecipeProvider(list -> {
			list.add(getInfoRecipe(SlimeItems.SLIME_BUCKET));
			list.add(getInfoRecipe(SlimeItems.MAGMA_CUBE_BUCKET));
		});
	}

	private ReliableClientRecipe getInfoRecipe(Item slimeBucket) {
		Identifier key = BuiltInRegistries.ITEM.getKey(slimeBucket);
		return new InfoClientRecipe(key, SlotContent.of(slimeBucket), Component.translatable(slimeBucket.getDescriptionId().replace("item", "lore")));
	}
}
