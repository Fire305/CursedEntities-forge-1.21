package com.mouse4ssistant.cursedentities.item;

import com.mouse4ssistant.cursedentities.CursedEntitiesMod;
import com.mouse4ssistant.cursedentities.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CursedEntitiesMod.MODID);

    public static final RegistryObject<CreativeModeTab> ESSENCE_OF_EXPLORATION_TAB = CREATIVE_MODE_TABS.register("cursed_entities_tab",
            () -> CreativeModeTab.builder().icon(()-> new ItemStack(ModItems.CURSED_BOOK.get()))
                    .title(Component.translatable("creativetab.cursed_entities"))
                    .displayItems(((itemDisplayParameters, output) -> {
                        //order in which objects are visible
                        output.accept(ModItems.CURSED_BOOK.get());
                        output.accept(ModBlocks.DEMON_CARVED_PUMPKIN.get());
                        output.accept(ModBlocks.INFUSED_DEMON_CARVED_PUMPKIN.get());
                        output.accept(ModItems.CURSED_BOOK_SCARECROW.get());
                        output.accept(ModItems.CELESTINE_SHARD.get());
                        output.accept(ModItems.CELESTINE_CRYSTAL.get());
                        output.accept(ModBlocks.CELESTINE_ORE.get());
                        output.accept(ModBlocks.CELESTINE_DEEPSLATE_ORE.get());

                    })).build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
