package cc.cassian.slime.registry;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.component.ForceMultiplier;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.UnaryOperator;

public class SlimeDataComponents {
    public static final DataComponentType<ForceMultiplier> FORCE_MULTIPLIER = registerComponentType("size",
            (builder) -> builder.persistent(ForceMultiplier.CODEC).networkSynchronized(ForceMultiplier.STREAM_CODEC));

    public static <T> DataComponentType<T> registerComponentType(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, SlimeTime.of(name), builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void touch() {

    }
}
