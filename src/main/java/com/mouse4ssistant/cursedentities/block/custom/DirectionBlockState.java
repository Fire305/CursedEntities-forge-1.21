package com.mouse4ssistant.cursedentities.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class DirectionBlockState extends HorizontalDirectionalBlock {

    public static final MapCodec<DirectionBlockState> CODEC = simpleCodec(DirectionBlockState::new);

    public DirectionBlockState(Properties pProperties) {
        super(pProperties);
        //default blockstate
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    //This method is used to determine which blockstate the block should have when placed.
    //We get the default blockstate (which we set in the constructor) and then change the value of the property FACING to the opposite horizontal direction.
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    //This method is used to "register" which blockstates this block can have. We only add the FACING property which is defined in the class that we extend
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

}
