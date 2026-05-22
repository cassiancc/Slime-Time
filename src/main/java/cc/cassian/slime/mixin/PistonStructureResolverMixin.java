package cc.cassian.slime.mixin;

import cc.cassian.slime.tags.SlimeBlockTags;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonStructureResolver.class)
public class PistonStructureResolverMixin {
    @Inject(method = "isSticky", require = 0, at = @At(value = "HEAD"), cancellable = true)
    private static void slimeBlocksStick(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.is(SlimeBlockTags.SLIME_BLOCKS))
            cir.setReturnValue(true);
    }

    @Inject(method = "canStickToEachOther", require = 0, at = @At(value = "HEAD"), cancellable = true)
    private static void doNotStickSlimeBlocksToEachOther(BlockState state1, BlockState state2, CallbackInfoReturnable<Boolean> cir) {
        if (state1.is(SlimeBlockTags.SLIME_BLOCKS) || state2.is(SlimeBlockTags.SLIME_BLOCKS))
            cir.setReturnValue(state1.getBlock() == state2.getBlock());
    }

}
