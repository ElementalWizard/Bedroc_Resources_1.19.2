package com.alexvr.bedres.items;

import com.alexvr.bedres.setup.Registration;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

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
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (!(pLivingEntity instanceof Player)) {
            return;
        }
        Player player = (Player) pLivingEntity;

        // don't allow free flight when using an elytra, should use fireworks
        if (player.isFallFlying()) {
            return;
        }

        player.causeFoodExhaustion(0.2F);
        player.setSprinting(true);

        float f = getForce(pStack, pTimeCharged);
        float speed = f / 3F;
        Vec3 look = player.getLookAngle();
        player.push(
                (look.x * speed),
                (1 + look.y) * speed / 2f,
                (look.z * speed));

        if (pLevel.isClientSide()) {
            player.getCooldowns().addCooldown(pStack.getItem(), 3);
        }
        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    /** Determines how much force a charged right click item will release on player letting go
     * To be used in conjunction with onPlayerStoppedUsing
     * @param stack - Item used (get from onPlayerStoppedUsing)
     * @param timeLeft - (get from onPlayerStoppedUsing)
     * @return appropriate charge for item */
    public float getForce(ItemStack stack, int timeLeft) {
        int i = this.getUseDuration(stack) - timeLeft;
        float f = i / 30.0F;
        f = (f * f + f * 2.0F) / 6.0F;
        f *= 4f;

        if (f > 16) {
            f = 16;
        }
        return f;
    }


    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        for (int i =0; i< 32;i++){
            summonParticles(player.level, player);
        }
        super.onUsingTick(stack, player, count);
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
