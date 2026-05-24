package cc.cassian.slime.mixin;

import cc.cassian.slime.registry.SlimeDataComponents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HumanoidArmorLayer.class)
public class EquipmentLayerRendererMixin {
    @WrapOperation(require = 0, method = "renderArmorPiece", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/DyedItemColor;getOrDefault(Lnet/minecraft/world/item/ItemStack;I)I"))
    private int slimeDyesRender(ItemStack itemStack, int defaultColor, Operation<Integer> original) {
        if (itemStack.has(SlimeDataComponents.DYED_COLOR)) {
            return itemStack.get(SlimeDataComponents.DYED_COLOR).argb();
        }
        return original.call(itemStack, defaultColor);
    }
}
