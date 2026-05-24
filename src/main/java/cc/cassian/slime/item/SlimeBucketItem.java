package cc.cassian.slime.item;

import cc.cassian.slime.api.BucketableCubeMob;
import cc.cassian.slime.api.SlimeColor;
//? fabric
import cc.cassian.slime.api.VariatedSlimeAccess;
import cc.cassian.slime.platform.FabricEntrypoint;
//? neoforge
//import cc.cassian.slime.platform.NeoForgeEntrypoint;
import cc.cassian.slime.registry.SlimeItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.locale.Language;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.advancements.CriteriaTriggers;
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
import org.apache.commons.lang3.text.WordUtils;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

//~ if >26.1 'Slime ' -> 'AbstractCubeMob ' {
@NullMarked
public class SlimeBucketItem extends BucketItem {

	private final EntityType<? extends Mob> type;
	private final SoundEvent emptySound;

	public SlimeBucketItem(final EntityType<? extends Mob> type, final SoundEvent emptySound, final Item.Properties properties) {
		super(Fluids.EMPTY, properties);
		this.type = type;
		this.emptySound = emptySound;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(final Level level, final Player player, final InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
		if (hitResult.getType() == HitResult.Type.MISS) {
			return InteractionResultHolder.pass(itemStack);
		} else if (hitResult.getType() != HitResult.Type.BLOCK) {
			return InteractionResultHolder.pass(itemStack);
		} else {
			BlockPos pos = hitResult.getBlockPos();
			Direction direction = hitResult.getDirection();
			BlockPos placePos = pos.relative(direction);
			if (!level.mayInteract(player, pos) || !player.mayUseItemAt(placePos, direction, itemStack)) {
				return InteractionResultHolder.fail(itemStack);
			} else {
				this.checkExtraContent(player, level, itemStack, placePos);
				if (player instanceof ServerPlayer) {
					CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, placePos, itemStack);
				}

				player.awardStat(Stats.ITEM_USED.get(this));
				ItemStack emptyResult = ItemUtils.createFilledResult(itemStack, player, getEmptySuccessItem(itemStack, player));
				return InteractionResultHolder.success(emptyResult);
			}
		}
	}

	@Override
	public void checkExtraContent(@Nullable Player user, Level level, ItemStack itemStack, BlockPos pos) {
		if (level instanceof ServerLevel) {
			this.spawn((ServerLevel)level, itemStack, pos);
			level.gameEvent(user, GameEvent.ENTITY_PLACE, pos);
		}
	}

	@Override
	protected void playEmptySound(@Nullable Player user, LevelAccessor level, BlockPos pos) {
		level.playSound(user, pos, this.emptySound, SoundSource.NEUTRAL, 1.0F, 1.0F);
	}

	private void spawn(final ServerLevel level, final ItemStack itemStack, final BlockPos spawnPos) {
		Mob mob = this.type.create(level, EntityType.createDefaultStackConfig(level, itemStack, null), spawnPos, MobSpawnType.BUCKET, true, false);
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

	public static void saveToBucketTag(Mob entity, ItemStack bucket) {
		CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, (tag) -> {
			if (entity.isNoAi()) {
				tag.putBoolean("NoAI", true);
			}

			if (entity.hasCustomName()) {
				tag.put("CustomName", ComponentSerialization.CODEC.encodeStart(NbtOps.INSTANCE, entity.getCustomName()).getOrThrow());
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
				var variant = ((VariatedSlimeAccess) slime).slimeTime$getVariant();
				if (variant != null) {
					variant.encode(tag);
				}
			}

			tag.putFloat("Health", entity.getHealth());
		});
	}

	public static void loadFromBucketTag(final Slime entity, final CompoundTag tag) {
		var noAI = tag.getBoolean("NoAI");
		entity.setNoAi(noAI);
		var silent = tag.getBoolean("Silent");
		entity.setSilent(silent);
		var noGravity = tag.getBoolean("NoGravity");
		entity.setNoGravity(noGravity);
		var glowing = tag.getBoolean("Glowing");
		entity.setGlowingTag(glowing);
		var invulnerable = tag.getBoolean("Invulnerable");
		entity.setInvulnerable(invulnerable);
		var health = tag.getFloat("Health");
		entity.setHealth(health);
		entity.setSize(1, true);
		if (tag.contains("SlimeTimeColor")) {
			SlimeColor slimeTimeColor = SlimeColor.decode(tag);
			//? fabric
			entity.setAttached(FabricEntrypoint.SLIME_STATE, slimeTimeColor);
			//? neoforge
			//entity.setData(NeoForgeEntrypoint.SLIME_STATE, slimeTimeColor);
		}

	}

	@Override
	public Component getName(ItemStack itemStack) {
		var entityData = itemStack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY).copyTag();
		if (entityData.contains("SlimeTimeColor")) {
			var color = SlimeColor.decode(entityData).getName();
			var key = "item.slime_time.%s_slime_bucket".formatted(color);
			if (Language.getInstance().has(key)) {
				return Component.translatable(key);
			}
			else return Component.translatable("item.slime_time.colored_slime_bucket", WordUtils.capitalize(color.replace("_", " ")));
		}
		return super.getName(itemStack);
	}

	public static SoundEvent getPickupSound() {
		return SoundEvents.BUCKET_FILL_TADPOLE;
	}
}
//~}