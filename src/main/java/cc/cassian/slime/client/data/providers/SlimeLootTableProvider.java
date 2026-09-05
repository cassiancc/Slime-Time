//? fabric {
package cc.cassian.slime.client.data.providers;

import cc.cassian.slime.registry.SlimeBlocks;
import cc.cassian.slime.util.SlimeHelpers;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class SlimeLootTableProvider extends FabricBlockLootSubProvider {

	public SlimeLootTableProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void generate() {
		SlimeBlocks.SLIME_BLOCKS.forEach((slimeColor, slimeBlock) -> {
			dropSelf(slimeBlock);
		});
	}
}
//?}