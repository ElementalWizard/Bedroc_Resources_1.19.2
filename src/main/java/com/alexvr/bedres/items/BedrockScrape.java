package com.alexvr.bedres.items;

import com.alexvr.bedres.utils.IDisplayFlux;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BedrockScrape extends Item implements IDisplayFlux {

    public BedrockScrape(Item.Properties pProperties) {
        super(pProperties);
    }


    @Override
    public boolean shouldDisplay(ItemStack offHand) {
        return true;
    }
}
