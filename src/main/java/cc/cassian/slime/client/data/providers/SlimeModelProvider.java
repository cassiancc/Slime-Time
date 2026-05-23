//? fabric {
package cc.cassian.slime.client.data.providers;

import cc.cassian.slime.client.SlimeDyeTintSource;
import cc.cassian.slime.registry.SlimeBlocks;
import cc.cassian.slime.registry.SlimeItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.color.item.Dye;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class SlimeModelProvider extends FabricModelProvider {
	public SlimeModelProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
		SlimeBlocks.SLIME_BLOCKS.values().forEach(blockModelGenerators::createNonTemplateModelBlock);
	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerators) {
		generateDyedItem(itemModelGenerators, SlimeItems.SLIME_BOOTS, -7274619);
//		generateDyedItem(itemModelGenerators, SlimeItems.SLIME_BUCKET, -7274619);
		itemModelGenerators.generateFlatItem(SlimeItems.MAGMA_CUBE_BUCKET, ModelTemplates.FLAT_ITEM);
	}

	public final void generateDyedItem(ItemModelGenerators itemModelGenerators, final Item item, final int defaultColor) {
		Identifier model = itemModelGenerators.createFlatItemModel(item, ModelTemplates.FLAT_ITEM);
		itemModelGenerators.itemModelOutput.accept(item, ItemModelUtils.tintedModel(model, new SlimeDyeTintSource(defaultColor)));
	}
}
//?}