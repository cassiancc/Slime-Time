package cc.cassian.slime.item;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.api.SlimeColor;
import cc.cassian.slime.registry.SlimeDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class SlimeArmorItem extends ArmorItem {
	public SlimeArmorItem(Holder<ArmorMaterial> slime, ArmorItem.Type type, Item.Properties p) {
		super(slime, type, p);
	}

	//? neoforge {
	/*@Override
	public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
		return SlimeTime.of("textures/models/armor/%s_slime_layer_1.png".formatted(stack.getOrDefault(SlimeDataComponents.DYED_COLOR, SlimeColor.LIME).getName()));
	}
	*///?}
}
