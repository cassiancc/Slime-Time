package cc.cassian.slime.client;

import cc.cassian.slime.registry.SlimeDataComponents;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record SlimeDyeTintSource(int defaultColor) implements ItemTintSource {
    public static final MapCodec<SlimeDyeTintSource> MAP_CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(ExtraCodecs.RGB_COLOR_CODEC.fieldOf("default").forGetter(SlimeDyeTintSource::defaultColor)).apply(i, SlimeDyeTintSource::new)
    );

    @Override
    public int calculate(final ItemStack itemStack, @Nullable final ClientLevel level, @Nullable final LivingEntity owner) {
        if (itemStack.has(SlimeDataComponents.DYED_COLOR))
            return itemStack.get(SlimeDataComponents.DYED_COLOR).argb();
        return defaultColor;
    }

    @Override
    public MapCodec<SlimeDyeTintSource> type() {
        return MAP_CODEC;
    }
}
