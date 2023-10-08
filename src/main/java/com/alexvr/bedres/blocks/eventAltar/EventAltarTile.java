package com.alexvr.bedres.blocks.eventAltar;

import com.alexvr.bedres.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;


public class EventAltarTile extends BlockEntity {
    
    public EventAltarTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.EVENT_ALTAR_TILE.get(), pWorldPosition, pBlockState);

    }

    public void tick() {
    }
}
