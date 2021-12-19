package com.alexvr.bedres.items;

import com.alexvr.bedres.utils.BedrockReferences;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;


public class FluxedCreepEggItem extends SpawnEggItem {

    public FluxedCreepEggItem(Item.Properties pProperties) {
        super(EntityType.CAVE_SPIDER, 10592673, 16711680, pProperties);
    }

}