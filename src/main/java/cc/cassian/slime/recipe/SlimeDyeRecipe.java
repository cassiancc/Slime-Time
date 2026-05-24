package cc.cassian.slime.recipe;

import cc.cassian.slime.api.SlimeColor;
import cc.cassian.slime.registry.SlimeDataComponents;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class SlimeDyeRecipe extends CustomRecipe {
    public static final MapCodec<SlimeDyeRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(
                            CraftingBookCategory.CODEC.optionalFieldOf("category", CraftingBookCategory.MISC).forGetter(o -> o.bookInfo),
                            Ingredient.CODEC.fieldOf("target").forGetter(o -> o.target),
                            Ingredient.CODEC.fieldOf("dye").forGetter(o -> o.dye),
                            ItemStack.CODEC.fieldOf("result").forGetter(o -> o.result)
                    )
                    .apply(i, SlimeDyeRecipe::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, SlimeDyeRecipe> STREAM_CODEC = StreamCodec.composite(
            CraftingBookCategory.STREAM_CODEC,
            o -> o.bookInfo,
            Ingredient.CONTENTS_STREAM_CODEC,
            o -> o.target,
            Ingredient.CONTENTS_STREAM_CODEC,
            o -> o.dye,
            ItemStack.STREAM_CODEC,
            o -> o.result,
            SlimeDyeRecipe::new
    );
    public static final RecipeSerializer<SlimeDyeRecipe> SERIALIZER = new RecipeSerializer<>() {
        @Override
        public MapCodec<SlimeDyeRecipe> codec() {
            return MAP_CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SlimeDyeRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    };
    private final Ingredient target;
    private final Ingredient dye;
    private final ItemStack result;
    private CraftingBookCategory bookInfo;

    public SlimeDyeRecipe(
            final CraftingBookCategory bookInfo,
            final Ingredient target,
            final Ingredient dye,
            final ItemStack result
    ) {
        super(bookInfo);
        this.bookInfo = bookInfo;
        this.target = target;
        this.dye = dye;
        this.result = result;
    }

    public boolean matches(final CraftingInput input, final Level level) {
        if (input.ingredientCount() < 2) {
            return false;
        } else {
            boolean hasTarget = false;
            boolean hasDye = false;

            for (int slot = 0; slot < input.size(); slot++) {
                ItemStack itemStack = input.getItem(slot);
                if (!itemStack.isEmpty()) {
                    if (this.target.test(itemStack)) {
                        if (hasTarget) {
                            return false;
                        }

                        hasTarget = true;
                    } else {
                        if (!this.dye.test(itemStack) || !(itemStack.getItem() instanceof DyeItem)) {
                            return false;
                        }

                        hasDye = true;
                    }
                }
            }

            return hasDye && hasTarget;
        }
    }

    public ItemStack assemble(final CraftingInput input, HolderLookup.Provider registries) {
        DyeColor dye = null;
        ItemStack targetStack = ItemStack.EMPTY;

        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack itemStack = input.getItem(slot);
            if (!itemStack.isEmpty()) {
                if (this.target.test(itemStack)) {
                    if (!targetStack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }

                    targetStack = itemStack;
                } else {
                    if (!this.dye.test(itemStack)) {
                        return ItemStack.EMPTY;
                    }

                    dye = ((DyeItem) itemStack.getItem()).getDyeColor();
                }
            }
        }

        if (!targetStack.isEmpty() && dye != null) {
            ItemStack result = this.result.copy();
            result.set(SlimeDataComponents.DYED_COLOR, SlimeColor.byDyeColor(dye));
            return result;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public RecipeSerializer<SlimeDyeRecipe> getSerializer() {
        return SERIALIZER;
    }

    public Ingredient getTarget() {
        return target;
    }

    public Ingredient getDye() {
        return dye;
    }
}
