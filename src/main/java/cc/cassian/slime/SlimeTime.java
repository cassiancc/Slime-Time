package cc.cassian.slime;

import cc.cassian.slime.registry.*;

import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

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
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(event -> {
			event.addAfter(Items.TURTLE_HELMET, SlimeItems.SLIME_BOOTS);
			if (SlimeTime.CONFIG.slimeTime.addSlimeBallToCombatTab)
				event.addAfter(Items.SNOWBALL, Items.SLIME_BALL);
		});
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(event -> {
			event.addAfter(Items.TADPOLE_BUCKET, SlimeItems.SLIME_BUCKET);
			event.addAfter(SlimeItems.SLIME_BUCKET.getDefaultInstance(), SlimeItems.MAGMA_CUBE_BUCKET);
			event.addBefore(Items.SADDLE, SlimeItems.SLIME_SLING);
		});
		ItemTooltipCallback.EVENT.register((stack, tooltipContext, tooltipType, lines) -> {
			if (stack.has(SlimeDataComponents.FORCE_MULTIPLIER)) {
				Objects.requireNonNull(stack.get(SlimeDataComponents.FORCE_MULTIPLIER)).addToTooltip(tooltipContext, lines::add, tooltipType);
			}
		});
	}
}