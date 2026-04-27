package cc.cassian.slime.client;

import cc.cassian.slime.registry.SlimeEntityTypes;
import dev.yumi.mc.core.api.ModContainer;
import dev.yumi.mc.core.api.entrypoint.client.ClientModInitializer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class SlimeTimeClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer modContainer) {
		EntityRenderers.register(SlimeEntityTypes.SLIMEBALL, ThrownItemRenderer::new);
	}
}
