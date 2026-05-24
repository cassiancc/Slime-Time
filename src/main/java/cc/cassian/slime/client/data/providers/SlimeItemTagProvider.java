package cc.cassian.slime.client.data.providers;

import cc.cassian.slime.registry.SlimeBlocks;
import cc.cassian.slime.registry.SlimeItems;
import cc.cassian.slime.tags.SlimeItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlimeBlock;

import java.util.concurrent.CompletableFuture;

public class SlimeItemTagProvider extends FabricTagProvider.ItemTagProvider {
	public SlimeItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
		super(output, registryLookupFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		getOrCreateTagBuilder(ItemTags.FOOT_ARMOR).add(SlimeItems.SLIME_BOOTS);
		getOrCreateTagBuilder(SlimeItemTags.SLIMY_ARMOR).add(SlimeItems.SLIME_BOOTS);
		getOrCreateTagBuilder(SlimeItemTags.THROWABLE_SLIME_BALLS).add(Items.SLIME_BALL);
		getOrCreateTagBuilder(ConventionalItemTags.BUCKETS).add(SlimeItems.SLIME_BUCKET).add(SlimeItems.MAGMA_CUBE_BUCKET);
		getOrCreateTagBuilder(ItemTags.FROG_FOOD).add(SlimeItems.SLIME_BUCKET);
		getOrCreateTagBuilder(ConventionalItemTags.TOOLS).add(SlimeItems.SLIME_SLING);
		getOrCreateTagBuilder(SlimeItemTags.FROGLIGHTS).add(Items.OCHRE_FROGLIGHT, Items.PEARLESCENT_FROGLIGHT, Items.VERDANT_FROGLIGHT);
		getOrCreateTagBuilder(SlimeItemTags.FROGLIGHTS).addOptional(key("instantfeedback", "cerulean_froglight"));
		getOrCreateTagBuilder(SlimeItemTags.SLIME_BLOCKS).addAll(SlimeBlocks.SLIME_BLOCKS.values().stream().map((SlimeBlock slimeBlock) -> slimeBlock.asItem().builtInRegistryHolder().key()).toList());
		getOrCreateTagBuilder(SlimeItemTags.DYEABLE_SLIME).add(SlimeItems.SLIME_BOOTS, SlimeItems.SLIME_SLING, Items.SLIME_BALL);

	}

	private ResourceKey<Item> key(String namespace, String path) {
		return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(namespace, path));
	}
}
