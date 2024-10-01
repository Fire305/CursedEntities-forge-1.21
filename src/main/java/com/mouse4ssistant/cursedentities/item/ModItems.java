package com.mouse4ssistant.cursedentities.item;

import com.mouse4ssistant.cursedentities.CursedEntitiesMod;
import com.mouse4ssistant.cursedentities.item.custom.SyringeItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
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
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
                    pTooltipComponents.add(Component.translatable("tooltip.curse.celestine_shard.tooltip.1"));
                    super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
                }
            });
    public static final RegistryObject<Item> CELESTINE_CRYSTAL = ITEMS.register("celestine_crystal",
            () -> new Item(new Item.Properties()){
                @Override
                public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
                    pTooltipComponents.add(Component.translatable("tooltip.curse.celestine_crystal.tooltip.1"));
                    super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
                }
            });

    public static final RegistryObject<Item> SYRINGE = ITEMS.register("syringe",
            () -> new SyringeItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> INFUSED_SYRINGE = ITEMS.register("infused_syringe",
            () -> new Item(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
                    pTooltipComponents.add(Component.translatable("tooltip.curse.infused_syringe.tooltip.1"));
                    super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
                }
            });
    public static final RegistryObject<Item> LITTLE_SOUL = ITEMS.register("little_soul",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CROW_FEATHER = ITEMS.register("crow_feather",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TOME = ITEMS.register("tome",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
