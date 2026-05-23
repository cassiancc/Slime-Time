//? fabric {
package cc.cassian.slime.client.data;

import cc.cassian.slime.client.data.providers.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class SlimeDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		//? if <26.2 {
		
		pack.addProvider(SlimeBlockTagProvider::new);
		pack.addProvider(SlimeItemTagProvider::new);
		pack.addProvider(SlimeModelProvider::new);
		pack.addProvider(SlimeRecipeProvider::new);
		//?}
	}
}
//?}