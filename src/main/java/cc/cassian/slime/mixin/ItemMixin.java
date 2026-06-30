package cc.cassian.slime.mixin;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.api.SlimeColor;
import cc.cassian.slime.entity.SlimeballEntity;
import cc.cassian.slime.registry.SlimeDataComponents;
import cc.cassian.slime.registry.SlimeSoundEvents;
import cc.cassian.slime.tags.SlimeItemTags;
import cc.cassian.slime.util.SlimeHelpers;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Item.class)
public abstract class ItemMixin {

	@ModifyReturnValue(at = @At("RETURN"), method = "getName")
	private Component changeDyedSlimeName(Component original, ItemStack itemStack) {
		if (SlimeTime.CONFIG.colorfulSlimes.colourfulSlimes && original.getContents() instanceof TranslatableContents translatableContents) {
			if ((itemStack.has(SlimeDataComponents.DYED_COLOR) || itemStack.is(SlimeItemTags.DYEABLE_SLIME))) {
				if (SlimeTime.CONFIG.colorfulSlimes.renameDefaultSlimeToLime || itemStack.has(SlimeDataComponents.DYED_COLOR)) {
					var color = Objects.requireNonNull(itemStack.getOrDefault(SlimeDataComponents.DYED_COLOR, SlimeColor.LIME));
					return Component.translatable(translatableContents.getKey()+"."+color.getName());
				}
			}
			else if (itemStack.is(Items.SLIME_BLOCK) && SlimeTime.CONFIG.colorfulSlimes.renameDefaultSlimeToLime) {
				return Component.translatable("block.slime_time.lime_slime_block");
			}
		}
		return original;
	}

	@Inject(at = @At("HEAD"), method = "use", cancellable = true)
	private void injThrowSlimeBall(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
		InteractionResult returnValue = SlimeHelpers.throwSlimeBall(level, player.getItemInHand(hand), player.position(), player, new Vec3(0.0f, 1.5f, 1.0));;
		if (returnValue.equals(InteractionResult.SUCCESS))
			cir.setReturnValue(returnValue);
	}



}