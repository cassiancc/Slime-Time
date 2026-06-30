package cc.cassian.slime.mixin;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.item.SlimeBucketItem;
import cc.cassian.slime.registry.SlimeItems;
import cc.cassian.slime.util.SlimeHelpers;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DispenseItemBehavior.class)
public interface DispenseItemBehaviorMixin {
	@Inject(method = "bootStrap", at = @At("TAIL"))
	private static void init(CallbackInfo info) {
		DispenseItemBehavior slimeBucketBehavior = new DefaultDispenseItemBehavior() {
			@Override
			public ItemStack execute(final BlockSource source, final ItemStack dispensed) {
				SlimeBucketItem bucket = (SlimeBucketItem)dispensed.getItem();
				BlockPos target = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
				Level level = source.level();
				bucket.checkExtraContent(null, level, dispensed, target);
				return this.consumeWithRemainder(source, dispensed, new ItemStack(Items.BUCKET));
			}
		};
		DispenserBlock.registerBehavior(SlimeItems.SLIME_BUCKET, slimeBucketBehavior);
		DispenserBlock.registerBehavior(SlimeItems.MAGMA_CUBE_BUCKET, slimeBucketBehavior);
		if (SlimeTime.CONFIG.slimeTime.throwableSlimeballs) {
			DispenseItemBehavior slimeBallBehaviour = new DefaultDispenseItemBehavior() {
				@Override
				public ItemStack execute(final BlockSource source, final ItemStack dispensed) {
					Direction direction = source.state().getValue(DispenserBlock.FACING);
					Position position = ProjectileItem.DispenseConfig.DEFAULT.positionFunction().getDispensePosition(source, direction);
					SlimeHelpers.throwSlimeBall(source.level(), dispensed, position, null, new Vec3(direction.getStepX(), direction.getStepY(), direction.getStepZ()));
					return dispensed;
				}
			};
			DispenserBlock.registerBehavior(Items.SLIME_BALL, slimeBallBehaviour);
		}
	}

}
