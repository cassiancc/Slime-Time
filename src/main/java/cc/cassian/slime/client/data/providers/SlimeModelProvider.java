//? fabric {
package cc.cassian.slime.client.data.providers;

import cc.cassian.slime.registry.SlimeBlocks;
import cc.cassian.slime.registry.SlimeItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.ModelProvider;
import net.minecraft.data.models.model.ModelTemplates;

import java.util.concurrent.CompletableFuture;

public class SlimeModelProvider extends FabricModelProvider {
	public SlimeModelProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
		SlimeBlocks.SLIME_BLOCKS.values().forEach(blockModelGenerators::createNonTemplateModelBlock);
	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerators) {
		itemModelGenerators.generateFlatItem(SlimeItems.SLIME_BOOTS, ModelTemplates.FLAT_ITEM);
//		itemModelGenerators.generateFlatItem(SlimeItems.SLIME_BUCKET, ModelTemplates.FLAT_ITEM);
		itemModelGenerators.generateFlatItem(SlimeItems.MAGMA_CUBE_BUCKET, ModelTemplates.FLAT_ITEM);
//		itemModelGenerators.generateFlatItem(SlimeItems.SLIME_SLING, ModelTemplates.FLAT_HANDHELD_ITEM);
	}
}
//?}