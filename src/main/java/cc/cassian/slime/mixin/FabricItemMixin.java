package cc.cassian.slime.mixin;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.api.Bounciness;
import cc.cassian.slime.registry.SlimeDataComponents;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

import static cc.cassian.slime.SlimeTime.CONFIG;

@Pseudo
//? fabric {
/*@Mixin(net.fabricmc.fabric.api.item.v1.FabricItem.class)
*///?} else {
@Mixin(Item.class)
//?}
public interface FabricItemMixin {
	//? fabric {
	/*@ModifyReturnValue(at = @At(value = "RETURN"), method = "getCreatorNamespace")
	private String vertical(String original, ItemStack stack) {
		if (stack.is(Items.SLIME_BALL) && stack.has(SlimeDataComponents.DYED_COLOR) && original.equals("minecraft")) {
			return SlimeTime.MOD_ID;
		}
		return original;
	}
	*///?}

}