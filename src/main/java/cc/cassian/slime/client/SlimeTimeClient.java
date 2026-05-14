package cc.cassian.slime.client;

import cc.cassian.slime.registry.SlimeEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class SlimeTimeClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(SlimeEntityTypes.SLIMEBALL, ThrownItemRenderer::new);
	}
}
