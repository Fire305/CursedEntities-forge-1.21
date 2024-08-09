package com.mouse4ssistant.cursedentities.block;

import com.mouse4ssistant.cursedentities.CursedEntitiesMod;
import com.mouse4ssistant.cursedentities.block.custom.DirectionBlockState;
import com.mouse4ssistant.cursedentities.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;
/* how to add a block remainder:
add the block here
add the loot tables in "ModBlockLootTables" in datagen
add the block in "ModCreativeModeTabs"
if the block as different faces create the "blockstate","models/block" and "models/item"
else if the block is simple add it in "ModBLockStateProvider"
add the block in "en_us.json"
add the texture
*
*
* */

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CursedEntitiesMod.MODID);

    public static final RegistryObject<Block> DEMON_CARVED_PUMPKIN = registryBlock("demon_carved_pumpkin",
            ()-> new DirectionBlockState(BlockBehaviour.Properties.of()
                    .strength(1f).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> INFUSED_DEMON_CARVED_PUMPKIN = registryBlock("infused_demon_carved_pumpkin",
            ()-> new DirectionBlockState(BlockBehaviour.Properties.of()
                    .strength(1f).sound(SoundType.WOOD)));


    private static <T extends Block> RegistryObject<T> registryBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}











