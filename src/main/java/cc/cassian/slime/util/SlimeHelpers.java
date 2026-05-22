package cc.cassian.slime.util;

import cc.cassian.slime.platform.NeoForgeEntrypoint;
import cc.cassian.slime.registry.SlimeBlocks;
import cc.cassian.slime.registry.SlimeDataComponents;
import cc.cassian.slime.tags.SlimeItemTags;
import dev.yumi.mc.core.api.YumiMods;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.FrogVariant;
import net.minecraft.world.entity.animal.frog.FrogVariants;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.text.WordUtils;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class SlimeHelpers {
    public static boolean mergeSlimeBalls(ItemStack self, ItemStack other, Player player) {
        if (self.is(Items.SLIME_BALL) && other.is(Items.SLIME_BALL) && (self.has(SlimeDataComponents.DYED_COLOR) || other.has(DataComponents.DYED_COLOR))) {
            DyeColor selfColor = null;
            DyeColor otherColor = null;
            DyeColor finalColor = null;
            if (self.has(SlimeDataComponents.DYED_COLOR)) {
                selfColor = Objects.requireNonNull(self.get(SlimeDataComponents.DYED_COLOR));
            }
            if (other.has(SlimeDataComponents.DYED_COLOR)) {
                otherColor = Objects.requireNonNull(other.get(SlimeDataComponents.DYED_COLOR));
            }
            if (selfColor != null && otherColor != null) {
                finalColor = getMixedColor(player.level(), selfColor, otherColor);
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

    public static void addDyeTooltip(ItemStack stack, Item.TooltipContext context, TooltipFlag flag, List<Component> tooltip) {
        if (stack.has(SlimeDataComponents.DYED_COLOR)) {
            var color = Objects.requireNonNull(stack.get(SlimeDataComponents.DYED_COLOR));
            tooltip.add(1, Component.translatable("item.color", WordUtils.capitalizeFully(color.getName().replace("_", " "))).withStyle(ChatFormatting.GRAY));
        }

    }

    public static DyeColor getMixedColor(final Level level, final DyeColor dyeColor1, final DyeColor dyeColor2) {
        DyeColor mixedColor = findColorMixInRecipes(level, dyeColor1, dyeColor2);
        if (mixedColor != null) {
            return mixedColor;
        } else {
            return level.getRandom().nextBoolean() ? dyeColor1 : dyeColor2;
        }
    }

    @Nullable
    private static DyeColor findColorMixInRecipes(final Level level, final DyeColor dyeColor1, final DyeColor dyeColor2) {
        DataComponentLookup<Item> itemComponents = level.registryAccess().lookupOrThrow(Registries.ITEM).componentLookup();
        Collection<Holder<Item>> dye1Items = itemComponents.findAll(DataComponents.DYE, dyeColor1);
        if (!dye1Items.isEmpty()) {
            Collection<Holder<Item>> dye2Items = itemComponents.findAll(DataComponents.DYE, dyeColor2);
            if (!dye2Items.isEmpty()) {
                for (Holder<Item> dye1Item : dye1Items) {
                    for (Holder<Item> dye2Item : dye2Items) {
                        CraftingInput input = CraftingInput.of(2, 1, List.of(new ItemStack(dye1Item), new ItemStack(dye2Item)));
                        Optional<RecipeHolder<CraftingRecipe>> foundRecipe = getSynchronizedRecipe(level, input);
                        if (foundRecipe.isPresent()) {
                            ItemStack craftingResult = foundRecipe.get().value().assemble(input);
                            DyeColor craftedDyeColor = craftingResult.get(DataComponents.DYE);
                            if (craftedDyeColor != null) {
                                return craftedDyeColor;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static Optional<RecipeHolder<CraftingRecipe>> getSynchronizedRecipe(Level level, CraftingInput input) {
        if (YumiMods.get().isModLoaded("fabric-api"))
            return level.recipeAccess().getSynchronizedRecipes().getFirstMatch(RecipeType.CRAFTING, input, level);
        return NeoForgeEntrypoint.getSynchronizedRecipes(level, input);
    }

    public static List<ItemStack> dye(ItemStack defaultInstance) {
        List<ItemStack> stacks = new ArrayList<>();
        stacks.add(defaultInstance);
        for (DyeColor dye : DyeColor.values()) {
            stacks.add(dye(defaultInstance, dye));
        }
        return stacks;
    }

    public static ItemStack dye(ItemStack defaultInstance, DyeColor dye) {
        if (defaultInstance.is(SlimeItemTags.DYEABLE_SLIME)) {
            var copy = defaultInstance.copy();
            copy.set(SlimeDataComponents.DYED_COLOR, dye);
            return copy;
        }
        if (defaultInstance.is(Items.SLIME_BLOCK)) {
            return SlimeBlocks.SLIME_BLOCKS.get(dye).asItem().getDefaultInstance();
        }
        return defaultInstance;
    }

    public static Item getFroglight(Frog frog) {
        ResourceKey<FrogVariant> variant = frog.getVariant().unwrapKey().get();
        if (variant.equals(FrogVariants.TEMPERATE)) {
            return Items.OCHRE_FROGLIGHT;
        }
        else if (variant.equals(FrogVariants.COLD)) {
            return Items.VERDANT_FROGLIGHT;
        }
        else if (variant.equals(FrogVariants.WARM)) {
            return Items.PEARLESCENT_FROGLIGHT;
        } else if (variant.identifier().equals(Identifier.fromNamespaceAndPath("instantfeedback", "dark"))) {
            return BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath("instantfeedback", "cerulean_froglight"));
        } else if (variant.identifier().equals(Identifier.fromNamespaceAndPath("nomansland", "mud"))) {
            return BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath("nomansland", "vermillion_froglight"));
        }
        return Items.AIR;
    }
}
