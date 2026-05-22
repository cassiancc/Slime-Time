package cc.cassian.slime.platform;

import cc.cassian.rrv.api.ReliableRecipeViewerClientPlugin;
import cc.cassian.rrv.api.recipe.ItemView;
import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.client.recipe.ClientRecipeManager;
import cc.cassian.rrv.common.builtin.crafting.CraftingClientRecipe;
import cc.cassian.rrv.common.builtin.info.InfoClientRecipe;
import cc.cassian.rrv.common.mixin.world.item.crafting.*;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import cc.cassian.slime.recipe.SlimeDyeRecipe;
import cc.cassian.slime.registry.SlimeDataComponents;
import cc.cassian.slime.registry.SlimeItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;

import java.util.ArrayList;
import java.util.List;

import static cc.cassian.rrv.common.recipe.util.RrvUtil.getItemsFromIngredient;

public class RrvEntrypoint implements ReliableRecipeViewerClientPlugin {

	@Override
	public void onIntegrationInitialize() {
		ItemView.addClientRecipeProvider(list -> {
			list.add(getInfoRecipe(SlimeItems.SLIME_BUCKET));
			list.add(getInfoRecipe(SlimeItems.MAGMA_CUBE_BUCKET));
			addCraftingRecipes(list);
		});
	}

	private static void addCraftingRecipes(List<ReliableClientRecipe> recipeList) {
		ClientRecipeManager.INSTANCE.getRecipesForType(RecipeType.CRAFTING).forEach(craftingRecipeHolder -> {
			var id = craftingRecipeHolder.id().identifier();
			var craftingRecipe = craftingRecipeHolder.value();
			 if (craftingRecipe instanceof SlimeDyeRecipe recipe) {
				List<ItemStackTemplate> results = new ArrayList<>();
				for (Item ingredient : getItemsFromIngredient(recipe.getTarget())) {
					for (DyeColor dyeColor : DyeColor.values()) {
						ItemStack defaultInstance = ingredient.getDefaultInstance();
						defaultInstance.set(SlimeDataComponents.DYED_COLOR, dyeColor);
						results.add(ItemStackTemplate.fromNonEmptyStack(defaultInstance));
					}
				}

				recipeList.add(new CraftingClientRecipe.Builder(id, recipe.getTarget(), recipe.getDye()).setResult(results).setDependentIndex(1).build());
			}
		});
	}


	private ReliableClientRecipe getInfoRecipe(Item slimeBucket) {
		Identifier key = BuiltInRegistries.ITEM.getKey(slimeBucket);
		return new InfoClientRecipe(key, SlotContent.of(slimeBucket), Component.translatable(slimeBucket.getDescriptionId().replace("item", "lore")));
	}
}
