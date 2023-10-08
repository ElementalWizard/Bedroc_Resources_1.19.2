package com.alexvr.bedres.entities;

import com.alexvr.bedres.setup.Registration;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;


public class LightProjectileEntity extends ThrowableProjectile {

    public LightProjectileEntity(EntityType<? extends ThrowableProjectile> type, Level levelIn) {
        super(type, levelIn);
    }

    public LightProjectileEntity(EntityType<? extends ThrowableProjectile> type, LivingEntity livingEntityIn, Level levelIn) {
        super(type, livingEntityIn, levelIn);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide && this.tickCount > 1) {
            for (float i = 0; i <= 1; i += 0.2F) {
                level().addParticle(Registration.LIGHT_PARTICLE_TYPE.get(),
                        Mth.lerp(i, this.xOld, this.getX()),
                        Mth.lerp(i, this.yOld, this.getY()),
                        Mth.lerp(i, this.zOld, this.getZ()),
                        this.random.nextGaussian() * 0.01F,
                        this.random.nextGaussian() * 0.01F,
                        this.random.nextGaussian() * 0.01F);
            }
        }
    }

    @Override
    protected void onHit(HitResult result) {
        if (!this.level().isClientSide) {
            if (result instanceof BlockHitResult res) {
                var pos = res.getBlockPos().relative(res.getDirection());
                var state = this.level().getBlockState(pos);
                if (state.canBeReplaced())
                    this.level().setBlockAndUpdate(pos, Registration.LIGHT_BLOCK.get().defaultBlockState());
            } else if (result instanceof EntityHitResult entity) {
                entity.getEntity().setRemainingFireTicks(5);
            }
        }
        this.discard();
    }
}
