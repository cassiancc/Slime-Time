package cc.cassian.slime.client;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.registry.SlimeEntityTypes;
import dev.yumi.mc.core.api.ModContainer;
import dev.yumi.mc.core.api.entrypoint.client.ClientModInitializer;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class SlimeTimeClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer modContainer) {
		EntityRenderers.register(SlimeEntityTypes.SLIMEBALL, ThrownItemRenderer::new);
		ItemTintSources.ID_MAPPER.put(SlimeTime.of("dye"), SlimeDyeTintSource.MAP_CODEC); //fixme neo
	}
}
