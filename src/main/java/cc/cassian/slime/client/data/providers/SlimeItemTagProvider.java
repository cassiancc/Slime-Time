//? fabric {
package cc.cassian.slime.client.data.providers;

import cc.cassian.slime.registry.SlimeItems;
import cc.cassian.slime.tags.SlimeBlockTags;
import cc.cassian.slime.tags.SlimeItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class SlimeItemTagProvider extends FabricTagProvider.ItemTagProvider {
	public SlimeItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture, SlimeBlockTagProvider blockTags) {
		super(output, registryLookupFuture, blockTags);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		tagBuilder(ItemTags.FOOT_ARMOR).add(SlimeItems.SLIME_BOOTS);
		tagBuilder(SlimeItemTags.SLIMY_ARMOR).add(SlimeItems.SLIME_BOOTS);
		tagBuilder(SlimeItemTags.THROWABLE_SLIME_BALLS).add(Items.SLIME_BALL);
		tagBuilder(ConventionalItemTags.BUCKETS).add(SlimeItems.SLIME_BUCKET).add(SlimeItems.MAGMA_CUBE_BUCKET);
		tagBuilder(ItemTags.FROG_FOOD).add(SlimeItems.SLIME_BUCKET);
		tagBuilder(ConventionalItemTags.TOOLS).add(SlimeItems.SLIME_SLING);
		tagBuilder(SlimeItemTags.FROGLIGHTS).add(Items.OCHRE_FROGLIGHT, Items.PEARLESCENT_FROGLIGHT, Items.VERDANT_FROGLIGHT);
		tagBuilder(SlimeItemTags.FROGLIGHTS).addOptional(key("instantfeedback", "cerulean_froglight"));
		copy(SlimeBlockTags.SLIME_BLOCKS, SlimeItemTags.SLIME_BLOCKS);
		tagBuilder(SlimeItemTags.DYEABLE_SLIME).add(SlimeItems.SLIME_BOOTS, SlimeItems.SLIME_SLING, Items.SLIME_BALL);
		tagBuilder(ConventionalItemTags.STORAGE_BLOCKS_SLIME).addOptionalTag(SlimeItemTags.SLIME_BLOCKS);
	}


	private SlimeTimeTagBuilder tagBuilder(TagKey<Item> tag) {
		return new SlimeTimeTagBuilder(tag);
	}

	public class SlimeTimeTagBuilder {
		//? if >26.1 {
		/*private TagAppender<Item> valueLookupBuilder;
		*///?} else if >1.21.2 {
		/*private TagAppender<Item, Item> valueLookupBuilder;
		*///?} else {
		private FabricTagProvider<Item>.FabricTagBuilder valueLookupBuilder;
		 //?}

		private TagBuilder rawBuilder;

		public SlimeTimeTagBuilder(TagKey<Item> tag) {
			//? if >26.1 {
			/*this.valueLookupBuilder = builder(tag);
			*///?} else if >1.21.2 {
			/*this.valueLookupBuilder = valueLookupBuilder(tag);
			*///?} else {
			this.valueLookupBuilder = getOrCreateTagBuilder(tag);
			 //?}

			this.rawBuilder = getOrCreateRawBuilder(tag);
		}

		public SlimeTimeTagBuilder add(Item item) {
			//? <26.2 {
			valueLookupBuilder = valueLookupBuilder.add(item);
			//?} else {
			/*valueLookupBuilder = valueLookupBuilder.add(item.builtInRegistryHolder().key());
			*///?}
			return this;
		}

		public SlimeTimeTagBuilder addOptionalTag(TagKey<Item> itemTagKey) {
			valueLookupBuilder = valueLookupBuilder.addOptionalTag(itemTagKey);
			return this;
		}

		public SlimeTimeTagBuilder add(Item... items) {
			//? <26.2 {
			valueLookupBuilder = valueLookupBuilder.add(items);
			//?} else {
			/*valueLookupBuilder.addAll(Arrays.stream(items).map(item -> item.builtInRegistryHolder().key()));
			*///?}
			return this;
		}

		public SlimeTimeTagBuilder addOptional(ResourceLocation item) {
			rawBuilder = rawBuilder.addOptionalElement(item);
			return this;
		}
	}

	private ResourceLocation key(String namespace, String path) {
		return ResourceLocation.fromNamespaceAndPath(namespace, path);
	}
}
//?}
