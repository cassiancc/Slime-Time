package cc.cassian.slime.client.data.providers;

import cc.cassian.slime.registry.SlimeItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;

import java.util.concurrent.CompletableFuture;

public class SlimeItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
	public SlimeItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
		super(output, registryLookupFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		valueLookupBuilder(ItemTags.FOOT_ARMOR).add(SlimeItems.SLIME_BOOTS);
	}
}
