package cc.cassian.slime.client.data.providers;

import cc.cassian.slime.registry.SlimeItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class SlimeModelProvider extends FabricModelProvider {
	public SlimeModelProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {

	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerators) {
		itemModelGenerators.generateFlatItem(SlimeItems.SLIME_BOOTS, ModelTemplates.FLAT_ITEM);
		itemModelGenerators.generateFlatItem(SlimeItems.SLIME_BUCKET, ModelTemplates.FLAT_ITEM);
		itemModelGenerators.generateFlatItem(SlimeItems.MAGMA_CUBE_BUCKET, ModelTemplates.FLAT_ITEM);
		itemModelGenerators.generateFlatItem(SlimeItems.SLIME_SLING, ModelTemplates.FLAT_HANDHELD_ITEM);
	}
}
