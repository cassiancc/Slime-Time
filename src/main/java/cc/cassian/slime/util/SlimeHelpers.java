package cc.cassian.slime.util;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.api.SlimeColor;
import cc.cassian.slime.api.VariatedSlimeAccess;
//? neoforge
//import cc.cassian.slime.client.platform.NeoForgeClientEntrypoint;
import cc.cassian.slime.registry.SlimeBlocks;
import cc.cassian.slime.registry.SlimeDataComponents;
import cc.cassian.slime.registry.SlimeItems;
import cc.cassian.slime.tags.SlimeItemTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

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
                int count = self.getCount() + other.getCount();
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
        return level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, level);
    }

    public static List<ItemStack> dye(ItemStack defaultInstance) {
        return dye(defaultInstance, true);
    }

    public static List<ItemStack> dye(ItemStack defaultInstance, boolean addDefault) {
        List<ItemStack> stacks = new ArrayList<>();
        if (addDefault)
            stacks.add(defaultInstance);
        for (SlimeColor dye : SlimeColor.values()) {
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
            copy.set(SlimeDataComponents.DYED_COLOR, color);
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
            return SlimeBlocks.SLIME_BLOCKS.get(color).asItem().getDefaultInstance();
        }
        return defaultInstance;
    }

    public static Item getFroglight(Frog frog) {
        ResourceKey<FrogVariant> variant = frog.getVariant().unwrapKey().get();
        if (variant.equals(FrogVariant.TEMPERATE)) {
            return Items.OCHRE_FROGLIGHT;
        }
        else if (variant.equals(FrogVariant.COLD)) {
            return Items.VERDANT_FROGLIGHT;
        }
        else if (variant.equals(FrogVariant.WARM)) {
            return Items.PEARLESCENT_FROGLIGHT;
        } else if (variant.location().equals(ResourceLocation.fromNamespaceAndPath("instantfeedback", "dark"))) {
            return BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("instantfeedback", "cerulean_froglight"));
        } else if (variant.location().equals(ResourceLocation.fromNamespaceAndPath("nomansland", "mud"))) {
            return BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("nomansland", "vermillion_froglight"));
        }
        return Items.AIR;
    }

    public static List<ItemStack> addDyedItems(ItemStack defaultInstance) {
        if (SlimeTime.CONFIG.slimeTime.colourfulSlimes && SlimeTime.CONFIG.slimeTime.addDyedVariantsToCreativeTabs) return SlimeHelpers.dye(defaultInstance);
        else return Collections.singletonList(defaultInstance);
    }

    public static ResourceLocation getVariatedSlimeTexture(Slime state, ResourceLocation original) {
        var variant = ((VariatedSlimeAccess) state).slimeTime$getVariant();
        if (SlimeTime.CONFIG.slimeTime.colourfulSlimes && variant != null) return SlimeTime.of("textures/entity/slime/%s_slime.png".formatted(variant.getName()));
        else return original;
    }
}
