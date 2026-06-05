package cc.cassian.slime.mixin;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.api.VariatedSlimeAccess;
//? if >26.1 {
/*import net.minecraft.world.entity.monster.cubemob.*;
*///?} else {
import net.minecraft.world.entity.monster.Slime;
//?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//~ if >26.1 'Slime' -> 'AbstractCubeMob' {
@Mixin(Slime.class)
//~}
public class AbstractCubeMobMixin {
	//? if >26.1 {
	/*@Inject(method = "setUpSplitCube", at = @At(value = "HEAD"))
	private void setVariantOnChildren(AbstractCubeMob cubeMob, int halfSize, float xd, float zd, CallbackInfo ci) {
		if (SlimeTime.CONFIG.colorfulSlimes.colourfulSlimes
				&& cubeMob instanceof VariatedSlimeAccess child && this instanceof VariatedSlimeAccess parent) {
			child.slimeTime$setVariant(parent.slimeTime$getVariant());
		}
	}
	*///?}
}
