package cc.cassian.slime.recipe;

import cc.cassian.slime.api.SlimeColor;
import cc.cassian.slime.registry.SlimeDataComponents;
import cc.cassian.slime.util.SlimeHelpers;
import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class SlimeShapedRecipe extends CustomRecipe {
    public static final MapCodec<SlimeShapedRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(
                            CraftingBookCategory.CODEC.optionalFieldOf("category", CraftingBookCategory.MISC).forGetter(o -> o.bookInfo),
                            ShapedRecipePattern.MAP_CODEC.forGetter(o -> o.pattern),
                            ItemStack.CODEC.fieldOf("result").forGetter(o -> o.result)
                    )
                    .apply(i, SlimeShapedRecipe::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, SlimeShapedRecipe> STREAM_CODEC = StreamCodec.composite(
            CraftingBookCategory.STREAM_CODEC,
            o -> o.bookInfo,
            ShapedRecipePattern.STREAM_CODEC,
            o -> o.pattern,
            ItemStack.STREAM_CODEC,
            o -> o.result,
            SlimeShapedRecipe::new
    );
    public static final RecipeSerializer<SlimeShapedRecipe> SERIALIZER = new RecipeSerializer<>() {
        @Override
        public MapCodec<SlimeShapedRecipe> codec() {
            return MAP_CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SlimeShapedRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    };
    private final ShapedRecipePattern pattern;
    public final ItemStack result;
    private final CraftingBookCategory bookInfo;

    public SlimeShapedRecipe(
            final CraftingBookCategory bookInfo, final ShapedRecipePattern pattern, final ItemStack result
    ) {
        super(bookInfo);
        this.bookInfo = bookInfo;
        this.pattern = pattern;
        this.result = result;
    }

    @Override
    public RecipeSerializer<SlimeShapedRecipe> getSerializer() {
        return SERIALIZER;
    }

    @VisibleForTesting
    public NonNullList<Ingredient> getIngredients() {
        return this.pattern.ingredients();
    }

    public boolean matches(final CraftingInput input, final Level level) {
        return this.pattern.matches(input);
    }

    public ItemStack assemble(final CraftingInput input, HolderLookup.Provider registries) {
        var result = this.result;
        if (input.items().stream().allMatch(s->s.has(SlimeDataComponents.DYED_COLOR))) {
            var dyedStack = input.items().stream().filter(s->s.has(SlimeDataComponents.DYED_COLOR)).findFirst().get();
            SlimeColor color = dyedStack.get(SlimeDataComponents.DYED_COLOR);
            if (input.items().stream().allMatch(s->s.get(SlimeDataComponents.DYED_COLOR).equals(color)))
                return SlimeHelpers.dye(result, color);
        }
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= this.pattern.width() && height >= this.pattern.height();
    }

    public int getWidth() {
        return this.pattern.width();
    }

    public int getHeight() {
        return this.pattern.height();
    }
}
