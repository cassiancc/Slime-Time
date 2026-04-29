package cc.cassian.slime.registry;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.entity.SlimeballEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public interface SlimeEntityTypes {
	EntityType<SlimeballEntity> SLIMEBALL = register(
			"slimeball", EntityType.Builder.<SlimeballEntity>of(SlimeballEntity::new, MobCategory.MISC).noLootTable().sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
	);

	private static <T extends Entity> EntityType<T> register(final ResourceKey<EntityType<?>> id, final EntityType.Builder<T> builder) {
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, id, builder.build(id));
	}

	private static ResourceKey<EntityType<?>> key(final String vanillaId) {
		return ResourceKey.create(Registries.ENTITY_TYPE, SlimeTime.of(vanillaId));
	}

	private static <T extends Entity> EntityType<T> register(final String id, final EntityType.Builder<T> builder) {
		return register(key(id), builder);
	}

	static void touch() {

	}
}
