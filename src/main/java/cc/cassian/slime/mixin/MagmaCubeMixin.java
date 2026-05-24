package cc.cassian.slime.mixin;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.api.BucketableCubeMob;
import cc.cassian.slime.api.SlimeColor;
import cc.cassian.slime.api.VariatedSlimeAccess;
import cc.cassian.slime.registry.SlimeItems;
import cc.cassian.slime.registry.SlimeSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
//? if >=26.2 {
/*import net.minecraft.world.entity.monster.cubemob.AbstractCubeMob;
*///?}
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MagmaCube.class)
public abstract class MagmaCubeMixin
        //~ if >26.1 'Slime' -> 'AbstractCubeMob' {
        extends Slime
        //~}
        implements BucketableCubeMob {

    public MagmaCubeMixin(EntityType<MagmaCube> type, Level level) {
        super(type, level);
    }

    @Override
    public ItemStack slimeTime$getBucketItem() {
        return new ItemStack(SlimeItems.MAGMA_CUBE_BUCKET);
    }

    @Override
    public int slimeTime$getSize() {
        return getSize();
    }

    public SoundEvent slimeTime$getPickupSound() {
        return SlimeSoundEvents.BUCKET_FILL_MAGMA_CUBE;
    }
}
