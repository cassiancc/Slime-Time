package cc.cassian.slime;

import cc.cassian.slime.platform.FieldGuideEntrypoint;
import cc.cassian.slime.registry.*;

import net.minecraft.resources.ResourceLocation;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@NullMarked
public class SlimeTime {
	public static final String MOD_ID = "slime_time";
	public static final ModConfig CONFIG = ModConfig.createToml(Platform.getConfigDirectory(), "", MOD_ID, ModConfig.class);
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static ResourceLocation of(String name) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
	}

	public static void onInitialize() {
		LOGGER.info("Slime the world!");
		SlimeAttributes.touch();
		SlimeDataComponents.touch();
		SlimeBlocks.touch();
		SlimeRecipes.touch();
		SlimeItems.touch();
		SlimeEntityTypes.touch();
		SlimeGameEvents.touch();
		SlimeEffects.touch();
		SlimeSoundEvents.touch();
		SlimeParticleTypes.touch();
		if (Platform.isModLoaded("fieldguide")) {
			FieldGuideEntrypoint.registerProvider();
		}
	}
}