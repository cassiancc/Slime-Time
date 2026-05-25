//? fabric {
package cc.cassian.slime.client.data.providers;

import cc.cassian.slime.registry.SlimeBlocks;
import cc.cassian.slime.tags.SlimeBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class SlimeBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
	public SlimeBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
		super(output, registryLookupFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		tagBuilder(SlimeBlockTags.SUPPRESSES_BOUNCE).add(Blocks.HONEY_BLOCK).addOptionalTag(TagKey.create(Registries.BLOCK, Identifier.withDefaultNamespace("suppresses_bounce")));
		tagBuilder(SlimeBlockTags.BOUNCY).add(Blocks.SLIME_BLOCK).addTag(SlimeBlockTags.SLIME_BLOCKS);
		var slimeBlocks = tagBuilder(SlimeBlockTags.SLIME_BLOCKS);
		SlimeBlocks.SLIME_BLOCKS.values().forEach(slimeBlocks::addOptional);
	}

	private SlimeTimeTagBuilder tagBuilder(TagKey<Block> tag) {
		return new SlimeTimeTagBuilder(tag);
	}

	public class SlimeTimeTagBuilder {
		//? if >26.1 {
		/*private TagAppender<Block> valueLookupBuilder;
		*///?} else if >1.21.2 {
		private TagAppender<Block, Block> valueLookupBuilder;
		 //?} else {
		/*private FabricTagProvider<Block>.FabricTagBuilder valueLookupBuilder;
		 *///?}

		private TagBuilder rawBuilder;

		public SlimeTimeTagBuilder(TagKey<Block> tag) {
			//? if >26.1 {
			/*this.valueLookupBuilder = builder(tag);
			*///?} else if >1.21.2 {
			this.valueLookupBuilder = valueLookupBuilder(tag);
			 //?} else {
			/*this.valueLookupBuilder = getOrCreateTagBuilder(tag);
			 *///?}

			this.rawBuilder = getOrCreateRawBuilder(tag);
		}

		public SlimeTimeTagBuilder add(Block item) {
			//? <26.2 {
			valueLookupBuilder = valueLookupBuilder.add(item);
			//?} else {
			/*valueLookupBuilder = valueLookupBuilder.add(item.builtInRegistryHolder().key());
			*///?}
			return this;
		}

		public SlimeTimeTagBuilder addOptional(Block item) {
			//? <26.2 {
			valueLookupBuilder = valueLookupBuilder.addOptional(item);
			//?} else {
			/*valueLookupBuilder = valueLookupBuilder.addOptional(item.builtInRegistryHolder().key());
			*///?}
			return this;
		}

		public SlimeTimeTagBuilder addTag(TagKey<Block> itemTagKey) {
			valueLookupBuilder = valueLookupBuilder.addTag(itemTagKey);
			return this;
		}

		public SlimeTimeTagBuilder addOptionalTag(TagKey<Block> itemTagKey) {
			valueLookupBuilder = valueLookupBuilder.addOptionalTag(itemTagKey);
			return this;
		}

		public SlimeTimeTagBuilder add(Block... items) {
			//? <26.2 {
			valueLookupBuilder = valueLookupBuilder.add(items);
			//?} else {
			/*valueLookupBuilder.addAll(Arrays.stream(items).map(item -> item.builtInRegistryHolder().key()));
			*///?}
			return this;
		}

		public SlimeTimeTagBuilder addOptional(Identifier item) {
			rawBuilder = rawBuilder.addOptionalElement(item);
			return this;
		}

		public SlimeTimeTagBuilder addAll(Stream<Block> items) {
			//? <26.2 {
			valueLookupBuilder = valueLookupBuilder.addAll(items);
			//?} else {
			/*items.map(i->i.builtInRegistryHolder().key()).forEach(valueLookupBuilder::add);
			*///?}
			return this;
		}
	}
}
//?}