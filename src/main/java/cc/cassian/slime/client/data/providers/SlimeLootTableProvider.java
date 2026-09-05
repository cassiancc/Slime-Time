//? fabric {
package cc.cassian.slime.client.data.providers;

import cc.cassian.slime.registry.SlimeBlocks;
import cc.cassian.slime.util.SlimeHelpers;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class SlimeLootTableProvider extends FabricBlockLootTableProvider {

	public SlimeLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
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