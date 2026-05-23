package cc.cassian.slime.api;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ARGB;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.IntFunction;

import static cc.cassian.slime.util.SlimeHelpers.getSynchronizedRecipe;

public enum SlimeColor implements StringRepresentable {
    WHITE(0, "white", 16383998),
    ORANGE(1, "orange", 16351261),
    MAGENTA(2, "magenta", 13061821),
    LIGHT_BLUE(3, "light_blue", 3847130),
    YELLOW(4, "yellow", 16701501),
    LIME(5, "lime", 8439583),
    PINK(6, "pink", 15961002),
    GRAY(7, "gray", 4673362),
    LIGHT_GRAY(8, "light_gray", 10329495),
    CYAN(9, "cyan", 1481884),
    PURPLE(10, "purple", 8991416),
    BLUE(11, "blue", 3949738),
    BROWN(12, "brown", 8606770),
    GREEN(13, "green", 6192150),
    RED(14, "red", 11546150),
    BLACK(15, "black", 1908001);

    public static final List<SlimeColor> VALUES = List.of(values());
    private static final IntFunction<SlimeColor> BY_ID = ByIdMap.continuous(
            SlimeColor::getId, VALUES.toArray(SlimeColor[]::new), ByIdMap.OutOfBoundsStrategy.ZERO
    );
    public static final StringRepresentable.EnumCodec<SlimeColor> CODEC = StringRepresentable.fromEnum(SlimeColor::values);
    public static final StreamCodec<ByteBuf, SlimeColor> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, SlimeColor::getId);
    private final int id;
    private final String name;
    private final int argb;

    SlimeColor(final int id, final String name, final int rgb) {
        this.id = id;
        this.name = name;
        this.argb = ARGB.opaque(rgb);
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int argb() {
        return this.argb;
    }

    public static SlimeColor byId(final int id) {
        return BY_ID.apply(id);
    }

    @Contract("_,!null->!null;_,null->_")
    @Nullable
    public static SlimeColor byName(final String name, @Nullable final SlimeColor def) {
        SlimeColor result = CODEC.byName(name);
        return result != null ? result : def;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    /// Variant of DyeColor.getMixedColor that uses recipe synchronization APIs.
    public static SlimeColor getMixedColor(final Level level, final SlimeColor dyeColor1, final SlimeColor dyeColor2) {
        SlimeColor mixedColor = findColorMixInRecipes(level, dyeColor1, dyeColor2);
        if (mixedColor != null) {
            return mixedColor;
        } else {
            return level.getRandom().nextBoolean() ? dyeColor1 : dyeColor2;
        }
    }

    /// Variant of DyeColor.findColorMixInRecipes that uses recipe synchronization APIs.
    @Nullable
    private static SlimeColor findColorMixInRecipes(final Level level, final SlimeColor slimeColor1, final SlimeColor slimeColor2) {
        DataComponentLookup<Item> itemComponents = level.registryAccess().lookupOrThrow(Registries.ITEM).componentLookup();
        Collection<Holder<Item>> dye1Items = itemComponents.findAll(DataComponents.DYE, DyeColor.byId(slimeColor1.id));
        if (!dye1Items.isEmpty()) {
            Collection<Holder<Item>> dye2Items = itemComponents.findAll(DataComponents.DYE, DyeColor.byId(slimeColor2.id));
            if (!dye2Items.isEmpty()) {
                for (Holder<Item> dye1Item : dye1Items) {
                    for (Holder<Item> dye2Item : dye2Items) {
                        CraftingInput input = CraftingInput.of(2, 1, List.of(new ItemStack(dye1Item), new ItemStack(dye2Item)));
                        Optional<RecipeHolder<CraftingRecipe>> foundRecipe = getSynchronizedRecipe(level, input);
                        if (foundRecipe.isPresent()) {
                            ItemStack craftingResult = foundRecipe.get().value().assemble(input);
                            DyeColor craftedDyeColor = craftingResult.get(DataComponents.DYE);
                            if (craftedDyeColor != null) {
                                return SlimeColor.byDyeColor(craftedDyeColor);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static @Nullable SlimeColor byDyeColor(DyeColor craftedDyeColor) {
        return SlimeColor.byId(craftedDyeColor.getId());
    }
}

