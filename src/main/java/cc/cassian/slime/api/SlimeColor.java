package cc.cassian.slime.api;

import cc.cassian.slime.registry.SlimeParticleTypes;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.FastColor;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntFunction;

import static cc.cassian.slime.util.SlimeHelpers.getSynchronizedRecipe;

public enum SlimeColor implements StringRepresentable {
    WHITE(0, "white", 0xf9fffe),
    ORANGE(1, "orange", 0xf9801d),
    MAGENTA(2, "magenta", 0xc74ebd),
    LIGHT_BLUE(3, "light_blue", 0x3ab3da),
    YELLOW(4, "yellow", 0xfed83d),
    LIME(5, "lime", 0xff90ff85),
    PINK(6, "pink", 0xf38baa),
    GRAY(7, "gray", 0x474f52),
    LIGHT_GRAY(8, "light_gray", 0x9d9d97),
    CYAN(9, "cyan", 0x169c9c),
    PURPLE(10, "purple", 0x8932b8),
    BLUE(11, "blue", 0x3c44aa),
    BROWN(12, "brown", 0x835432),
    GREEN(13, "green", 0x5e7c16),
    RED(14, "red", 0xb02e26),
    BLACK(15, "black", 0x1d1d21);

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
        this.argb = FastColor.ARGB32.opaque(rgb);
    }

    public static @Nullable SlimeColor decode(CompoundTag entityData) {
        if (entityData.contains("SlimeTimeColor"))
            return CODEC.decode(NbtOps.INSTANCE, entityData.get("SlimeTimeColor")).getOrThrow().getFirst();
        return null;
    }

    public void encode(CompoundTag tag) {
        tag.put("SlimeTimeColor", CODEC.encodeStart(NbtOps.INSTANCE, this).getOrThrow());
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

    public static boolean isDefault(@Nullable SlimeColor color) {
        return color == null || color == LIME;
    }

    /// Variant of DyeColor.getMixedColor that uses recipe synchronization APIs.
    public static SlimeColor getMixedColor(final Level level, final SlimeColor dyeColor1, final SlimeColor dyeColor2) {
        SlimeColor mixedColor = findColorMixInRecipes(level, DyeColor.byId(dyeColor1.id), DyeColor.byId(dyeColor2.id));
        if (mixedColor != null) {
            return mixedColor;
        } else {
            return level.getRandom().nextBoolean() ? dyeColor1 : dyeColor2;
        }
    }

    /// Variant of DyeColor.findColorMixInRecipes that uses recipe synchronization APIs.
    @Nullable
    private static SlimeColor findColorMixInRecipes(final Level level, final DyeColor slimeColor1, final DyeColor slimeColor2) {
        CraftingInput craftingInput = makeCraftInput(slimeColor1, slimeColor2);
        Optional<Item> var10000 = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftingInput, level).map((recipeHolder) -> recipeHolder.value().assemble(craftingInput, level.registryAccess())).map(ItemStack::getItem);
        Objects.requireNonNull(DyeItem.class);
        var10000 = var10000.filter(DyeItem.class::isInstance);
        Objects.requireNonNull(DyeItem.class);
        return SlimeColor.byDyeColor(var10000.map(o -> (DyeItem) o).map(DyeItem::getDyeColor).orElseGet(() -> level.random.nextBoolean() ? slimeColor1 : slimeColor2));
    }

    private static CraftingInput makeCraftInput(DyeColor color1, DyeColor color2) {
        return CraftingInput.of(2, 1, List.of(new ItemStack(DyeItem.byColor(color1)), new ItemStack(DyeItem.byColor(color2))));
    }

    public static @Nullable SlimeColor byDyeColor(DyeColor craftedDyeColor) {
        return SlimeColor.byId(craftedDyeColor.getId());
    }

    public ParticleOptions getParticle() {
        if (isDefault(this)) return ParticleTypes.ITEM_SLIME;
        return ColorParticleOption.create(SlimeParticleTypes.TINTED_SLIME, argb());
    }

    public DyeColor getDyeColor() {
        return DyeColor.byId(this.getId());
    }
}

