package cc.cassian.springs.registry;

import cc.cassian.springs.SpringyThings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.function.Function;

public class SpringyItems {
	public static final Item SPRING_BOOTS = register("spring_boots", Item::new, new Item.Properties().humanoidArmor(SpringyMaterials.SPRINGY, ArmorType.BOOTS).durability(128).attributes(ItemAttributeModifiers.builder().add(SpringyAttributes.BOUNCINESS, new AttributeModifier(SpringyThings.of("spring_boots"), 1f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.FEET).build()));

	public static <T extends Item> T register(ResourceKey<Item> itemKey, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
		// Create the item instance.
		T item = itemFactory.apply(settings.setId(itemKey));

		// Register the item.
		Registry.register(BuiltInRegistries.ITEM, itemKey, item);

		return item;
	}

	public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
		// Create the item key.
		ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, SpringyThings.of(name));
		return register(itemKey, itemFactory, settings);
	}

	public static void touch() {

	}
}
