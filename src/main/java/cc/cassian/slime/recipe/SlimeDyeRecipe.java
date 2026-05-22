package cc.cassian.slime.recipe;

import cc.cassian.slime.registry.SlimeDataComponents;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;

public class SlimeDyeRecipe extends NormalCraftingRecipe {
    public static final MapCodec<SlimeDyeRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(
                            Recipe.CommonInfo.MAP_CODEC.forGetter(o -> o.commonInfo),
                            CraftingRecipe.CraftingBookInfo.MAP_CODEC.forGetter(o -> o.bookInfo),
                            Ingredient.CODEC.fieldOf("target").forGetter(o -> o.target),
                            Ingredient.CODEC.fieldOf("dye").forGetter(o -> o.dye),
                            ItemStackTemplate.CODEC.fieldOf("result").forGetter(o -> o.result)
                    )
                    .apply(i, SlimeDyeRecipe::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, SlimeDyeRecipe> STREAM_CODEC = StreamCodec.composite(
            Recipe.CommonInfo.STREAM_CODEC,
            o -> o.commonInfo,
            CraftingRecipe.CraftingBookInfo.STREAM_CODEC,
            o -> o.bookInfo,
            Ingredient.CONTENTS_STREAM_CODEC,
            o -> o.target,
            Ingredient.CONTENTS_STREAM_CODEC,
            o -> o.dye,
            ItemStackTemplate.STREAM_CODEC,
            o -> o.result,
            SlimeDyeRecipe::new
    );
    public static final RecipeSerializer<SlimeDyeRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);
    private final Ingredient target;
    private final Ingredient dye;
    private final ItemStackTemplate result;

    public SlimeDyeRecipe(
            final Recipe.CommonInfo commonInfo,
            final CraftingRecipe.CraftingBookInfo bookInfo,
            final Ingredient target,
            final Ingredient dye,
            final ItemStackTemplate result
    ) {
        super(commonInfo, bookInfo);
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
                        if (!this.dye.test(itemStack) || !itemStack.has(DataComponents.DYE)) {
                            return false;
                        }

                        hasDye = true;
                    }
                }
            }

            return hasDye && hasTarget;
        }
    }

    public ItemStack assemble(final CraftingInput input) {
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

                    dye = itemStack.getOrDefault(DataComponents.DYE, DyeColor.WHITE);
                }
            }
        }

        if (!targetStack.isEmpty() && dye != null) {
            ItemStack result = TransmuteRecipe.createWithOriginalComponents(this.result, targetStack);
            result.set(SlimeDataComponents.DYED_COLOR, dye);
            return result;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public RecipeSerializer<SlimeDyeRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    protected PlacementInfo createPlacementInfo() {
        return PlacementInfo.create(List.of(this.target, this.dye));
    }

    @Override
    public List<RecipeDisplay> display() {
        SlotDisplay.OnlyWithComponent dyesWithDyeComponent = new SlotDisplay.OnlyWithComponent(this.dye.display(), DataComponents.DYE);
        SlotDisplay targetDisplay = this.target.display();
        return List.of(
                new ShapelessCraftingRecipeDisplay(
                        List.of(targetDisplay, dyesWithDyeComponent),
                        new SlotDisplay.DyedSlotDemo(dyesWithDyeComponent, targetDisplay),
                        new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)
                )
        );
    }

    public Ingredient getTarget() {
        return target;
    }

    public Ingredient getDye() {
        return dye;
    }
}
