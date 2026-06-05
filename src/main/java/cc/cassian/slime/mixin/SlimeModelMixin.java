package cc.cassian.slime.mixin;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.util.SlimeHelpers;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SlimeModel.class)
public abstract class SlimeModelMixin {

	@WrapMethod(method = "createOuterBodyLayer")
	private static LayerDefinition translucent(Operation<LayerDefinition> original) {
		if (SlimeTime.CONFIG.colorfulSlimes.colourfulSlimes) {
			MeshDefinition mesh = new MeshDefinition();
			PartDefinition root = mesh.getRoot();

			root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 16.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
					.texOffs(24, 16).addBox(-3.0F, 17.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

			return LayerDefinition.create(mesh, 64, 32);
		} else {
			return original.call();
		}
	}

}