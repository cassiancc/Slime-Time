package cc.cassian.slime.mixin;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.api.SlimeColor;
import cc.cassian.slime.registry.SlimeDataComponents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HumanoidArmorLayer.class)
public class EquipmentLayerRendererMixin {

    //? fabric {
    @WrapOperation(require = 0, method = "renderArmorPiece", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/HumanoidArmorLayer;renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/model/HumanoidModel;ILnet/minecraft/resources/ResourceLocation;)V"))
    private void slimeDyesRender(HumanoidArmorLayer instance, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, HumanoidModel model, int dyeColor, ResourceLocation id, Operation<Void> original, @Local ItemStack stack) {
        if (id.getNamespace().equals(SlimeTime.MOD_ID)) {
            var equipmentAssetResourceKey = id.withPath(p -> p.replace("slime", (stack.getOrDefault(SlimeDataComponents.DYED_COLOR, SlimeColor.LIME).getName() + "_slime")));
            original.call(instance, poseStack, multiBufferSource, packedLight, model, dyeColor, equipmentAssetResourceKey);
        } else {
            original.call(instance, poseStack, multiBufferSource, packedLight, model, dyeColor, id);
        }
    }
    //?}
}
