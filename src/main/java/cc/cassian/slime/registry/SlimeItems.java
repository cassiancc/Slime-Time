package cc.cassian.slime.registry;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.component.ForceMultiplier;
import cc.cassian.slime.item.SlimeArmorItem;
import cc.cassian.slime.item.SlimeBucketItem;
import cc.cassian.slime.item.SlimeSlingItem;
import cc.cassian.slime.tags.SlimeItemTags;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
//? if >26.1
//import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.function.Function;

public interface SlimeItems {
	Item SLIME_BOOTS = register("slime_boots", p->new SlimeArmorItem(SlimeMaterials.SLIME, ArmorItem.Type.BOOTS, p), new Item.Properties().durability(128)
			.attributes(ItemAttributeModifiers.builder()
					.add(SlimeAttributes.BOUNCINESS, new AttributeModifier(SlimeTime.of("slime_boots"), 1f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.FEET)
					.build()));

	Item SLIME_BUCKET = register("slime_bucket", (p) -> new SlimeBucketItem(EntityType.SLIME, SoundEvents.BUCKET_EMPTY_TADPOLE, p), (new Item.Properties()).stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY).craftRemainder(Items.BUCKET));
	Item MAGMA_CUBE_BUCKET = register("magma_cube_bucket", (p) -> new SlimeBucketItem(EntityType.MAGMA_CUBE, SoundEvents.BUCKET_EMPTY_TADPOLE, p), (new Item.Properties()).stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY).craftRemainder(Items.BUCKET));
	Item SLIME_SLING = register("slime_sling", SlimeSlingItem::new, new Item.Properties().component(SlimeDataComponents.FORCE_MULTIPLIER, ForceMultiplier.DEFAULT).durability(128).stacksTo(1));


	static <T extends Item> T register(ResourceKey<Item> itemKey, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
		// Create the item instance.
		T item = itemFactory.apply(settings);

		// Register the item.
		Registry.register(BuiltInRegistries.ITEM, itemKey, item);

		return item;
	}

	static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
		return register(keyOfItem(name), itemFactory, settings);
	}

	static ResourceKey<Item> keyOfItem(String name) {
		return ResourceKey.create(Registries.ITEM, SlimeTime.of(name));
	}

	static void touch() {

	}
}
