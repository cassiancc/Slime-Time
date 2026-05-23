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

public class RrvServerEntrypoint implements ReliableRecipeViewerPlugin {

	@Override
	public void onIntegrationInitialize() {
		ItemView.addServerReloadCallback(()->{
			if (SlimeTime.CONFIG.slimeTime.colourfulSlimes) {
				SlimeHelpers.dye(Items.SLIME_BALL.getDefaultInstance()).stream().filter(s->s.has(SlimeDataComponents.DYED_COLOR)).forEach(stack -> ItemView.addMobDrops(EntityType.SLIME, SlotContent.of(stack)));
			}
		});
	}
}
