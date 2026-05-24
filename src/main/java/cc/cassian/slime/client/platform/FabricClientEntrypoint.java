//? fabric {
package cc.cassian.slime.client.platform;

import cc.cassian.slime.client.SlimeTimeClient;
import cc.cassian.slime.client.particle.TintedSlimeParticle;
import cc.cassian.slime.registry.SlimeParticleTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;

public class FabricClientEntrypoint implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SlimeTimeClient.onInitializeClient();
        ParticleProviderRegistry.getInstance().register(SlimeParticleTypes.TINTED_SLIME, new TintedSlimeParticle.TintedSlimeProvider());
    }
}
//?}