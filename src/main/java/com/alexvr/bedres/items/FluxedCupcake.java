package com.alexvr.bedres.items;

import com.alexvr.bedres.utils.BedrockReferences;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class FluxedCupcake extends Item {
    public static final FoodProperties CUPCAKE = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.3F).build();

    public FluxedCupcake(Item.Properties pProperties) {
        super(pProperties.food(CUPCAKE));
    }

//    @Override
//    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
//        LazyOptional<IBedrockFlux> abilities = entityLiving.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);
//        abilities.ifPresent(flux -> {
//            flux.consume(flux.getBedrockFlux()/8);
//        });
//        return super.onItemUseFinish(stack, worldIn, entityLiving);
//    }
}
