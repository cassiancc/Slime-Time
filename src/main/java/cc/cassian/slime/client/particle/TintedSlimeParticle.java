package cc.cassian.slime.client.particle;

import cc.cassian.slime.SlimeTime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.world.item.Items;
import org.jspecify.annotations.Nullable;

public class TintedSlimeParticle extends BreakingItemParticle {

    protected TintedSlimeParticle(ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite) {
        super(level, x, y, z, Items.SLIME_BALL.getDefaultInstance());
        setSprite(sprite);
    }

    public static class TintedSlimeProvider implements ParticleProvider<ColorParticleOption> {

        @Override
        public @Nullable Particle createParticle(ColorParticleOption options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            TintedSlimeParticle particle = new TintedSlimeParticle(level, x, y, z, this.getSprite());
            particle.setColor(options.getRed(), options.getGreen(), options.getBlue());
            return particle;
        }

        private TextureAtlasSprite getSprite() {
            return Minecraft.getInstance().getModelManager().getAtlas(TextureAtlas.LOCATION_BLOCKS).getSprite(SlimeTime.of("item/slime_ball_dyed"));
        }
    }
}
