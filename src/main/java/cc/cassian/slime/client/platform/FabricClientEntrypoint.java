//? fabric {
package cc.cassian.slime.client.platform;

import cc.cassian.slime.client.SlimeTimeClient;
import cc.cassian.slime.registry.SlimeEntityTypes;
import cc.cassian.slime.registry.SlimeItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.Items;

public class FabricClientEntrypoint implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SlimeTimeClient.onInitializeClient();
        EntityRendererRegistry.register(SlimeEntityTypes.SLIMEBALL, ThrownItemRenderer::new);
        ColorProviderRegistry.ITEM.register(SlimeTimeClient::calculateTinting, Items.SLIME_BALL, SlimeItems.SLIME_BOOTS, SlimeItems.SLIME_SLING, SlimeItems.SLIME_BUCKET);
    }
}
//?}