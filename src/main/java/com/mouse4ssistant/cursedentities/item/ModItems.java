package com.mouse4ssistant.cursedentities.item;

import com.mouse4ssistant.cursedentities.CursedEntitiesMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CursedEntitiesMod.MODID);

    public static final RegistryObject<Item> CURSED_BOOK = ITEMS.register("cursed_book",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
