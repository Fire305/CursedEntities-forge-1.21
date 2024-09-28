package com.mouse4ssistant.cursedentities.block.entity;

import com.mouse4ssistant.cursedentities.CursedEntitiesMod;
import com.mouse4ssistant.cursedentities.block.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CursedEntitiesMod.MODID);

    public static final RegistryObject<BlockEntityType<CursedTableBlockEntity>> CURSED_TABLE_BE =
            BLOCK_ENTITIES.register("cursed_table_be", () ->
                    BlockEntityType.Builder.of(CursedTableBlockEntity::new,
                            ModBlocks.CURSED_TABLE.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
