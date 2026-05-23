package cc.cassian.slime.registry;

import cc.cassian.slime.SlimeTime;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class SlimeEffects {
	public static Holder<MobEffect> SLIME_TIME = register(
			"slime_time",
			new SlimeMobEffect(MobEffectCategory.BENEFICIAL, 10092451)
					.addAttributeModifier(SlimeAttributes.BOUNCINESS, SlimeTime.of("effect.slime_time"), 1.0, AttributeModifier.Operation.ADD_VALUE)
	);

	private static class SlimeMobEffect extends MobEffect {
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
