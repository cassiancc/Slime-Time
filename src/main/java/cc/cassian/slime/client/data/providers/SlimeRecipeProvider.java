//? fabric {
package cc.cassian.slime.client.data.providers;

import cc.cassian.slime.recipe.SlimeDyeRecipe;
import cc.cassian.slime.registry.SlimeItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.CustomCraftingRecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.data.recipes.ShapedRecipeBuilder.shaped;

public class SlimeRecipeProvider extends FabricRecipeProvider {

	public SlimeRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
		return new SlimeTimeRecipeProvider(provider, output);
	}

	@Override
	public String getName() {
		return "Slime Time Recipes";
	}

	private static class SlimeTimeRecipeProvider extends RecipeProvider {
		RecipeOutput output;

		public SlimeTimeRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
			super(provider, output);
			this.output = output;
		}

		@Override
		public void buildRecipes() {

			shaped(RecipeCategory.MISC, SlimeItems.SLIME_BOOTS, 1)
					.pattern("l l")
					.pattern("s s")
					.define('l', ConventionalItemTags.SLIME_BALLS)
					.define('s', Items.SLIME_BLOCK)
					.unlockedBy(getHasName(Items.SLIME_BLOCK), has(Items.SLIME_BLOCK))
					.save(output);

			shaped(RecipeCategory.MISC, SlimeItems.SLIME_SLING, 1)
					.pattern("l l")
					.pattern("lll")
					.define('l', ConventionalItemTags.SLIME_BALLS)
					.unlockedBy(getHasName(Items.SLIME_BLOCK), has(Items.SLIME_BLOCK))
					.save(output);
//			dyedItem(SlimeItems.SLIME_BOOTS, "slime_boots");
//			dyedItem(SlimeItems.SLIME_SLING, "slime_sling");
//			dyedItem(Items.SLIME_BALL, "slime_ball");
		}

		public void dyedItem(final Item target, final String group) {
			CustomCraftingRecipeBuilder.customCrafting(
							RecipeCategory.MISC,
							(commonInfo, bookInfo) -> new SlimeDyeRecipe(commonInfo, bookInfo, Ingredient.of(target), this.tag(ItemTags.DYES), new ItemStackTemplate(target))
					)
					.unlockedBy(getHasName(target), this.has(target))
					.group(group)
					.save(output, "slime_time:"+ getItemName(target) + "_dyed");
		}
	}
}
//?}