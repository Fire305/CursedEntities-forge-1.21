package com.mouse4ssistant.cursedentities.item.custom;

import com.mouse4ssistant.cursedentities.item.ModItems;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class SyringeItem extends Item {
    public SyringeItem(Properties properties) {
        super(properties);
    }


    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        super.hurtEnemy(pStack, pTarget, pAttacker);
        if (pAttacker instanceof Player player){
            if (player.hasInfiniteMaterials()) {
                player.addItem(new ItemStack(ModItems.INFUSED_SYRINGE.get()));
            }
            else {
                pStack.shrink(1);
                player.addItem(new ItemStack(ModItems.INFUSED_SYRINGE.get()));
            }
            if(pTarget instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 400, 1), player);
            }
        }
        return true;
    }
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        if(!Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.curse.syringe.tooltip.shift"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.curse.syringe.tooltip.1"));
        }

        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }
}

