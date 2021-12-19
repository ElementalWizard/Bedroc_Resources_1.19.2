package com.alexvr.bedres.utils;

import com.alexvr.bedres.blocks.scrapeTank.ScrapeTankTile;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class BigItemStackHandler extends ItemStackHandler {

    private int slotSize;
    public final ScrapeTankTile tile;
    public BigItemStackHandler(ScrapeTankTile tile, int size, int stackSize)
    {
        super(size);
        stacks = NonNullList.withSize(size, ItemStack.EMPTY);
        this.slotSize = stackSize;
        this.tile = tile;
    }

    public int getSlotLimit(int slot) {
        return slotSize;
    }

    protected int getStackLimit(int slot, @Nonnull ItemStack stack)
    {
        return Math.max(getSlotLimit(slot), stack.getMaxStackSize());
    }

    @Override
    protected void onContentsChanged(int slot) {
        tile.setChanged();
    }

    @Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
    {
        if (stack.isEmpty())
            return ItemStack.EMPTY;

        if (!isItemValid(slot, stack))
            return stack;

        validateSlotIndex(slot);

        ItemStack existing = this.stacks.get(slot);

        int limit = getStackLimit(slot, stack);

        if (!existing.isEmpty())
        {
            limit -= existing.getCount();
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate)
        {
            if (existing.isEmpty())
            {
                this.stacks.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
            }
            else
            {
                existing.grow(reachedLimit ? limit : stack.getCount());
            }
            onContentsChanged(slot);
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount()- limit) : ItemStack.EMPTY;
    }

}
