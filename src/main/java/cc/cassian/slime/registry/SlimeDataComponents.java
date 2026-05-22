package cc.cassian.slime.registry;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.component.ForceMultiplier;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.component.DyedItemColor;

import java.util.function.UnaryOperator;

public class SlimeDataComponents {
    public static final DataComponentType<ForceMultiplier> FORCE_MULTIPLIER = registerComponentType("force",
            (builder) -> builder.persistent(ForceMultiplier.CODEC).networkSynchronized(ForceMultiplier.STREAM_CODEC));
    public static final DataComponentType<DyeColor> DYED_COLOR = registerComponentType(
            "dyed_color", b -> b.persistent(DyeColor.CODEC).networkSynchronized(DyeColor.STREAM_CODEC)
    );


    public static <T> DataComponentType<T> registerComponentType(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, SlimeTime.of(name), builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void touch() {

    }
}
