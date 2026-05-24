package cc.cassian.slime.registry;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.tags.SlimeItemTags;
import dev.emi.emi.api.EmiEntrypoint;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.Util;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

@EmiEntrypoint
public interface SlimeMaterials {
	Holder<ArmorMaterial> SLIME = register("slime", Util.make(new EnumMap<>(ArmorItem.Type.class), (enumMap) -> {
		enumMap.put(ArmorItem.Type.BOOTS, 1);
		enumMap.put(ArmorItem.Type.LEGGINGS, 2);
		enumMap.put(ArmorItem.Type.CHESTPLATE, 3);
		enumMap.put(ArmorItem.Type.HELMET, 1);
		enumMap.put(ArmorItem.Type.BODY, 3);
	}), 15, SoundEvents.ARMOR_EQUIP_GENERIC, 0.0F, 0.0F, () -> Ingredient.of(SlimeItemTags.SLIME_BALLS), List.of(new ArmorMaterial.Layer(SlimeTime.of("slime"), "", false)));



	private static Holder<ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> defense, int enchantmentValue, Holder<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngridient, List<ArmorMaterial.Layer> layers) {
		EnumMap<ArmorItem.Type, Integer> enumMap = new EnumMap<>(ArmorItem.Type.class);

		for(ArmorItem.Type type : ArmorItem.Type.values()) {
			enumMap.put(type, defense.get(type));
		}

		return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, SlimeTime.of(name), new ArmorMaterial(enumMap, enchantmentValue, equipSound, repairIngridient, layers, toughness, knockbackResistance));
	}

}
