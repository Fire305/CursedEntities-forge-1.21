package com.mouse4ssistant.cursedentities.datagen;

import com.mouse4ssistant.cursedentities.block.ModBlocks;
import com.mouse4ssistant.cursedentities.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SYRINGE.get())
                .pattern(" E ")
                .pattern(" A ")
                .pattern(" B ")
                .define('E', ModItems.CELESTINE_CRYSTAL.get())
                .define('A', Items.GLASS_PANE)
                .define('B', Items.IRON_INGOT)

                .unlockedBy("has_celestine_crystal", has(ModItems.CELESTINE_CRYSTAL.get())).save(recipeOutput);

        //recipe for CELESTINE_CRYSTAL
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CELESTINE_CRYSTAL.get(), 1)
                .requires(ModItems.CELESTINE_SHARD.get())
                .requires(ModItems.CELESTINE_SHARD.get())
                .requires(ModItems.CELESTINE_SHARD.get())
                .requires(ModItems.CELESTINE_SHARD.get())
                .unlockedBy("has_celestine_shard", has(ModItems.CELESTINE_SHARD.get())).save(recipeOutput);
    }
}
