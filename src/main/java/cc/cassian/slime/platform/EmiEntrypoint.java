package cc.cassian.slime.platform;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.api.SlimeColor;
import cc.cassian.slime.recipe.SlimeDyeRecipe;
import cc.cassian.slime.recipe.SlimeShapedRecipe;
import cc.cassian.slime.registry.SlimeItems;
import cc.cassian.slime.tags.SlimeItemTags;
import cc.cassian.slime.util.SlimeHelpers;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiCraftingRecipe;
import dev.emi.emi.api.recipe.EmiInfoRecipe;
import dev.emi.emi.api.recipe.EmiPatternCraftingRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.GeneratedSlotWidget;
import dev.emi.emi.api.widget.SlotWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cc.cassian.slime.util.SlimeHelpers.dye;

@dev.emi.emi.api.EmiEntrypoint
public class EmiEntrypoint implements EmiPlugin {

	private EmiInfoRecipe getInfoRecipe(Item slimeBucket) {
		ResourceLocation key = BuiltInRegistries.ITEM.getKey(slimeBucket);
		return new EmiInfoRecipe(List.of(EmiStack.of(slimeBucket)), List.of(Component.translatable(slimeBucket.getDescriptionId().replace("item", "lore"))), key.withPrefix("/"));
	}

	private static void addCraftingRecipes(EmiRegistry registry) {
		if (SlimeTime.CONFIG.slimeTime.colourfulSlimes) {
			for (SlimeColor color : SlimeColor.values()) {
				EmiStack slimeball = EmiStack.of(SlimeHelpers.dye(Items.SLIME_BALL.getDefaultInstance(), color));
				EmiStack slimeBlock = EmiStack.of(dye(Items.SLIME_BLOCK.getDefaultInstance(), color));
				EmiStack slimeBoots = EmiStack.of(dye(SlimeItems.SLIME_BOOTS.getDefaultInstance(), color));
				registry.addRecipe(new EmiCraftingRecipe(List.of(slimeball, slimeball, EmiStack.EMPTY, slimeball, slimeball), slimeBlock, SlimeTime.of("/"+color.getName()+"_slime_block")));
				registry.addRecipe(new EmiCraftingRecipe(List.of(slimeBlock), EmiStack.of(slimeball.copy().getItemStack().copyWithCount(4)), SlimeTime.of("/"+color.getName()+"_slime_ball")));
				registry.addRecipe(new EmiCraftingRecipe(List.of(slimeball, EmiStack.EMPTY, slimeball, slimeBlock, EmiStack.EMPTY, slimeBlock), slimeBoots, SlimeTime.of("/"+color.getName()+"_slime_boots")));
			}
			EmiStack slimeball = EmiStack.of(Items.SLIME_BALL.getDefaultInstance());
			EmiStack slimeBlock = EmiStack.of(Items.SLIME_BLOCK.getDefaultInstance());
			EmiStack slimeBoots = EmiStack.of(SlimeItems.SLIME_BOOTS.getDefaultInstance());
			registry.addRecipe(new EmiCraftingRecipe(List.of(slimeball, slimeball, EmiStack.EMPTY, slimeball, slimeball), slimeBlock, SlimeTime.of("/_slime_block")));
			registry.addRecipe(new EmiCraftingRecipe(List.of(slimeBlock), EmiStack.of(slimeball.copy().getItemStack().copyWithCount(4)), SlimeTime.of("/slime_ball")));
			registry.addRecipe(new EmiCraftingRecipe(List.of(slimeball, EmiStack.EMPTY, slimeball, slimeBlock, EmiStack.EMPTY, slimeBlock), slimeBoots, SlimeTime.of("/slime_boots")));
		}


		Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING).forEach(craftingRecipeHolder -> {
			var id = craftingRecipeHolder.id();
			var craftingRecipe = craftingRecipeHolder.value();
			/*
			if (craftingRecipe instanceof SlimeShapedRecipe shapedRecipe) {
				HashMap<Integer, EmiIngredient> ingredients = new HashMap<>();
				int i = 0;
				for (int y = 0; y < 3; y++) {
					for (int x = 0; x < 3; x++) {

						if (x >= shapedRecipe.getWidth() || y >= shapedRecipe.getHeight()) {
							continue;
						}

						if (!shapedRecipe.getIngredients().get(i).isEmpty()) {
							Ingredient ingredient = shapedRecipe.getIngredients().get(i);
                            int index = x + y * 3;
                            ingredients.put(index, EmiIngredient.of(ingredient));
						}

						i++;
					}
				}
				registry.addRecipe(new EmiCraftingRecipe(padIngredients(ingredients), EmiStack.of(shapedRecipe.result), id));
			}
			else */
			if (craftingRecipe instanceof SlimeDyeRecipe recipe) {

				registry.addRecipe(new EmiSlimeDyeRecipe(recipe.getTarget().getItems()[0], id));
			}
		});
	}

    private static List<EmiIngredient> padIngredients(HashMap<Integer, EmiIngredient> map) {
		List<EmiIngredient> list = new ArrayList<>(9);
		for (int i = 0; i < 9; i++) {
            list.add(map.getOrDefault(i, EmiStack.EMPTY));
		}
		return list;
    }

	@Override
	public void register(EmiRegistry emiRegistry) {
		emiRegistry.addRecipe(getInfoRecipe(SlimeItems.SLIME_BUCKET));
		emiRegistry.addRecipe(getInfoRecipe(SlimeItems.MAGMA_CUBE_BUCKET));
        if (!SlimeTime.CONFIG.slimeTime.colourfulSlimes) {
            emiRegistry.removeEmiStacks(stack->stack.getItemStack().is(SlimeItemTags.SLIME_BLOCKS));
        }
        addCraftingRecipes(emiRegistry);
	}

	public static class EmiSlimeDyeRecipe extends EmiPatternCraftingRecipe {
		private static final List<DyeItem> DYES = Stream.of(DyeColor.values()).map(DyeItem::byColor).toList();
		private final ItemStack stackToDye;

		public EmiSlimeDyeRecipe(ItemStack item, ResourceLocation id) {
			super(List.of(EmiIngredient.of(DYES.stream().map(EmiStack::of).collect(Collectors.toList())), EmiStack.of(item)), EmiStack.of(item), id);
			this.stackToDye = item;
		}

		public SlotWidget getInputWidget(int slot, int x, int y) {
			if (slot == 0) {
				return new SlotWidget(EmiStack.of(this.stackToDye), x, y);
			} else if (slot == 1) {
				return new GeneratedSlotWidget((r) -> {
					DyeItem dyes = this.getDyes(r);
					return EmiStack.of(dyes);
				}, this.unique, x, y);
			} else return new SlotWidget(EmiStack.EMPTY, x, y);
		}

		public SlotWidget getOutputWidget(int x, int y) {
			return new GeneratedSlotWidget((r) -> EmiStack.of(SlimeHelpers.dye(this.stackToDye, getDyes(r).getDyeColor())), this.unique, x, y);
		}

		private DyeItem getDyes(Random random) {
			return DYES.get(random.nextInt(DYES.size()));
		}
	}


}
