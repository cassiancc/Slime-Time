package cc.cassian.slime.client.data.providers;

import cc.cassian.slime.registry.SlimeItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class SlimeRecipeProvider extends FabricRecipeProvider {

	public SlimeRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
		return new RecipeProvider(provider, output) {
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
			}
		};
	}

	@Override
	public String getName() {
		return "Slime Time Recipes";
	}
}
