package cc.cassian.slime.mixin;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.entity.SlimeballEntity;
import cc.cassian.slime.tags.SlimeItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {

	@Inject(at = @At("HEAD"), method = "use", cancellable = true)
	private void init(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (SlimeTime.CONFIG.slimeTime.throwableSlimeballs && itemStack.is(SlimeItemTags.THROWABLE_SLIME_BALLS)) {
			level.playSound(
					null,
					player.getX(),
					player.getY(),
					player.getZ(),
					SoundEvents.SNOWBALL_THROW,
					SoundSource.NEUTRAL,
					0.5F,
					0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
			);
			if (level instanceof ServerLevel serverLevel) {
				Projectile.spawnProjectileFromRotation(SlimeballEntity::new, serverLevel, itemStack, player, 0.0F, 1.5F, 1.0F);
			}

			player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
			itemStack.consume(1, player);
			cir.setReturnValue(InteractionResult.SUCCESS);
		}
	}

}