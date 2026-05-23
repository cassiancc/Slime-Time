package cc.cassian.slime.platform;

import cc.cassian.rrv.api.ReliableRecipeViewerClientPlugin;
import cc.cassian.rrv.api.recipe.ItemView;
import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.client.recipe.ClientRecipeManager;
import cc.cassian.rrv.common.builtin.crafting.CraftingClientRecipe;
import cc.cassian.rrv.common.builtin.info.InfoClientRecipe;
import cc.cassian.rrv.common.mixin.world.item.crafting.IngredientAccessor;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.api.SlimeColor;
import cc.cassian.slime.recipe.SlimeDyeRecipe;
import cc.cassian.slime.recipe.SlimeShapedRecipe;
import cc.cassian.slime.registry.SlimeBlocks;
import cc.cassian.slime.registry.SlimeDataComponents;
import cc.cassian.slime.registry.SlimeItems;
import cc.cassian.slime.tags.SlimeItemTags;
import cc.cassian.slime.util.SlimeHelpers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.Block;

import java.util.*;

import static cc.cassian.rrv.common.recipe.util.RrvUtil.getItemsFromIngredient;
import static cc.cassian.slime.util.SlimeHelpers.dye;

public class RrvEntrypoint implements ReliableRecipeViewerClientPlugin {

	@Override
	public void onIntegrationInitialize() {
		ItemView.addClientRecipeProvider(list -> {
			list.add(getInfoRecipe(SlimeItems.SLIME_BUCKET));
			list.add(getInfoRecipe(SlimeItems.MAGMA_CUBE_BUCKET));
			addCraftingRecipes(list);
		});
		ItemView.addClientReloadCallback(()->{
			if (SlimeTime.CONFIG.slimeTime.colourfulSlimes) {
				addDyedItems(Items.SLIME_BALL.getDefaultInstance());
				addDyedItems(SlimeItems.SLIME_SLING.getDefaultInstance());
				addDyedItems(SlimeItems.SLIME_BOOTS.getDefaultInstance());
				addDyedItems(SlimeItems.SLIME_BUCKET.getDefaultInstance());
			} else {
				ItemView.excludeItems(SlimeBlocks.SLIME_BLOCKS.values().stream().map(Block::asItem).toArray(Item[]::new));
			}
		});
	}

	private static void addDyedItems(ItemStack defaultInstance) {
		dye(defaultInstance, false).forEach(ItemView::addStackSensitive);
	}

	private static void addCraftingRecipes(List<ReliableClientRecipe> recipeList) {
		ClientRecipeManager.INSTANCE.getRecipesForType(RecipeType.CRAFTING).forEach(craftingRecipeHolder -> {
			var id = craftingRecipeHolder.id().identifier();
			var craftingRecipe = craftingRecipeHolder.value();
			if (craftingRecipe instanceof SlimeShapedRecipe shapedRecipe) {
				HashMap<Integer, SlotContent> ingredients = new HashMap<>();
				int i = 0;
				for (int y = 0; y < 3; y++) {
					for (int x = 0; x < 3; x++) {

						if (x >= shapedRecipe.getWidth() || y >= shapedRecipe.getHeight()) {
							continue;
						}

						if (shapedRecipe.getIngredients().get(i).isPresent()) {
							Ingredient ingredient = shapedRecipe.getIngredients().get(i).get();
							ArrayList<ItemStack> list = new ArrayList<>();
							int index = x + y * 3;
							Optional<TagKey<Item>> tagFromIngredient = getTagFromIngredient(ingredient);
							if (tagFromIngredient.isPresent()) {
								TagKey<Item> tag = tagFromIngredient.get();
								if (tag.equals(SlimeItemTags.SLIME_BLOCKS)) {
									ItemStack defaultInstance = Items.SLIME_BLOCK.getDefaultInstance();
									list.addAll(dye(defaultInstance));
									ingredients.put(index, SlotContent.of(list).bindItemTag(tag));
								} else {
									ingredients.put(index, SlotContent.of(tag));
								}
							} else {
								getItemsFromIngredient(ingredient).forEach(item-> {
									ItemStack defaultInstance = item.getDefaultInstance();
									list.addAll(dye(defaultInstance));
								});

								ingredients.put(index, SlotContent.of(list));
							}
						}

						i++;
					}
				}
				recipeList.add(new CraftingClientRecipe.Builder(id, ingredients).setSize(shapedRecipe.getWidth(), shapedRecipe.getHeight()).setResult(SlotContent.of(dye(shapedRecipe.result.create()))).setDependentIndex(0).build());
			}
			else if (craftingRecipe instanceof SlimeDyeRecipe recipe) {
				List<ItemStackTemplate> results = new ArrayList<>();
				for (Item ingredient : getItemsFromIngredient(recipe.getTarget())) {
					for (SlimeColor color : SlimeColor.values()) {
						ItemStack defaultInstance = ingredient.getDefaultInstance();
						defaultInstance.set(SlimeDataComponents.DYED_COLOR, color);
						results.add(ItemStackTemplate.fromNonEmptyStack(defaultInstance));
					}
				}

				recipeList.add(new CraftingClientRecipe.Builder(id, recipe.getTarget(), recipe.getDye()).setResult(results).setDependentIndex(1).build());
			}
		});
	}

	public static Optional<TagKey<Item>> getTagFromIngredient(Ingredient ingredient) {
		var ingredientContent = ((IngredientAccessor) (Object) ingredient).getValues().unwrap();
        if (ingredientContent.left().isPresent()) {
			return ingredientContent.left();
		}

		return Optional.empty();
	}

	private ReliableClientRecipe getInfoRecipe(Item slimeBucket) {
		Identifier key = BuiltInRegistries.ITEM.getKey(slimeBucket);
		return new InfoClientRecipe(key, SlotContent.of(slimeBucket), Component.translatable(slimeBucket.getDescriptionId().replace("item", "lore")));
	}
}
