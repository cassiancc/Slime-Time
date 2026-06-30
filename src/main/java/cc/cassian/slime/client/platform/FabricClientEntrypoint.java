//? fabric {
package cc.cassian.slime.client.platform;

import cc.cassian.slime.client.SlimeTimeClient;
import cc.cassian.slime.client.particle.TintedSlimeParticle;
import cc.cassian.slime.client.screen.WelcomeMessageScreen;
import cc.cassian.slime.registry.SlimeEntityTypes;
import cc.cassian.slime.registry.SlimeItems;
import cc.cassian.slime.registry.SlimeParticleTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.Items;

public class FabricClientEntrypoint implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SlimeTimeClient.onInitializeClient();
        EntityRendererRegistry.register(SlimeEntityTypes.SLIMEBALL, ThrownItemRenderer::new);
        ParticleFactoryRegistry.getInstance().register(SlimeParticleTypes.TINTED_SLIME, new TintedSlimeParticle.TintedSlimeProvider());
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            WelcomeMessageScreen.openWarningScreen(screen);
        });
    }
}
//?}