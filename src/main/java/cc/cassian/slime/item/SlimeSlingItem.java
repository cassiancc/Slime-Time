package cc.cassian.slime.item;

import cc.cassian.slime.SlimeTime;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SlimeSlingItem extends Item {
	public static final int MAX_DRAW_DURATION = 20;

	public SlimeSlingItem(final Item.Properties properties) {
		super(properties);
	}

	@Override
	public boolean releaseUsing(final ItemStack itemStack, final Level level, final LivingEntity entity, final int remainingTime) {
		if (!(entity instanceof Player player)) {
			return false;
		} else {
			int timeHeld = this.getUseDuration(itemStack, entity) - remainingTime;
			float pow = getPowerForTime(timeHeld);
			if (pow < 0.1) {
				return false;
			} else {
				if (level instanceof ServerLevel) {
					Vec3 view = player.getViewVector(0);
					float horizontalForceMultiplier = SlimeTime.CONFIG.slimeSling.horizontalForceMultiplier;
					var launch = new Vec3(-view.x* horizontalForceMultiplier, pow * SlimeTime.CONFIG.slimeSling.verticalForceMultiplier, -view.z* horizontalForceMultiplier);
					player.setDeltaMovement(launch);
					player.hurtMarked = true;
				}
				itemStack.hurtAndBreak(1, entity, entity.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof SlimeSlingItem ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);

				level.playSound(
						null,
						player.getX(),
						player.getY(),
						player.getZ(),
						SoundEvents.ARROW_SHOOT,
						SoundSource.PLAYERS,
						1.0F,
						1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + pow * 0.5F
				);
				player.awardStat(Stats.ITEM_USED.get(this));
				return true;
			}
		}
	}

	public static float getPowerForTime(final int timeHeld) {
		float pow = (float) timeHeld / MAX_DRAW_DURATION;
		pow = (pow * pow + pow * 2.0F) / 3.0F;
		if (pow > 1.0F) {
			pow = 1.0F;
		}

		return pow;
	}

	@Override
	public int getUseDuration(final ItemStack itemStack, final LivingEntity user) {
		return 72000;
	}

	@Override
	public ItemUseAnimation getUseAnimation(final ItemStack itemStack) {
		return ItemUseAnimation.BOW;
	}

	@Override
	public InteractionResult use(final Level level, final Player player, final InteractionHand hand) {
		player.startUsingItem(hand);
		return InteractionResult.SUCCESS;
	}
}
