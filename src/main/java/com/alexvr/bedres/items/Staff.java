package com.alexvr.bedres.items;

import com.alexvr.bedres.BedrockResources;
import com.mojang.math.Vector3f;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class Staff extends TieredItem {
    private static final UUID REDUCED_GRAVITY_ID = UUID.fromString("DEB06000-7979-4242-8888-00000DEB0600");
    private static final AttributeModifier REDUCED_GRAVITY = (new AttributeModifier(REDUCED_GRAVITY_ID, "Reduced gravity", (double)-0.80, AttributeModifier.Operation.MULTIPLY_TOTAL));

    boolean active = false;
    public Staff(Item.Properties pProperties) {
        super(Tiers.DIAMOND, pProperties);
    }


    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        AttributeInstance grav = player.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
        BedrockResources.LOGGER.debug("Removed low gravity from Entity: {}", player);
        grav.removeModifier(REDUCED_GRAVITY);
        active = false;
        return super.onDroppedByPlayer(item, player);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof Player player && (!player.getMainHandItem().is(this) && !player.getOffhandItem().is(this))){
            AttributeInstance grav = player.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
            if (grav.hasModifier(REDUCED_GRAVITY))
            {
                BedrockResources.LOGGER.debug("Removed low gravity from Entity: {}", player);
                grav.removeModifier(REDUCED_GRAVITY);
            }
        }else if (pEntity instanceof Player player && (player.getMainHandItem().is(this) || player.getOffhandItem().is(this))){
            AttributeInstance grav = player.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
            if (!grav.hasModifier(REDUCED_GRAVITY))
            {
                BedrockResources.LOGGER.debug("Granted low gravity to Entity: {}", player);
                grav.addTransientModifier(REDUCED_GRAVITY);
            }
            player.resetFallDistance();
            player.fallDistance = 0;
            for (int i = 0; i < 9; i++) {
                pLevel.addParticle(new DustParticleOptions(new Vector3f(0.416f,0.051f,0.678f),2),pEntity.getOnPos().getX() + 0.8,pEntity.getOnPos().getY() + 0.4,((Player) pEntity).getOnPos().getZ() +0.8f,0,-0.4,0);
            }
        }

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        AttributeInstance grav = pPlayer.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
        if (!grav.hasModifier(REDUCED_GRAVITY))
        {
            BedrockResources.LOGGER.info("Granted low gravity to Entity: {}", pPlayer);
            grav.addTransientModifier(REDUCED_GRAVITY);
            pPlayer.resetFallDistance();
            active = true;
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }



    //    @Override
//    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
//        NBTHelper.flipBoolean(playerIn.getHeldItemMainhand(),"status");
//        return super.onItemRightClick(worldIn, playerIn, handIn);
//    }
//
//    @Override
//    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
//        if (entityIn instanceof PlayerEntity) {
//            PlayerEntity player = (PlayerEntity) entityIn;
//            if (player.getHeldItemMainhand() != stack || player.onGround || player.isPotionActive(Effects.HUNGER) ){
//                NBTHelper.setBoolean(stack,"status",false);
//            }
//            if (player.getHeldItemMainhand() == stack && !player.onGround && !player.isPotionActive(Effects.HUNGER) && NBTHelper.getBoolean(stack, "status", false)) {
//                NBTHelper.increaseInteger(stack, "counter", 1);
//                if (NBTHelper.getInteger(stack, "counter") % 20 == 0) {
//                    if (new Random().nextBoolean()) {
//                        LazyOptional<IBedrockFlux> bedrockFlux = player.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);
//                        bedrockFlux.ifPresent(flux -> {
//                            flux.fill(4);
//                        });
//                    }
//                }
//                if (NBTHelper.getInteger(stack, "counter") % (20 * 5) == 0) {
//                    if (new Random().nextBoolean()) {
//                        player.addPotionEffect(new EffectInstance(Effects.HUNGER, 20 * 5 , 2));
//                        NBTHelper.setBoolean(stack,"status",false);
//                    }
//                }
//                player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 1, 5));
//                player.setMotion(-player.getMotion().x, 0, -player.getMotion().z);
//                player.fallDistance = player.fallDistance/2;
//                player.velocityChanged = true;
//            }
//        }
//        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
//    }
//
//    @Override
//    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
//        tooltip.add(new StringTextComponent("Gravity cancel: " + NBTHelper.getBoolean(stack,"status",false)));
//        super.addInformation(stack, worldIn, tooltip, flagIn);
//    }

}
