package cc.cassian.slime.client.data.providers;

import cc.cassian.slime.tags.SlimeBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class SlimeBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
	public SlimeBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
		super(output, registryLookupFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		builder(SlimeBlockTags.BOUNCY);
		valueLookupBuilder(SlimeBlockTags.SUPPRESSES_BOUNCE).add(Blocks.HONEY_BLOCK).addOptionalTag(TagKey.create(Registries.BLOCK, Identifier.withDefaultNamespace("suppresses_bounce")));
	}
}
