package cc.cassian.slime.mixin;

import cc.cassian.slime.registry.SlimeAttributes;
import cc.cassian.slime.tags.SlimeBlockTags;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(PistonMovingBlockEntity.class)
public class PistonMovingBlockEntityMixin {
	@WrapOperation(method = "moveCollidedEntities", require = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z"))
	private static boolean vertical(BlockState instance, Object o, Operation<Boolean> original, @Local(name = "entities") List<Entity> entities) {
		return original.call(instance, o) || instance.is(SlimeBlockTags.BOUNCY) || entities.stream().anyMatch(e->e.slime$getEntityBounciness()>0);
	}
}
