package cc.cassian.slime.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Objects;

public class SlimeHelpers {
    public static boolean mergeSlimeBalls(ItemStack self, ItemStack other, Player player) {
        if (self.is(Items.SLIME_BALL) && other.is(Items.SLIME_BALL) && (self.has(DataComponents.DYED_COLOR) || other.has(DataComponents.DYED_COLOR))) {
            Integer selfColor = null;
            Integer otherColor = null;
            Integer finalColor = null;
            if (self.has(DataComponents.DYED_COLOR)) {
                selfColor = Objects.requireNonNull(self.get(DataComponents.DYED_COLOR)).rgb();
            }
            if (other.has(DataComponents.DYED_COLOR)) {
                otherColor = Objects.requireNonNull(other.get(DataComponents.DYED_COLOR)).rgb();
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
}
