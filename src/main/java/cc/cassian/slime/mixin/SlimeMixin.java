package cc.cassian.slime.mixin;

import cc.cassian.slime.api.ModHelpers;
import cc.cassian.slime.item.SlimeBucketItem;
import cc.cassian.slime.registry.SlimeItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.FrogVariant;
import net.minecraft.world.entity.animal.frog.FrogVariants;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class SlimeMixin {

	@Inject(at = @At("TAIL"), method = "mobInteract", cancellable = true)
	private void init(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
		var mob = (Mob) (Object) this;
		ItemStack itemStack = player.getItemInHand(hand);
		if (mob instanceof Slime slime) {
			if (slime.isAlive() && itemStack.is(Items.BUCKET) && slime.getSize() == 1) {
				slime.playSound(SlimeBucketItem.getPickupSound(), 1.0F, 1.0F);
				ItemStack bucket = SlimeBucketItem.getBucketItemStack(slime);
				SlimeBucketItem.saveToBucketTag(slime, bucket);
				ItemStack result = ItemUtils.createFilledResult(itemStack, player, bucket, false);
				player.setItemInHand(hand, result);
				Level level = slime.level();
				if (!level.isClientSide()) {
					CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, bucket);
				}

				slime.discard();
				cir.setReturnValue(InteractionResult.SUCCESS);
			}
		}
		if (mob instanceof Frog frog) {
			boolean magma = itemStack.is(SlimeItems.MAGMA_CUBE_BUCKET);
			if (magma || itemStack.is(SlimeItems.SLIME_BUCKET)) {
				Item froglight = ModHelpers.getFroglight(magma, frog);
				SoundEvent sound = magma ? SoundEvents.MAGMA_CUBE_DEATH_SMALL : SoundEvents.SLIME_DEATH_SMALL;
				frog.playSound(sound, 1.0F, 1.0F);
				Vec3 pos = frog.position();
				Level level = frog.level();
				ItemEntity entity = new ItemEntity(level, pos.x, pos.y, pos.z, froglight.getDefaultInstance());
				if (entity != null) {
					level.addFreshEntity(entity);
				}
				ItemStack result = Items.BUCKET.getDefaultInstance();
				player.setItemInHand(hand, result);
				cir.setReturnValue(InteractionResult.SUCCESS);
			}
		}
	}

}