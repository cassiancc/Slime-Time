package cc.cassian.slime.client.particle;

import cc.cassian.slime.SlimeTime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.data.AtlasIds;
import net.minecraft.util.RandomSource;

public class TintedSlimeParticle extends BreakingItemParticle {

    protected TintedSlimeParticle(ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite) {
        super(level, x, y, z, sprite);
    }

    public static class TintedSlimeProvider implements ParticleProvider<ColorParticleOption> {

        protected TextureAtlasSprite getSprite() {
            return Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(AtlasIds.ITEMS).getSprite(SlimeTime.of("item/slime_ball_dyed"));
        }

        public Particle createParticle(
                final ColorParticleOption options,
                final ClientLevel level,
                final double x,
                final double y,
                final double z,
                final double xAux,
                final double yAux,
                final double zAux,
                final RandomSource random
        ) {
            TintedSlimeParticle particle = new TintedSlimeParticle(level, x, y, z, this.getSprite());
            particle.setColor(options.getRed(), options.getGreen(), options.getBlue());
            return particle;
        }
    }
}
