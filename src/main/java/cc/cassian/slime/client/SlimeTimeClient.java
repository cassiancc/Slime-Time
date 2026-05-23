package cc.cassian.slime.client;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.registry.SlimeEntityTypes;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class SlimeTimeClient {

	public static void onInitializeClient() {
		EntityRenderers.register(SlimeEntityType.SLIMEBALL, ThrownItemRenderer::new);
		ItemTintSources.ID_MAPPER.put(SlimeTime.of("dye"), SlimeDyeTintSource.MAP_CODEC); //fixme neo
	}
}
