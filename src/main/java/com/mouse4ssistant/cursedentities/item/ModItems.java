package com.mouse4ssistant.cursedentities.item;

import com.mouse4ssistant.cursedentities.CursedEntitiesMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
/*
* how to create simple item reminder:
* add the item here
* add the item to "ModItemModelProvider"
* add item to "ModCreativeModeTabs"
* add the item to "en_us.json"
* add the texture
* */

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CursedEntitiesMod.MODID);

    public static final RegistryObject<Item> CURSED_BOOK = ITEMS.register("cursed_book",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CURSED_BOOK_SCARECROW = ITEMS.register("cursed_book_scarecrow",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CELESTINE_SHARD = ITEMS.register("celestine_shard",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CELESTINE_CRYSTAL = ITEMS.register("celestine_crystal",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
