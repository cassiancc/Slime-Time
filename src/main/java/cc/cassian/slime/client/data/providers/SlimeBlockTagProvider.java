//? fabric {
package cc.cassian.slime.client.data.providers;

import cc.cassian.slime.registry.SlimeBlocks;
import cc.cassian.slime.tags.SlimeBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class SlimeBlockTagProvider extends FabricTagProvider.BlockTagProvider {
	public SlimeBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
		super(output, registryLookupFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		getOrCreateTagBuilder(SlimeBlockTags.BOUNCY).add(Blocks.SLIME_BLOCK).addTag(SlimeBlockTags.SLIME_BLOCKS);
		getOrCreateTagBuilder(SlimeBlockTags.SLIME_BLOCKS).addAll(SlimeBlocks.SLIME_BLOCKS.values().stream().map(o->o.builtInRegistryHolder().key()).toList());
		getOrCreateTagBuilder(SlimeBlockTags.SUPPRESSES_BOUNCE).add(Blocks.HONEY_BLOCK).addOptionalTag(TagKey.create(Registries.BLOCK, ResourceLocation.withDefaultNamespace("suppresses_bounce")));
	}
}
//?}