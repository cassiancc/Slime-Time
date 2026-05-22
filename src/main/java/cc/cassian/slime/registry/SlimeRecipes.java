package cc.cassian.slime.registry;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.recipe.SlimeDyeRecipe;
import cc.cassian.slime.recipe.SlimeShapedRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jspecify.annotations.NonNull;

public class SlimeRecipes {
    public static final RecipeSerializer<SlimeDyeRecipe> SLIME_DYE_RECIPE_SERIALIZER = register("dye", SlimeDyeRecipe.SERIALIZER);
    public static final RecipeSerializer<SlimeShapedRecipe> SLIME_SHAPED_RECIPE_SERIALIZER = register("crafting_shaped", SlimeShapedRecipe.SERIALIZER);

    private static <T extends Recipe<?>> RecipeSerializer<T> register(String name, RecipeSerializer<T> serializer) {
        return Registry.register(
                BuiltInRegistries.RECIPE_SERIALIZER,
                SlimeTime.of(name),
                serializer
        );
    }

    public static void touch() {

    }
}
