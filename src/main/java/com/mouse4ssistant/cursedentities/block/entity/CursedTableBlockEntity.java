package com.mouse4ssistant.cursedentities.block.entity;

import com.mouse4ssistant.cursedentities.item.ModItems;
import com.mouse4ssistant.cursedentities.screen.CursedTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CursedTableBlockEntity extends BlockEntity implements MenuProvider{

    private final ItemStackHandler inventory = new ItemStackHandler(4) {
      @Override
      protected void onContentsChanged(int slot) {
          setChanged();
          if(!level.isClientSide()) {
              level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
          }
      }
    };


    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final SimpleContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public CursedTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CURSED_TABLE_BE.get(), pPos, pBlockState);
        this.data = new SimpleContainerData(4) {
            @Override
            public int get(int i) {
                return switch (i){
                    case 0 -> CursedTableBlockEntity.this.progress;
                    case 1 -> CursedTableBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int i1) {
                switch (i){
                    case 0 -> CursedTableBlockEntity.this.progress = i1;
                    case 1 -> CursedTableBlockEntity.this.maxProgress = i1;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            if(side == null) {
                return lazyItemHandler.cast();
            }
        }

        return super.getCapability(cap, side);
    }


    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> inventory);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops(){
        SimpleContainer container = new SimpleContainer(inventory.getSlots());
        for (int i=0; i<inventory.getSlots(); i++){
            container.setItem(i, inventory.getStackInSlot(i));

        }
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.curse.cursed_table");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainer, Inventory inventory, Player player) {
        return new CursedTableMenu(pContainer, inventory, this, this.data);
    }


    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        pTag.put("inventory", inventory.serializeNBT(pRegistries));
        pTag.putInt("cursed_table.progress", progress);
        pTag.putInt("cursed_table.max_progress", maxProgress);

        super.saveAdditional(pTag, pRegistries);
    }


    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);

        inventory.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
        progress = pTag.getInt("cursed_table.progress");
        maxProgress = pTag.getInt("cursed_table.max_progress");
    }


    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if(hasRecipe()){
            increaseCraftingProgress();
            setChanged(level, blockPos, blockState);

            if(hasProgressFinished()){
                craftItem();
                resetProgress();
            }
        }else {
            resetProgress();
        }
    }

    private void resetProgress() {
        progress=0;
    }

    private void craftItem() {
        ItemStack result = new ItemStack(ModItems.CURSED_BOOK_SCARECROW.get(), 1);
        this.inventory.extractItem(1, 1, false);
        this.inventory.extractItem(2, 1, false);

        this.inventory.setStackInSlot(3, new ItemStack(result.getItem(),
                this.inventory.getStackInSlot(3).getCount()+result.getCount()));
    }

    private boolean hasRecipe() {
        boolean hasCraftingItem1 = this.inventory.getStackInSlot(0).getItem()== ModItems.CURSED_BOOK.get();
        boolean hasCraftingItem2 = this.inventory.getStackInSlot(1).getItem()== ModItems.LITTLE_SOUL.get();
        boolean hasCraftingItem3 = this.inventory.getStackInSlot(2).getItem()== ModItems.CROW_FEATHER.get();
        ItemStack result = new ItemStack(ModItems.CURSED_BOOK_SCARECROW.get());

        return hasCraftingItem1 && hasCraftingItem2 && hasCraftingItem3 && canInsertAmmountIntoOutputSlot(result.getCount())&& canInsertItemIntoOutputSlot(result.getItem());

    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        //itemHandler
        return this.inventory.getStackInSlot(3).isEmpty() || this.inventory.getStackInSlot(3).is(item);
    }

    private boolean canInsertAmmountIntoOutputSlot(int count) {
        //itemHandler
        return this.inventory.getStackInSlot(3).getCount() + count <= this.inventory.getStackInSlot(3).getMaxStackSize();
    }

    private boolean hasProgressFinished() {
        return progress>=maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

}
