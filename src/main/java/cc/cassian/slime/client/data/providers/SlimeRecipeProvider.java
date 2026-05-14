package cc.cassian.slime.client.data.providers;

import cc.cassian.slime.registry.SlimeItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.data.recipes.ShapedRecipeBuilder.shaped;

public class SlimeRecipeProvider extends FabricRecipeProvider {

	public SlimeRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void buildRecipes(RecipeOutput output) {
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
	}

	@Override
	public String getName() {
		return "Slime Time Recipes";
	}
}
