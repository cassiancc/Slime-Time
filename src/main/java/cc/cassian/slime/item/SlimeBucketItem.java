package cc.cassian.slime.item;

import cc.cassian.slime.registry.SlimeItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jspecify.annotations.Nullable;

public class SlimeBucketItem extends BucketItem {

	private final EntityType<? extends Mob> type;
	private final SoundEvent emptySound;

	public SlimeBucketItem(final EntityType<? extends Mob> type, final SoundEvent emptySound, final Item.Properties properties) {
		super(Fluids.EMPTY, properties);
		this.type = type;
		this.emptySound = emptySound;
	}

	@Override
	public InteractionResult use(final Level level, final Player player, final InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
		if (hitResult.getType() == HitResult.Type.MISS) {
			return InteractionResult.PASS;
		} else if (hitResult.getType() != HitResult.Type.BLOCK) {
			return InteractionResult.PASS;
		} else {
			BlockPos pos = hitResult.getBlockPos();
			Direction direction = hitResult.getDirection();
			BlockPos placePos = pos.relative(direction);
			if (!level.mayInteract(player, pos) || !player.mayUseItemAt(placePos, direction, itemStack)) {
				return InteractionResult.FAIL;
			} else {
				this.checkExtraContent(player, level, itemStack, placePos);
				if (player instanceof ServerPlayer) {
					CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, placePos, itemStack);
				}

				player.awardStat(Stats.ITEM_USED.get(this));
				ItemStack emptyResult = ItemUtils.createFilledResult(itemStack, player, getEmptySuccessItem(itemStack, player));
				return InteractionResult.SUCCESS.heldItemTransformedTo(emptyResult);
			}
		}
	}

	@Override
	public void checkExtraContent(final @Nullable LivingEntity user, final Level level, final ItemStack itemStack, final BlockPos pos) {
		if (level instanceof ServerLevel) {
			this.spawn((ServerLevel)level, itemStack, pos);
			level.gameEvent(user, GameEvent.ENTITY_PLACE, pos);
		}

	}

	@Override
	protected void playEmptySound(final @Nullable LivingEntity user, final LevelAccessor level, final BlockPos pos) {
		level.playSound(user, pos, this.emptySound, SoundSource.NEUTRAL, 1.0F, 1.0F);
	}

	private void spawn(final ServerLevel level, final ItemStack itemStack, final BlockPos spawnPos) {
		Mob mob = this.type.create(level, EntityType.createDefaultStackConfig(level, itemStack, null), spawnPos, EntitySpawnReason.BUCKET, true, false);
		if (mob instanceof Slime slime) {
			CustomData entityData = itemStack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
			loadFromBucketTag(slime, entityData.copyTag());
			slime.setPersistenceRequired();
		}

		if (mob != null) {
			level.addFreshEntityWithPassengers(mob);
			mob.playAmbientSound();
		}

	}

	public static void saveToBucketTag(Slime entity, ItemStack bucket) {
		bucket.copyFrom(DataComponents.CUSTOM_NAME, entity);
		CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, (tag) -> {
			if (entity.isNoAi()) {
				tag.putBoolean("NoAI", true);
			}

			if (entity.isSilent()) {
				tag.putBoolean("Silent", true);
			}

			if (entity.isNoGravity()) {
				tag.putBoolean("NoGravity", true);
			}

			if (entity.hasGlowingTag()) {
				tag.putBoolean("Glowing", true);
			}

			if (entity.isInvulnerable()) {
				tag.putBoolean("Invulnerable", true);
			}

			if (entity instanceof Slime slime) {
				tag.putInt("Size", slime.getSize());
			}

			tag.putFloat("Health", entity.getHealth());
		});
	}

	public static void loadFromBucketTag(final Slime entity, final CompoundTag tag) {
		var noAI = tag.getBoolean("NoAI");
		noAI.ifPresent(entity::setNoAi);
		var silent = tag.getBoolean("Silent");
		silent.ifPresent(entity::setSilent);
		var noGravity = tag.getBoolean("NoGravity");
		noGravity.ifPresent(entity::setNoGravity);
		var glowing = tag.getBoolean("Glowing");
		glowing.ifPresent(entity::setGlowingTag);
		var invulnerable = tag.getBoolean("Invulnerable");
		invulnerable.ifPresent(entity::setInvulnerable);
		var health = tag.getFloat("Health");
		health.ifPresent(entity::setHealth);
		entity.setSize(tag.getIntOr("Size", 1), true);
	}

	public static ItemStack getBucketItemStack(Slime slime) {
		if (slime instanceof MagmaCube) {
			return new ItemStack(SlimeItems.MAGMA_CUBE_BUCKET);
		}
		return new ItemStack(SlimeItems.SLIME_BUCKET);
	}

	public static SoundEvent getPickupSound() {
		return SoundEvents.BUCKET_FILL_TADPOLE;
	}
}
