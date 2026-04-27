package cc.cassian.springs.registry;

import cc.cassian.springs.SpringyThings;
import com.google.common.collect.Maps;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.Map;

public interface SpringyMaterials {
	ArmorMaterial SPRINGY = new ArmorMaterial(25, makeDefense(2, 5, 6, 2, 5), 9, SoundEvents.ARMOR_EQUIP_GENERIC, 0.0F, 0.0F, ItemTags.REPAIRS_IRON_ARMOR, ResourceKey.create(ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset")), SpringyThings.of("springy")));

	static Map<ArmorType, Integer> makeDefense(final int boots, final int legs, final int chest, final int helm, final int body) {
		return Maps.newEnumMap(Map.of(ArmorType.BOOTS, boots, ArmorType.LEGGINGS, legs, ArmorType.CHESTPLATE, chest, ArmorType.HELMET, helm, ArmorType.BODY, body));
	}
}
