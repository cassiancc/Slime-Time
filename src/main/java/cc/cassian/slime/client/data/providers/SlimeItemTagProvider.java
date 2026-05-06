package cc.cassian.slime.client.data.providers;

import cc.cassian.slime.registry.SlimeItems;
import cc.cassian.slime.tags.SlimeItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class SlimeItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
	public SlimeItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
		super(output, registryLookupFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		valueLookupBuilder(ItemTags.FOOT_ARMOR).add(SlimeItems.SLIME_BOOTS);
		valueLookupBuilder(SlimeItemTags.SLIMY_ARMOR).add(SlimeItems.SLIME_BOOTS);
		valueLookupBuilder(SlimeItemTags.THROWABLE_SLIME_BALLS).add(Items.SLIME_BALL);
		valueLookupBuilder(ConventionalItemTags.BUCKETS).add(SlimeItems.SLIME_BUCKET).add(SlimeItems.MAGMA_CUBE_BUCKET);
		valueLookupBuilder(ItemTags.FROG_FOOD).add(SlimeItems.SLIME_BUCKET);
		valueLookupBuilder(ConventionalItemTags.TOOLS).add(SlimeItems.SLIME_SLING);
		valueLookupBuilder(SlimeItemTags.FROGLIGHTS).add(Items.OCHRE_FROGLIGHT, Items.PEARLESCENT_FROGLIGHT, Items.VERDANT_FROGLIGHT);
		builder(SlimeItemTags.FROGLIGHTS).addOptional(key("instantfeedback", "cerulean_froglight"));
	}

	private ResourceKey<Item> key(String namespace, String path) {
		return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(namespace, path));
	}
}
