//? fabric && >26 {
/*package cc.cassian.slime.client.data.providers;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.api.SlimeColor;
import cc.cassian.slime.client.SlimeDyeSelectProperty;
import cc.cassian.slime.registry.SlimeBlocks;
import cc.cassian.slime.registry.SlimeItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.ModelProvider;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class SlimeModelProvider extends FabricModelProvider {
	public SlimeModelProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
		SlimeBlocks.SLIME_BLOCKS.values().forEach(blockModelGenerators::createNonTemplateModelBlock);
	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerators) {
		generateDyedItem(itemModelGenerators, SlimeItems.SLIME_BOOTS);
		generateDyedItem(itemModelGenerators, SlimeItems.SLIME_SLING);
		generateDyedItem(itemModelGenerators, SlimeItems.SLIME_BUCKET);
		generateDyedItem(itemModelGenerators, Items.SLIME_BALL);
		itemModelGenerators.generateFlatItem(SlimeItems.MAGMA_CUBE_BUCKET, ModelTemplates.FLAT_ITEM);
	}

	public final void generateDyedItem(ItemModelGenerators itemModelGenerators, final Item item) {
		var model = itemModelGenerators.createFlatItemModel(item, ModelTemplates.FLAT_ITEM);

		ArrayList<SelectItemModel.SwitchCase<SlimeColor>> values = new ArrayList<>();

		for (SlimeColor value : SlimeColor.values()) {
			var coloredModel = SlimeTime.of(model.withPath(p->p.replace("/", "/"+value.getName() + "_")).getPath());
			ModelTemplates.FLAT_ITEM.create(coloredModel, TextureMapping.layer0(new Material(coloredModel)), itemModelGenerators.modelOutput);
			values.add(ItemModelUtils.when(value, ItemModelUtils.plainModel(coloredModel)));
		}

		itemModelGenerators.itemModelOutput.accept(item, ItemModelUtils.select(
				new SlimeDyeSelectProperty(),  ItemModelUtils.plainModel(model), values
		));

	}
}
*///?}