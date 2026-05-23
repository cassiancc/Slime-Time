package cc.cassian.slime.mixin;

import cc.cassian.slime.api.SlimeEntity;
import cc.cassian.slime.registry.SlimeAttributes;
import cc.cassian.slime.tags.SlimeBlockTags;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(PistonMovingBlockEntity.class)
public class PistonMovingBlockEntityMixin {
	@WrapOperation(method = "moveCollidedEntities", require = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
	private static boolean vertical(BlockState instance, Block block, Operation<Boolean> original, @Local(ordinal = 0) List<Entity> entities) {
		return original.call(instance, block) || instance.is(SlimeBlockTags.BOUNCY) || entities.stream().anyMatch(e->((SlimeEntity) e).slime$getEntityBounciness()>0);
	}
	//?} else {
    /*@WrapOperation(method = "moveCollidedEntities", require = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isSlimeBlock()Z"))
    private static boolean vertical(BlockState instance, Operation<Boolean> original, @Local(name = "entities") List<Entity> entities) {
        return original.call(instance) || instance.is(SlimeBlockTags.BOUNCY) || entities.stream().anyMatch(e->e.slime$getEntityBounciness()>0);
    }
    *///?}
}
