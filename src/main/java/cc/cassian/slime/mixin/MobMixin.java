package cc.cassian.slime.mixin;

import cc.cassian.slime.api.BucketableCubeMob;
import cc.cassian.slime.api.SlimeColor;
import cc.cassian.slime.api.VariatedSlimeAccess;
import cc.cassian.slime.item.SlimeBucketItem;
import cc.cassian.slime.registry.SlimeItems;
import cc.cassian.slime.util.SlimeHelpers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.item.ItemEntity;
//? <26.2 {
import net.minecraft.advancements.CriteriaTriggers;
//?} else {
/*import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.world.entity.monster.cubemob.AbstractCubeMob;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Slime;
*///?}
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobMixin {

	@Inject(at = @At("TAIL"), method = "mobInteract", cancellable = true)
	private void interact(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
		var mob = (Mob) (Object) this;
		ItemStack itemStack = player.getItemInHand(hand);
		if (mob instanceof BucketableCubeMob slime) {
			if (mob.isAlive() && slime.slimeTime$getSize() == 1) {
				if (itemStack.is(Items.BUCKET)) {
					mob.playSound(slime.slimeTime$getPickupSound(), 1.0F, 1.0F);
					ItemStack bucket = slime.slimeTime$getBucketItem();
					SlimeBucketItem.saveToBucketTag(mob, bucket);
					ItemStack result = ItemUtils.createFilledResult(itemStack, player, bucket, false);
					player.setItemInHand(hand, result);
					Level level = mob.level();
					if (!level.isClientSide()) {
						CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, bucket);
					}

					mob.discard();
					cir.setReturnValue(InteractionResult.SUCCESS);
				} else if (itemStack.is(ItemTags.DYES) && mob instanceof VariatedSlimeAccess variatedSlime) {
					mob.playSound(SoundEvents.DYE_USE, 1.0F, 1.0F);
					variatedSlime.slimeTime$setVariant(SlimeColor.byDyeColor(itemStack.getOrDefault(DataComponents.DYE, DyeColor.LIME)));
					itemStack.consume(1, player);
					cir.setReturnValue(InteractionResult.SUCCESS);
				}
			}
		}
		if (mob instanceof Frog frog) {
			if (itemStack.is(SlimeItems.MAGMA_CUBE_BUCKET)) {
				Item froglight = SlimeHelpers.getFroglight(frog);
				SoundEvent sound = SoundEvents.MAGMA_CUBE_DEATH_SMALL;
				frog.playSound(sound, 1.0F, 1.0F);
				Vec3 pos = frog.position();
				Level level = frog.level();
				ItemEntity entity = new ItemEntity(level, pos.x, pos.y, pos.z, froglight.getDefaultInstance());
				if (entity != null) {
					level.addFreshEntity(entity);
				}
				if (!player.hasInfiniteMaterials()) {
					ItemStack result = Items.BUCKET.getDefaultInstance();
					player.setItemInHand(hand, result);
				}
				cir.setReturnValue(InteractionResult.SUCCESS);
			}
		}
	}

}