package cc.cassian.springs.registry;

import cc.cassian.springs.SpringyThings;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.EquipmentAssets;

import static net.minecraft.world.item.equipment.ArmorMaterials.makeDefense;

public interface SpringyMaterials {
	ArmorMaterial SPRINGY = new ArmorMaterial(25, makeDefense(2, 5, 6, 2, 5), 9, SoundEvents.ARMOR_EQUIP_GENERIC, 0.0F, 0.0F, ItemTags.REPAIRS_IRON_ARMOR, ResourceKey.create(ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset")), SpringyThings.of("springy")));

}
