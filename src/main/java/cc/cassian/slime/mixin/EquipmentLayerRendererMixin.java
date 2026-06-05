package cc.cassian.slime.mixin;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.api.SlimeColor;
import cc.cassian.slime.registry.SlimeDataComponents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.resources.model.EquipmentAssetManager;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EquipmentLayerRenderer.class)
public class EquipmentLayerRendererMixin {
    @WrapOperation(require = 0, method = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/EquipmentAssetManager;get(Lnet/minecraft/resources/ResourceKey;)Lnet/minecraft/client/resources/model/EquipmentClientInfo;"))
    private EquipmentClientInfo slimeDyesRender(EquipmentAssetManager instance, ResourceKey<EquipmentAsset> id, Operation<EquipmentClientInfo> original, @Local(name = "itemStack", argsOnly = true) ItemStack stack) {
        if (id.identifier().getNamespace().equals(SlimeTime.MOD_ID)) {
            ResourceKey<EquipmentAsset> equipmentAssetResourceKey = ResourceKey.create(id.registryKey(), id.identifier().withPath(p -> "%s_%s".formatted(stack.getOrDefault(SlimeDataComponents.DYED_COLOR, SlimeColor.LIME).getName(), p)));
            return original.call(instance, equipmentAssetResourceKey);
        } else {
            return original.call(instance, id);
        }
    }
}
