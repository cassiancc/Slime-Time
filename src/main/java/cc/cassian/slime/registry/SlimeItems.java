package cc.cassian.slime.registry;

import cc.cassian.slime.SlimeTime;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.function.Function;

public class SlimeItems {
	public static final Item SLIME_BOOTS = register("slime_boots", Item::new, new Item.Properties().humanoidArmor(SlimeMaterials.SLIME, ArmorType.BOOTS).durability(128)
			.attributes(ItemAttributeModifiers.builder()
					.add(SlimeAttributes.BOUNCINESS, new AttributeModifier(SlimeTime.of("spring_boots"), 1f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.FEET)
					.add(Attributes.SAFE_FALL_DISTANCE, new AttributeModifier(SlimeTime.of("spring_boots"), 2f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.FEET)
					.build()));

	public static <T extends Item> T register(ResourceKey<Item> itemKey, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
		// Create the item instance.
		T item = itemFactory.apply(settings.setId(itemKey));

		// Register the item.
		Registry.register(BuiltInRegistries.ITEM, itemKey, item);

		return item;
	}

	public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
		// Create the item key.
		ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, SlimeTime.of(name));
		return register(itemKey, itemFactory, settings);
	}

	public static void touch() {

	}
}
