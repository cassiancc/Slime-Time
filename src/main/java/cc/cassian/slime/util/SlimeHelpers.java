package cc.cassian.slime.util;

import cc.cassian.slime.registry.SlimeDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.*;
import org.apache.commons.lang3.text.WordUtils;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SlimeHelpers {
    public static boolean mergeSlimeBalls(ItemStack self, ItemStack other, Player player) {
        if (self.is(Items.SLIME_BALL) && other.is(Items.SLIME_BALL) && (self.has(SlimeDataComponents.DYED_COLOR) || other.has(DataComponents.DYED_COLOR))) {
            Integer selfColor = null;
            Integer otherColor = null;
            Integer finalColor = null;
            if (self.has(SlimeDataComponents.DYED_COLOR)) {
                selfColor = Objects.requireNonNull(self.get(SlimeDataComponents.DYED_COLOR)).getTextureDiffuseColor();
            }
            if (other.has(SlimeDataComponents.DYED_COLOR)) {
                otherColor = Objects.requireNonNull(other.get(SlimeDataComponents.DYED_COLOR)).getTextureDiffuseColor();
            }
            if (selfColor != null && otherColor != null) {
                finalColor = ARGB.alphaBlend(selfColor, otherColor);
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
            tooltip.add(Component.translatable("item.color", WordUtils.capitalizeFully(color.getName().replace("_", " "))).withStyle(ChatFormatting.GRAY));
        }

    }
}
