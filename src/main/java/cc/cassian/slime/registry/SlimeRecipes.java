package cc.cassian.slime.registry;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.recipe.SlimeDyeRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class SlimeRecipes {
    public static final RecipeSerializer<SlimeDyeRecipe> SLIME_DYE_RECIPE_SERIALIZER = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER,
            SlimeTime.of("dye"),
            SlimeDyeRecipe.SERIALIZER
    );

    public static void touch() {

    }
}
