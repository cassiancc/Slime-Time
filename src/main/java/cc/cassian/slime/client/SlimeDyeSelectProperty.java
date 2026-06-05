package cc.cassian.slime.client;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.api.SlimeColor;
import cc.cassian.slime.registry.SlimeDataComponents;
import cc.cassian.slime.registry.SlimeItems;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.jspecify.annotations.Nullable;

public record SlimeDyeSelectProperty() implements SelectItemModelProperty<SlimeColor> {
	public static final Codec<SlimeColor> VALUE_CODEC = SlimeColor.CODEC;
	public static final SelectItemModelProperty.Type<SlimeDyeSelectProperty, SlimeColor> TYPE = SelectItemModelProperty.Type.create(MapCodec.unit(new SlimeDyeSelectProperty()), VALUE_CODEC);


	public SlimeColor get(final ItemStack itemStack, @Nullable final ClientLevel level, @Nullable final LivingEntity owner, final int seed, final ItemDisplayContext displayContext) {
		if (itemStack.is(SlimeItems.SLIME_BUCKET)) {
			var entityData = itemStack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY).copyTag();
			if (entityData.contains("SlimeTimeColor")) {
				return SlimeColor.decode(entityData);
			}
		}
		var color = itemStack.get(SlimeDataComponents.DYED_COLOR);
		return color != null ? color : SlimeColor.LIME;
	}

	@Override
	public Type<? extends SelectItemModelProperty<SlimeColor>, SlimeColor> type() {
		return TYPE;
	}

	@Override
	public Codec<SlimeColor> valueCodec() {
		return VALUE_CODEC;
	}
}
