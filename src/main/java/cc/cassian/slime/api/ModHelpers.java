package cc.cassian.slime.api;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.FrogVariant;
import net.minecraft.world.entity.animal.frog.FrogVariants;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ModHelpers {
	public static Item getFroglight(Frog frog) {
		ResourceKey<FrogVariant> variant = frog.getVariant().unwrapKey().get();
		if (variant.equals(FrogVariants.TEMPERATE)) {
			return Items.OCHRE_FROGLIGHT;
		}
		else if (variant.equals(FrogVariants.COLD)) {
			return Items.VERDANT_FROGLIGHT;
		}
		else if (variant.equals(FrogVariants.WARM)) {
			return Items.PEARLESCENT_FROGLIGHT;
		} else if (variant.identifier().equals(Identifier.fromNamespaceAndPath("instantfeedback", "dark"))) {
			return BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath("instantfeedback", "cerulean_froglight"));
		}
		return Items.AIR;
	}
}
