package cc.cassian.slime.platform;

import cc.cassian.rrv.api.ReliableRecipeViewerPlugin;
import cc.cassian.rrv.api.recipe.ItemView;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.registry.SlimeDataComponents;
import cc.cassian.slime.util.SlimeHelpers;
import net.minecraft.world.entity.EntityType;
//? if >=26.2
//import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Items;

import static cc.cassian.slime.util.SlimeHelpers.dye;

public class RrvServerEntrypoint implements ReliableRecipeViewerPlugin {

	@Override
	public void onIntegrationInitialize() {
		ItemView.addServerReloadCallback(()->{
			if (SlimeTime.CONFIG.colorfulSlimes.colourfulSlimes) {
				ItemView.modifyMobDrops((entityType)->entityType.equals(EntityType.SLIME), (slotContent)->slotContent.contains(Items.SLIME_BALL), SlotContent.of(dye(Items.SLIME_BALL.getDefaultInstance(), true)));
			}
		});
	}
}
