package cc.cassian.slime.util;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.api.SlimeColor;
import cc.cassian.slime.api.VariatedSlimeRenderStateAccess;
//? neoforge
//import cc.cassian.slime.client.platform.NeoForgeClientEntrypoint;
import cc.cassian.slime.entity.SlimeballEntity;
import cc.cassian.slime.registry.SlimeBlocks;
import cc.cassian.slime.registry.SlimeDataComponents;
import cc.cassian.slime.registry.SlimeItems;
import cc.cassian.slime.registry.SlimeSoundEvents;
import cc.cassian.slime.tags.SlimeItemTags;
import net.minecraft.client.renderer.entity.state.SlimeRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.FrogVariant;
import net.minecraft.world.entity.animal.frog.FrogVariants;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class SlimeHelpers {
    /// Mixes two Slime Balls together.
    public static boolean mergeSlimeBalls(ItemStack self, ItemStack other, Player player) {
        if (self.is(Items.SLIME_BALL) && other.is(Items.SLIME_BALL) && (self.has(SlimeDataComponents.DYED_COLOR) || other.has(DataComponents.DYED_COLOR))) {
            SlimeColor selfColor = null;
            SlimeColor otherColor = null;
            SlimeColor finalColor = null;
            if (self.has(SlimeDataComponents.DYED_COLOR)) {
                selfColor = Objects.requireNonNull(self.get(SlimeDataComponents.DYED_COLOR));
            }
            if (other.has(SlimeDataComponents.DYED_COLOR)) {
                otherColor = Objects.requireNonNull(other.get(SlimeDataComponents.DYED_COLOR));
            }
            if (selfColor != null && otherColor != null) {
                finalColor = SlimeColor.getMixedColor(player.level(), selfColor, otherColor);
            }
            else if (selfColor != null) {
                finalColor = selfColor;
            }
            else if (otherColor != null) {
                finalColor = otherColor;
            }
            if (finalColor != null) {
                int count = self.count() + other.getCount();
                if (count <= self.getMaxStackSize()) {
                    self.setCount(count);
                    self.set(SlimeDataComponents.DYED_COLOR, finalColor);
                    other.setCount(0);
                    broadcastChangesOnContainerMenu(player);
                    return true;
                }
            }
        }
        return false;
    }

    private static void broadcastChangesOnContainerMenu(final Player player) {
        AbstractContainerMenu containerMenu = player.containerMenu;
        if (containerMenu != null) {
            containerMenu.slotsChanged(player.getInventory());
        }
    }

    /// Replaces the name of the item with its dyed variant.
    public static void addDyeTooltip(ItemStack stack, Item.TooltipContext context, TooltipFlag flag, List<Component> tooltip) {

    }

    public static Optional<RecipeHolder<CraftingRecipe>> getSynchronizedRecipe(Level level, CraftingInput input) {
        //? fabric
        return level.recipeAccess().getSynchronizedRecipes().getFirstMatch(RecipeType.CRAFTING, input, level);
        //? neoforge
        //return NeoForgeClientEntrypoint.getSynchronizedRecipes(level, input);
    }

    public static List<ItemStack> dye(ItemStack defaultInstance) {
        return dye(defaultInstance, true);
    }

    public static List<ItemStack> dye(ItemStack defaultInstance, boolean addDefault) {
        List<ItemStack> stacks = new ArrayList<>();
        for (SlimeColor dye : SlimeColor.values()) {
            if (!SlimeColor.isDefault(dye) || addDefault)
                stacks.add(dye(defaultInstance, dye));
        }
        return stacks;
    }

    public static ItemStack dye(ItemStack defaultInstance, DyeColor dye) {
        return dye(defaultInstance, SlimeColor.byDyeColor(dye));
    }

    public static ItemStack dye(ItemStack defaultInstance, SlimeColor color) {
        if (defaultInstance.is(SlimeItemTags.DYEABLE_SLIME)) {
            var copy = defaultInstance.copy();
            if (SlimeColor.isDefault(color)) copy.remove(SlimeDataComponents.DYED_COLOR);
            else copy.set(SlimeDataComponents.DYED_COLOR, color);
            return copy;
        }
        if (defaultInstance.is(SlimeItems.SLIME_BUCKET)) {
            var copy = defaultInstance.copy();
            CustomData.update(DataComponents.BUCKET_ENTITY_DATA, copy, (tag) -> {
                color.encode(tag);
            });
            return copy;
        }
        if (defaultInstance.is(Items.SLIME_BLOCK)) {
            if (SlimeColor.isDefault(color)) return Items.SLIME_BLOCK.getDefaultInstance();
            return SlimeBlocks.SLIME_BLOCKS.get(color).asItem().getDefaultInstance();
        }
        return defaultInstance;
    }

    public static Item getFroglight(Frog frog) {
        Holder<FrogVariant> variant = frog.getVariant();
        if (variant.is(FrogVariants.TEMPERATE)) {
            return Items.OCHRE_FROGLIGHT;
        } else if (variant.is(FrogVariants.COLD)) {
            return Items.VERDANT_FROGLIGHT;
        } else if (variant.is(FrogVariants.WARM)) {
            return Items.PEARLESCENT_FROGLIGHT;
        } else if (variant.is(Identifier.fromNamespaceAndPath("instantfeedback", "dark"))) {
            return BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath("instantfeedback", "cerulean_froglight"));
        } else if (variant.is(Identifier.fromNamespaceAndPath("nomansland", "mud"))) {
            return BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath("nomansland", "vermillion_froglight"));
        }
        return Items.AIR;
    }

    public static List<ItemStack> addDyedItems(ItemStack defaultInstance) {
        if (SlimeTime.CONFIG.colorfulSlimes.colourfulSlimes && SlimeTime.CONFIG.colorfulSlimes.addDyedVariantsToCreativeTabs) return SlimeHelpers.dye(defaultInstance);
        else return Collections.singletonList(defaultInstance);
    }

    public static Identifier getVariatedSlimeTexture(SlimeRenderState state, Identifier original) {
        var variant = ((VariatedSlimeRenderStateAccess) state).slimeTime$getVariant();
        if (SlimeTime.CONFIG.colorfulSlimes.colourfulSlimes && variant != null) return SlimeTime.of("textures/entity/slime/%s_slime.png".formatted(variant.getName()));
        else return original;
    }

    public static InteractionResult throwSlimeBall(Level level, ItemStack itemStack, Position pos, @Nullable Player player, Vec3 angle) {
        if (SlimeTime.CONFIG.slimeTime.throwableSlimeballs && itemStack.is(SlimeItemTags.THROWABLE_SLIME_BALLS)) {
            level.playSound(
                    null,
                    pos.x(),
                    pos.y(),
                    pos.z(),
                    SlimeSoundEvents.SLIME_BALL_THROW,
                    SoundSource.NEUTRAL,
                    0.5F,
                    0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F)
            );
            if (level instanceof ServerLevel serverLevel) {
                if (player != null) {
                    Projectile.spawnProjectileFromRotation(SlimeballEntity::new, serverLevel, itemStack, player, 0.0F, 1.5F, 1.0F);
                } else {
                    ProjectileItem.DispenseConfig config = ProjectileItem.DispenseConfig.DEFAULT;
                    Projectile.spawnProjectileUsingShoot(new SlimeballEntity(level, pos.x(), pos.y(), pos.z(), itemStack), serverLevel, itemStack, angle.x(), angle.y(), angle.z(), config.power(), config.uncertainty());
                }
            }

            if (player != null) {
                player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
            }
            itemStack.consume(1, player);
            return (InteractionResult.SUCCESS);
        }
        return InteractionResult.PASS;
    }
}
