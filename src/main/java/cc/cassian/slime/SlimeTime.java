package cc.cassian.slime;

import cc.cassian.slime.registry.*;

import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NullMarked
public class SlimeTime {
	public static final String MOD_ID = "slime_time";
	public static final ModConfig CONFIG = ModConfig.createToml(Platform.getConfigDirectory(), "", MOD_ID, ModConfig.class);
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Identifier of(String name) {
		return Identifier.fromNamespaceAndPath(MOD_ID, name);
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
	}
}