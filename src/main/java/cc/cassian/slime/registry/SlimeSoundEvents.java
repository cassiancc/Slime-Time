package cc.cassian.slime.registry;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.item.SlimeBucketItem;
import cc.cassian.slime.item.SlimeSlingItem;
import cc.cassian.slime.tags.SlimeItemTags;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.UseRemainder;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.function.Function;

public interface SlimeSoundEvents {
	SoundEvent SLIME_SLING_FLING = register("slime_time.slime_sling.fling");

	private static SoundEvent register(final String id) {
		return register(SlimeTime.of(id));
	}

	private static SoundEvent register(final Identifier id) {
		return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
	}

	static void touch() {

	}
}
