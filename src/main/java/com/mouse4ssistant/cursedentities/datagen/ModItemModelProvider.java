package com.mouse4ssistant.cursedentities.datagen;

import com.mouse4ssistant.cursedentities.CursedEntitiesMod;
import com.mouse4ssistant.cursedentities.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CursedEntitiesMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.CURSED_TOME.get());
        basicItem(ModItems.CURSED_TOME_SCARECROW.get());
        basicItem(ModItems.CELESTINE_CRYSTAL.get());
        basicItem(ModItems.CELESTINE_SHARD.get());
        basicItem(ModItems.SYRINGE.get());
        basicItem(ModItems.INFUSED_SYRINGE.get());
        basicItem(ModItems.LITTLE_SOUL.get());
        basicItem(ModItems.CROW_FEATHER.get());
        basicItem(ModItems.TOME.get());
    }
}
