package cc.cassian.springs.client.data;

import cc.cassian.springs.client.data.providers.SpringyBlockTagProvider;
import cc.cassian.springs.client.data.providers.SpringyItemTagProvider;
import cc.cassian.springs.client.data.providers.SpringyModelProvider;
import cc.cassian.springs.client.data.providers.SpringyRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class SpringyDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(SpringyBlockTagProvider::new);
		pack.addProvider(SpringyItemTagProvider::new);
		pack.addProvider(SpringyModelProvider::new);
		pack.addProvider(SpringyRecipeProvider::new);
	}
}
