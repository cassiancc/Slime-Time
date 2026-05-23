package cc.cassian.slime.api;

import net.minecraft.world.item.ItemStack;

public interface BucketableCubeMob {
    default ItemStack slimeTime$getBucketItem() {
        return ItemStack.EMPTY;
    }

    default int slimeTime$getSize() {
        return 1;
    }
}
