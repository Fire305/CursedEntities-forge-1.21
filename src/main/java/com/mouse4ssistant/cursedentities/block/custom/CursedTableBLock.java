package com.mouse4ssistant.cursedentities.block.custom;

import com.mojang.serialization.MapCodec;
import com.mouse4ssistant.cursedentities.item.ModItems;
import com.mouse4ssistant.cursedentities.screen.CursedTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;


import javax.annotation.Nullable;


public class CursedTableBLock extends HorizontalDirectionalBlock implements MenuProvider {
    public static final EnumProperty<ChestType> PART = BlockStateProperties.CHEST_TYPE;

    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 18, 16);
    public static final VoxelShape SHAPE_TABLETOP = Block.box(0, 9, 0, 16, 14, 16);
    public static final VoxelShape SHAPE_LEG_1 = Block.box(1, 0, 1, 4, 10, 4);
    public static final VoxelShape SHAPE_LEG_2 = Block.box(12, 0, 1, 15, 10, 4);
    public static final VoxelShape SHAPE_LEG_3 = Block.box(1, 0, 12, 4, 10, 15);
    public static final VoxelShape SHAPE_LEG_4 = Block.box(12, 0, 12, 15, 10, 15);
    public static final VoxelShape SHAPE_LEGS_EAST = Shapes.or(SHAPE_LEG_2, SHAPE_LEG_4, SHAPE_TABLETOP);
    public static final VoxelShape SHAPE_LEGS_WEST = Shapes.or(SHAPE_LEG_1, SHAPE_LEG_3, SHAPE_TABLETOP);
    public static final VoxelShape SHAPE_LEGS_NORTH = Shapes.or(SHAPE_LEG_3, SHAPE_LEG_4, SHAPE_TABLETOP);
    public static final VoxelShape SHAPE_LEGS_SOUTH = Shapes.or(SHAPE_LEG_1, SHAPE_LEG_2, SHAPE_TABLETOP);

    private final ItemStackHandler itemHandler = new ItemStackHandler(4);

    private static final int INPUT_SLOT_1 = 0;
    private static final int INPUT_SLOT_2 = 1;
    private static final int INPUT_SLOT_3 = 2;
    private static final int OUTPUT_SLOT = 3;

    public CursedTableBLock(Properties pProperties) {
        super(pProperties);
    }

    public BlockState updateShape(BlockState myState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos myPos, BlockPos pFacingPos) {
        ChestType half = myState.getValue(PART);
        BlockPos requiredNeighborPos = myPos.relative(getNeighbourDirection(half, myState.getValue(FACING)));
        BlockState neighborState = pLevel.getBlockState(requiredNeighborPos);
        if (!neighborState.is(this)) {
            var air = Blocks.AIR.defaultBlockState();
            //manually set to prevent block from dropping
            pLevel.setBlock(myPos, air, 35);
            pLevel.levelEvent(null, 2001, myPos, Block.getId(air));
            return air;
        }
        return super.updateShape(myState, pFacing, pFacingState, pLevel, myPos, pFacingPos);
    }

    private static Direction getNeighbourDirection(ChestType pPart, Direction pDirection) {
        return pPart == ChestType.LEFT ? pDirection.getCounterClockWise() : pDirection.getClockWise();
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(PART).equals(ChestType.RIGHT) ? pState.getValue(FACING) : pState.getValue(FACING).getOpposite();
        return switch (direction) {
            case NORTH -> SHAPE_LEGS_WEST;
            case SOUTH -> SHAPE_LEGS_EAST;
            case WEST -> SHAPE_LEGS_NORTH;
            default -> SHAPE_LEGS_SOUTH;
        };
    }

    @javax.annotation.Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction direction = pContext.getHorizontalDirection();
        BlockPos blockpos = pContext.getClickedPos();
        BlockPos blockpos1 = blockpos.relative(direction.getCounterClockWise());
        Level level = pContext.getLevel();
        if (level.getBlockState(blockpos1).canBeReplaced(pContext) && level.getWorldBorder().isWithinBounds(blockpos1)) {
            return this.defaultBlockState().setValue(FACING, direction.getOpposite());
        }

        return null;
    }

    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if (!pLevel.isClientSide) {
            BlockPos blockpos = pPos.relative(pState.getValue(FACING).getClockWise());
            pLevel.setBlock(blockpos, pState.setValue(PART, ChestType.LEFT), 3);
            pLevel.setBlock(pPos, pState.setValue(PART, ChestType.RIGHT), 3);
            pLevel.blockUpdated(pPos, Blocks.AIR);
            pState.updateNeighbourShapes(pLevel, pPos, 3);
        }

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        if (blockState.getValue(PART).equals(ChestType.RIGHT))
            return RenderShape.MODEL;
        else
            return RenderShape.INVISIBLE;
    }

    public PushReaction getPistonPushReaction(BlockState pState) {
        return PushReaction.BLOCK;
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return null;
    }

    //menu:

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.curse.cursed_table");
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            pPlayer.openMenu(pState.getMenuProvider(pLevel, pPos));
            return InteractionResult.CONSUME;
        }
    }

    private void craftItem() {
        ItemStack result = new ItemStack(ModItems.CURSED_BOOK_SCARECROW.get(), 1);
        this.itemHandler.extractItem(INPUT_SLOT_1, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_2, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_3, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    private boolean hasRecipe() {
        boolean hasCraftingItem1 = this.itemHandler.getStackInSlot(INPUT_SLOT_1).getItem()== ModItems.CURSED_BOOK.get();
        boolean hasCraftingItem2 = this.itemHandler.getStackInSlot(INPUT_SLOT_2).getItem()== ModItems.LITTLE_SOUL.get();
        boolean hasCraftingItem3 = this.itemHandler.getStackInSlot(INPUT_SLOT_3).getItem()== ModItems.CROW_FEATHER.get();
        ItemStack result = new ItemStack(ModItems.CURSED_BOOK_SCARECROW.get());

        return hasCraftingItem1 && hasCraftingItem2 && hasCraftingItem3 && canInsertAmmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContaionerId, Inventory inventory, Player player) {
        return null;
    }
}



