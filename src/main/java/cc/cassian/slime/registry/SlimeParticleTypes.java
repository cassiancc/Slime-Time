package cc.cassian.slime.registry;

import cc.cassian.slime.SlimeTime;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Function;

public class SlimeParticleTypes {
    public static final ParticleType<ColorParticleOption> TINTED_SLIME = register(
            "tinted_slime", false, ColorParticleOption::codec, ColorParticleOption::streamCodec
    );

    private static <T extends ParticleOptions> ParticleType<T> register(
            final String name,
            final boolean overrideLimiter,
            final Function<ParticleType<T>, MapCodec<T>> codec,
            final Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> streamCodec
    ) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, SlimeTime.of(name), new ParticleType<T>(overrideLimiter) {
            @Override
            public MapCodec<T> codec() {
                return codec.apply(this);
            }

            @Override
            public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
                return streamCodec.apply(this);
            }
        });
    }

    public static void touch() {}
}
