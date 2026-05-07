package cc.cassian.slime.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import org.jspecify.annotations.NullMarked;

import java.util.function.Consumer;

@NullMarked
public record ForceMultiplier(float horizontalForce, float verticalForce) implements TooltipProvider {
    public static final Codec<ForceMultiplier> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Codec.FLOAT.fieldOf("horizontal_force").forGetter(ForceMultiplier::horizontalForce),
                            Codec.FLOAT.fieldOf("vertical_force").forGetter(ForceMultiplier::verticalForce)
                    )
                    .apply(instance, ForceMultiplier::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, ForceMultiplier> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            ForceMultiplier::horizontalForce,
            ByteBufCodecs.FLOAT,
            ForceMultiplier::verticalForce,
            ForceMultiplier::new
    );

    public static final ForceMultiplier DEFAULT = new ForceMultiplier(1.5f, 1.2f);

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> consumer, TooltipFlag flag, DataComponentGetter components) {
        consumer.accept(Component.empty());
        consumer.accept(Component.translatable("slime_time.modifiers.used").withStyle(ChatFormatting.GRAY));
        consumer.accept(Component.translatable("component.slime_sling.horizontal_force", horizontalForce).withStyle(ChatFormatting.DARK_GREEN));
        consumer.accept(Component.translatable("component.slime_sling.vertical_force", verticalForce).withStyle(ChatFormatting.DARK_GREEN));
    }
}