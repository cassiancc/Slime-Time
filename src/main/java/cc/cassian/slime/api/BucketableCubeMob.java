package cc.cassian.slime.api;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;

public interface BucketableCubeMob {
    default ItemStack slimeTime$getBucketItem() {
        return ItemStack.EMPTY;
    }

    default SoundEvent slimeTime$getPickupSound() {
        return SoundEvents.BUCKET_FILL;
    }

    default int slimeTime$getSize() {
        return 1;
    }
}
