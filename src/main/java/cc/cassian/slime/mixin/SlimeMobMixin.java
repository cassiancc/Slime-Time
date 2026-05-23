package cc.cassian.slime.mixin;

import cc.cassian.slime.api.VariatedSlimeAccess;
import cc.cassian.slime.util.SlimeHelpers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class SlimeMobMixin extends Entity {
    protected SlimeMobMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @Inject(method = "dropFromLootTable", at = @At(value = "HEAD"), cancellable = true)
    private void dropDyedSlimeBalls(ServerLevel level, DamageSource source, boolean playerKilled, CallbackInfo ci) {
        if (this instanceof VariatedSlimeAccess slimeAccess && slimeAccess.slimeTime$getVariant() != null) {
            var entity = new ItemEntity(level, this.getX(), this.getY(), this.getZ(), SlimeHelpers.dye(Items.SLIME_BALL.getDefaultInstance(), slimeAccess.slimeTime$getVariant()));
            level.addFreshEntity(entity);
            ci.cancel();
        }
    }
}
