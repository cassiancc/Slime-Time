package cc.cassian.slime.mixin;

import cc.cassian.slime.item.SlimeBucketItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class SlimeMixin {

	@Inject(at = @At("TAIL"), method = "mobInteract", cancellable = true)
	private void init(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
		var mob = (Mob) (Object) this;
		if (mob instanceof Slime slime) {
			ItemStack itemStack = player.getItemInHand(hand);
			if (itemStack.getItem() == Items.BUCKET && slime.isAlive() && slime.getSize()==1) {
				slime.playSound(SlimeBucketItem.getPickupSound(), 1.0F, 1.0F);
				ItemStack bucket = SlimeBucketItem.getBucketItemStack(slime);
				SlimeBucketItem.saveToBucketTag(slime, bucket);
				ItemStack result = ItemUtils.createFilledResult(itemStack, player, bucket, false);
				player.setItemInHand(hand, result);
				Level level = slime.level();
				if (!level.isClientSide()) {
					CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)player, bucket);
				}

				slime.discard();
				cir.setReturnValue(InteractionResult.SUCCESS);
			}
		}
	}

}