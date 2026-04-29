package cc.cassian.slime;

import cc.cassian.slime.registry.*;

import dev.yumi.mc.core.api.ModContainer;
import dev.yumi.mc.core.api.YumiMods;
import dev.yumi.mc.core.api.entrypoint.ModInitializer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NullMarked
public class SlimeTime implements ModInitializer {
	public static final String MOD_ID = "slime_time";
	public static final ModConfig CONFIG = ModConfig.createToml(YumiMods.get().getConfigDirectory(), "", MOD_ID, ModConfig.class);
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Identifier of(String name) {
		return Identifier.fromNamespaceAndPath(MOD_ID, name);
	}

	@Override
	public void onInitialize(ModContainer mod) {
		LOGGER.info("Slime the world!");
		SlimeAttributes.touch();
		SlimeItems.touch();
		SlimeEntityTypes.touch();
		SlimeGameEvents.touch();
		SlimeEffects.touch();
	}
}