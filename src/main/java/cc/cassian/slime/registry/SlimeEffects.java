package cc.cassian.slime.registry;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.item.SlimeBucketItem;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.function.Function;

public class SlimeEffects {
	public static final Holder<MobEffect> SLIME_TIME = register(
			"slime_time",
			new SlimeMobEffect(MobEffectCategory.BENEFICIAL, 10092451)
					.addAttributeModifier(SlimeAttributes.BOUNCINESS, SlimeTime.of("effect.slime_time"), 1.0, AttributeModifier.Operation.ADD_VALUE)
	);

	public static class SlimeMobEffect extends MobEffect {
		protected SlimeMobEffect(MobEffectCategory category, int color) {
			super(category, color, ParticleTypes.ITEM_SLIME);
		}
	}

	private static Holder<MobEffect> register(final String name, final MobEffect mobEffect) {
		return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, SlimeTime.of(name), mobEffect);
	}

	public static void touch() {

	}
}
