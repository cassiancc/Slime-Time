package cc.cassian.slime.mixin;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.api.BucketableCubeMob;
import cc.cassian.slime.api.SlimeColor;
import cc.cassian.slime.api.VariatedSlimeAccess;
//? fabric
import cc.cassian.slime.platform.FabricEntrypoint;
//? neoforge
//import cc.cassian.slime.platform.NeoForgeEntrypoint;
import cc.cassian.slime.registry.SlimeItems;
import cc.cassian.slime.registry.SlimeParticleTypes;
import cc.cassian.slime.registry.SlimeSoundEvents;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
//? if >26.1
//import net.minecraft.world.entity.monster.cubemob.AbstractCubeMob;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Slime.class)
public abstract class SlimeMixin
        //~ if >26.1 'Mob' -> 'AbstractCubeMob' {
        extends Mob
        //~}
        implements VariatedSlimeAccess, BucketableCubeMob {

    //? if <26.2 {
    @Shadow
    public abstract int getSize();
    //?}

    protected SlimeMixin(EntityType<Slime> type, Level level) {
        super(type, level);
    }

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void initiallySetRandomVariant(EntityType<Slime> type, Level level, CallbackInfo ci) {
        if (SlimeTime.CONFIG.slimeTime.colourfulSlimes
                //? if <26.2
                && !type.equals(EntityType.MAGMA_CUBE)
                && level.getRandom().nextBoolean() && this.slimeTime$getVariant() == null) {
            this.slimeTime$setVariant(SlimeColor.values()[this.getRandom().nextInt(0, SlimeColor.values().length)]);
        }
    }

    @ModifyReturnValue(method = "getParticleType", at = @At(value = "RETURN"))
    private ParticleOptions getVariantParticle(ParticleOptions original) {
        if (slimeTime$getVariant() != null)
            return ColorParticleOption.create(SlimeParticleTypes.TINTED_SLIME, slimeTime$getVariant().argb());
        return original;
    }

    @Override
    public @Nullable SlimeColor slimeTime$getVariant() {
        //? fabric
        return this.getAttached(FabricEntrypoint.SLIME_STATE);
        //? neoforge
        //return this.getData(NeoForgeEntrypoint.SLIME_STATE);
    }

    @Override
    public void slimeTime$setVariant(SlimeColor variant) {
        //? <26.2
        if (!typeHolder().is(EntityType.MAGMA_CUBE.builtInRegistryHolder().key())) return;
        //? fabric
        this.setAttached(FabricEntrypoint.SLIME_STATE, variant);
        //? neoforge
        //this.setData(NeoForgeEntrypoint.SLIME_STATE, variant);
    }

    @Override
    public ItemStack slimeTime$getBucketItem() {
        //? if <26.2 {
        if ((Object) this instanceof MagmaCube) {
            return new ItemStack(SlimeItems.MAGMA_CUBE_BUCKET);
        }
        //?}
        ItemStack bucket = new ItemStack(SlimeItems.SLIME_BUCKET);
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, (tag) -> {
            SlimeColor variant = slimeTime$getVariant();
            if (variant != null)
                variant.encode(tag);
        });
        return bucket;
    }

    @Override
    public int slimeTime$getSize() {
        return getSize();
    }

    public SoundEvent slimeTime$getPickupSound() {
        return SlimeSoundEvents.BUCKET_FILL_SLIME;
    }
}
