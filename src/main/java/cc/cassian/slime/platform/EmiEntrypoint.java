package cc.cassian.slime.platform;

import cc.cassian.slime.registry.SlimeItems;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiInfoRecipe;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.List;

public class EmiEntrypoint implements EmiPlugin {

	private EmiInfoRecipe getInfoRecipe(Item slimeBucket) {
		Identifier key = BuiltInRegistries.ITEM.getKey(slimeBucket);
		return new EmiInfoRecipe(List.of(EmiStack.of(slimeBucket)), List.of(Component.translatable(slimeBucket.getDescriptionId().replace("item", "lore"))), key);
	}

	@Override
	public void register(EmiRegistry emiRegistry) {
		emiRegistry.addRecipe(getInfoRecipe(SlimeItems.SLIME_BUCKET));
		emiRegistry.addRecipe(getInfoRecipe(SlimeItems.MAGMA_CUBE_BUCKET));
	}
}
