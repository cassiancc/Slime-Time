package cc.cassian.slime.mixin;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.api.SlimeColor;
import cc.cassian.slime.api.VariatedSlimeAccess;
//? fabric
import cc.cassian.slime.platform.FabricEntrypoint;
//? neoforge
//import cc.cassian.slime.platform.NeoForgeEntrypoint;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Slime.class)
public class SlimeMixin extends Mob implements VariatedSlimeAccess {

    protected SlimeMixin(EntityType<? extends Mob> type, Level level) {
        super(type, level);
    }

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void initiallySetRandomVariant(EntityType<Slime> type, Level level, CallbackInfo ci) {
        if (SlimeTime.CONFIG.slimeTime.colourfulSlimes && level.getRandom().nextBoolean() && this.slimeTime$getVariant() == null) {
            this.slimeTime$setVariant(SlimeColor.values()[this.getRandom().nextInt(0, SlimeColor.values().length)]);
        }
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
        //? fabric
        this.setAttached(FabricEntrypoint.SLIME_STATE, variant);
        //? neoforge
        //this.setData(NeoForgeEntrypoint.SLIME_STATE, variant);
    }
}
