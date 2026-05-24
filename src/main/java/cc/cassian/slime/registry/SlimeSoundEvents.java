package cc.cassian.slime.registry;

import cc.cassian.slime.SlimeTime;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public interface SlimeSoundEvents {
	SoundEvent SLIME_SLING_FLING = register("slime_time.slime_sling.fling");
	SoundEvent SLIME_SLING_SNAP = register("slime_time.slime_sling.snap");
	SoundEvent SLIMY_BOUNCE = register("slime_time.slimy_bounce");
	SoundEvent BUCKET_FILL_SLIME = register("slime_time.bucket.fill_slime");
	SoundEvent BUCKET_FILL_MAGMA_CUBE = register("slime_time.bucket.fill_magma_cube");


	private static SoundEvent register(final String id) {
		return register(SlimeTime.of(id));
	}

	private static SoundEvent register(final ResourceLocation id) {
		return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
	}

	static void touch() {

	}
}
