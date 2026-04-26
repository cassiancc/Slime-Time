package cc.cassian.springs.client.data.providers;

import cc.cassian.springs.registry.SpringyItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;

import java.util.concurrent.CompletableFuture;

public class SpringyItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
	public SpringyItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
		super(output, registryLookupFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		valueLookupBuilder(ItemTags.FOOT_ARMOR).add(SpringyItems.SPRING_BOOTS);
	}
}
