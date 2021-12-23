package com.alexvr.bedres.utils;

import net.minecraft.core.Direction;
import net.minecraftforge.items.ItemStackHandler;


public class DirectionalItemStackHandler extends ItemStackHandler {

    public Direction directionLocated;
    public DirectionalItemStackHandler(int size, Direction direction)
    {
        super(size);
        this.directionLocated = direction;
    }

}
