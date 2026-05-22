package cc.cassian.slime.mixin;

import cc.cassian.slime.util.SlimeHelpers;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Item.class)
public class SlimeBallItemMixin {

    @ModifyReturnValue(method = "overrideStackedOnOther", at = @At("RETURN"))
    private boolean stackedOnOther(boolean original, final ItemStack self, final Slot slot, final ClickAction clickAction, final Player player) {
        if (!original)
            return SlimeHelpers.mergeSlimeBalls(self, slot.getItem(), player);
        return true;
    }

    @ModifyReturnValue(method = "overrideOtherStackedOnMe", at = @At("RETURN"))
    private boolean stackedOnMe(boolean original, ItemStack self, ItemStack other, Slot slot, ClickAction clickAction, Player player, SlotAccess carriedItem) {
        if (!original)
            return SlimeHelpers.mergeSlimeBalls(self, other, player);
        return true;
    }

}
