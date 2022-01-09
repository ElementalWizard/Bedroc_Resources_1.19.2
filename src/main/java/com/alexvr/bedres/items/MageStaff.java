package com.alexvr.bedres.items;

import com.alexvr.bedres.setup.Registration;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class MageStaff extends Item {

    public MageStaff(Properties pProperties) {
        super(pProperties);
    }
    String particlDirection = "up";
    int height = 2;
    double yIncrement = 0.2;
    double radius = 1.5;
    double a = 0;
    double x = 0;
    double y = 0;
    double z = 0;

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        for (int i =0; i< 64;i++){
            summonParticles(pLevel, pPlayer);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }


    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        summonParticles(pLevel, pEntity);
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    private void summonParticles(Level pLevel, Entity pEntity) {
        if (pEntity instanceof Player player ){
            if (player.getMainHandItem().is(Registration.MAGE_STAFF_ITEM.get()) || player.getOffhandItem().is(Registration.MAGE_STAFF_ITEM.get())){


                x = (cos(a) * radius);
                z = sin(a) * radius;
                pLevel.addParticle(ParticleTypes.REVERSE_PORTAL,player.getX() + x,player.getY() + y,player.getZ() + z,0,0,0);
                a++;
                if(particlDirection.equals("up"))
                {
                    if(y >= height)
                    {
                        particlDirection = "down";
                        y -= yIncrement;
                    }
                    else
                    {
                        y += yIncrement;
                    }
                }
                else
                {
                    if(y <= 0)
                    {
                        particlDirection = "up";
                        y += yIncrement;
                    }
                    else
                    {
                        y -= yIncrement;
                    }
                }

                if(a >= 360){a = 0;} //reset a to stop it getting too large
            }
        }
    }
}
