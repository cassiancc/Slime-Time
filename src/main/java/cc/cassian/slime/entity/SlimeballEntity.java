package cc.cassian.slime.entity;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.registry.SlimeEntityTypes;
import cc.cassian.slime.tags.SlimeBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class SlimeballEntity extends ThrowableItemProjectile {
	private static final EntityDataAccessor<Float> BOUNCE = SynchedEntityData.defineId(SlimeballEntity.class, EntityDataSerializers.FLOAT);

	public SlimeballEntity(final EntityType<? extends SlimeballEntity> type, final Level level) {
		super(type, level);
	}

	public SlimeballEntity(final Level level, final LivingEntity mob, final ItemStack itemStack) {
		super(SlimeEntityTypes.SLIMEBALL, mob, level, itemStack);
	}

	public SlimeballEntity(final Level level, final double x, final double y, final double z, final ItemStack itemStack) {
		super(SlimeEntityTypes.SLIMEBALL, x, y, z, level, itemStack);
	}

	@Override
	protected Item getDefaultItem() {
		return Items.SLIME_BALL;
	}

	@Override
	public void tick() {
		super.tick();
		if (!this.level().isClientSide()) {
			if (this.isInLava()) {
				this.level().playSound(this, this.blockPosition(),
						SoundEvents.FIRE_EXTINGUISH, SoundSource.NEUTRAL,
						0.5f, 1 + level().getRandom().nextFloat() * 0.4F + 0.8F
				);
				this.discard();
			}
			if (this.isInWater()) {
				ServerLevel level = (ServerLevel) this.level();
				level.playSound(this, this.blockPosition(),
						SoundEvents.BUBBLE_POP, SoundSource.NEUTRAL,
						this.getBounce() * .7f, this.getBounce() * 1.5f
				);
			}
		} else {
			if (SlimeTime.CONFIG.slimeballParticles && random.nextBoolean() && tickCount > 1) {
				level().addParticle(getParticle(), getX(), getY(), getZ(), 0.0, 0.0, 0.0);
			}
		}
		if (this.getDeltaMovement().equals(Vec3.ZERO)) {
			this.discard();
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		Entity entity = entityHitResult.getEntity();
		if (entity instanceof Player || entity instanceof SlimeballEntity) return;
		this.setDeltaMovement(Vec3.ZERO);
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		this.bounce(blockHitResult);
	}

	private void bounce(BlockHitResult blockHitResult) {
		BlockState state = this.level().getBlockState(blockHitResult.getBlockPos());
		Vec3 velocity = this.getDeltaMovement();
		Vec3i hitVector = blockHitResult.getDirection().getUnitVec3i();
		Vec3 normal = new Vec3(hitVector.getX(), hitVector.getY(), hitVector.getZ());

		double dotProduct = velocity.dot(normal);

		Vec3 reflectionVector = new Vec3(velocity.x - 2 * dotProduct * normal.x, velocity.y - 2 * dotProduct * normal.y, velocity.z - 2 * dotProduct * normal.z);
		float bounceFactor = this.getAndDecrementBounce(this.getDampeningFactor(state));
		if (bounceFactor < 0.01f) this.discard();
		reflectionVector = reflectionVector.scale(bounceFactor);
		this.setDeltaMovement(reflectionVector);
	}

	private void setBounce(float bounce) {
		this.entityData.set(BOUNCE, bounce);
	}

	private float getBounce() {
		return this.entityData.get(BOUNCE);
	}

	private float getAndDecrementBounce(float dampening) {
		float bounceFactor = this.getBounce() * dampening;
		this.entityData.set(BOUNCE, bounceFactor);
		return this.getBounce();
	}

	private float getDampeningFactor(BlockState state) {
		if (state.is(SlimeBlockTags.SUPPRESSES_BOUNCE)) return 1.0f;
		else if (state.is(SlimeBlockTags.BOUNCY)) return 0.075f;
		else return 0.85f;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder entityData) {
		super.defineSynchedData(entityData);
		entityData.define(BOUNCE, .5f);
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putFloat("Bounce", this.getBounce());
		output.store("Stack", ItemStack.CODEC, this.getItem());
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		this.setBounce(input.getFloatOr("Bounce", 0));
	}

	private ParticleOptions getParticle() {
		ItemStack item = this.getItem();
		return item.isEmpty() ? ParticleTypes.ITEM_SLIME : new ItemParticleOption(ParticleTypes.ITEM, ItemStackTemplate.fromNonEmptyStack(item));
	}

	@Override
	public void handleEntityEvent(final byte id) {
		if (id == 3) {
			ParticleOptions particle = this.getParticle();

			for(int i = 0; i < 8; ++i) {
				this.level().addParticle(particle, this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F, 0.0F);
			}
		}

	}

	@Override
	public double slime$getEntityBounciness() {
		return 1;
	}
}
