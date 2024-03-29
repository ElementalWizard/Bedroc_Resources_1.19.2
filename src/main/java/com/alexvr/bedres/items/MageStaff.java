package com.alexvr.bedres.items;

import com.alexvr.bedres.capability.abilities.IPlayerAbility;
import com.alexvr.bedres.capability.abilities.PlayerAbilityProvider;
import com.alexvr.bedres.entities.LightProjectileEntity;
import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.utils.IDisplayFlux;
import com.alexvr.bedres.utils.NBTHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static com.alexvr.bedres.items.MageStaff.TYPES.*;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class MageStaff extends Item implements IDisplayFlux {

    private static final double LIGHTNINGRANGE = 16;
    private static final int LIFESTEALCOUNTERMAX = 10;
    private static final int POISONCOUNTERMAX = 60;
    private static final int BONEMEALCOUNTERMAX = 20;
    private static final int GREENTICKCOUNTERMAX = 5;

    public MageStaff(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        Minecraft.getInstance().player.reviveCaps();
        LazyOptional<IPlayerAbility> playerFlux = Minecraft.getInstance().player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        if (getType(itemstack).equals(ZETA.name) && !pLevel.isClientSide()){
            playerFlux.ifPresent(k -> {
                if (k.getFlux() > 2){
                    k.removeFlux(2D);
                    getZetaEffect(pPlayer,pLevel);
                }
            });
            Minecraft.getInstance().player.invalidateCaps();
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
        }
        Minecraft.getInstance().player.invalidateCaps();
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (!(pLivingEntity instanceof Player)) {
            return;
        }
        Player player = (Player) pLivingEntity;
        Minecraft.getInstance().player.reviveCaps();
        LazyOptional<IPlayerAbility> playerFlux = Minecraft.getInstance().player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        playerFlux.ifPresent(k -> {
            if (getType(pStack).equals(ALPHA.name)){
                getAlphaEffect(player,getForce(pStack, pTimeCharged) / 3F,player.getLookAngle(),k);
            }else if (getType(pStack).equals(BETA.name)){
                getBetaEffect(player,getLightningForce(pStack,pTimeCharged),k);
            }else if (getType(pStack).equals(DELTA.name)){
                getDeltaEffect(player, getTieredForce(pStack,pTimeCharged),k);
            }
        });

        if (pLevel.isClientSide()) {
            player.getCooldowns().addCooldown(pStack.getItem(), 10);
        }
        Minecraft.getInstance().player.invalidateCaps();
        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        if (pLivingEntity instanceof Player player){
            if (player.getCooldowns().isOnCooldown(pStack.getItem()) || !player.isUsingItem()){
                return;
            }
            Minecraft.getInstance().player.reviveCaps();
            LazyOptional<IPlayerAbility> playerFlux = Minecraft.getInstance().player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
            if (getCooldown(pStack) <= 0){
                if (getType(pStack).equals(EPSILON.name)){
                    playerFlux.ifPresent(k -> {
                        if (k.getFlux() > 2){
                            getEpsilonEffect(player,k);
                        }
                    });
                    setCooldown(pStack,LIFESTEALCOUNTERMAX);
                }
                if (getType(pStack).equals(GAMA.name)){
                    playerFlux.ifPresent(k -> {
                        if (k.getFlux() > 2){
                            getGamaEffect(player,k);

                        }
                    });
                    setCooldown(pStack,POISONCOUNTERMAX);
                }
                if (pLevel instanceof ServerLevel serverLevel){
                    if (getType(pStack).equals(THETA.name)){
                        playerFlux.ifPresent(k -> getThetaGreenTickEffect(player, serverLevel, player.isShiftKeyDown(),k));
                        player.causeFoodExhaustion(0.2F);
                        if (player.isShiftKeyDown()){
                            setCooldown(pStack,BONEMEALCOUNTERMAX);
                        }else{
                            setCooldown(pStack,GREENTICKCOUNTERMAX);
                        }
                    }
                }
            }else{
                tickCooldown(pStack);
            }
            Minecraft.getInstance().player.invalidateCaps();
        }
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    }

    private void getZetaEffect(Player player, Level level) {
        var projectile = new LightProjectileEntity(Registration.LIGHT_PROJ_ENTITY.get(), player, level);
        projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1.5F, 0);
        level.addFreshEntity(projectile);
    }

    private void getThetaGreenTickEffect(Player pPlayer, ServerLevel level, boolean isShiftDown, IPlayerAbility flux) {
        BlockHitResult lookingAt = getLookingAt(pPlayer, ClipContext.Fluid.NONE);
        if (isShiftDown && flux.getFlux()>2){
            if (pPlayer.level.getBlockState(lookingAt.getBlockPos()).getBlock() instanceof BonemealableBlock bonemealableBlock){
                bonemealableBlock.performBonemeal(level, RandomSource.create(),lookingAt.getBlockPos(),pPlayer.level.getBlockState(lookingAt.getBlockPos()));
                pPlayer.getCooldowns().addCooldown(pPlayer.getUseItem().getItem(), 10);
                flux.removeFlux(2D);
            }else{
                pPlayer.sendSystemMessage(Component.translatable("bedres.mage_staff.green_thumb.fail"));
            }
        } else{
            BlockPos sourcePos = lookingAt.getBlockPos();
            for (int x = - 4; x<= 4;x++){
                for (int z = - 4; z<=4;z++){
                    BlockPos newPos = sourcePos.offset(x,0,z);
                    if (level.getBlockState(newPos).getBlock() instanceof IPlantable plantable && flux.getFlux()>0.25){
                        plantable.getPlant(level,newPos).randomTick(level,newPos, RandomSource.create());
                        flux.removeFlux(0.25);
                    }
                }
            }
        }
    }

    private void getGamaEffect(Player player, IPlayerAbility flux) {
        Level world = player.level;
        LivingEntity mob = world.getNearestEntity(LivingEntity.class, TargetingConditions.forCombat().range(8.0D),player,player.getX(),player.getY(),player.getZ(),player.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
        if (mob != null){
            int duration = 100;
            int amp = 0;
            if (mob.hasEffect(MobEffects.POISON)){
                amp = Math.min(mob.getEffect(MobEffects.POISON).getAmplifier() + 1,5);
                mob.removeEffect(MobEffects.POISON);
            }
            player.causeFoodExhaustion(0.2F);
            mob.addEffect(new MobEffectInstance(MobEffects.POISON,duration,amp,false, true));
            flux.removeFlux(2D);
        }
    }

    private void getEtaEffect(Player player) {

    }

    private void getEpsilonEffect(Player player, IPlayerAbility flux) {
        Level world = player.level;
        LivingEntity mob = world.getNearestEntity(LivingEntity.class, TargetingConditions.forCombat().range(8.0D),player,player.getX(),player.getY(),player.getZ(),player.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
        if (mob != null){
            mob.hurt(DamageSource.MAGIC,1.5f);
            player.heal(0.75f);
            player.getFoodData().setSaturation(player.getFoodData().getSaturationLevel()+0.25f);
            flux.removeFlux(2D);
        }
    }

    private void getDeltaEffect(Player player, int power, IPlayerAbility flux) {
        float effectsRemoved = 0;
        if (flux.getFlux() < 8){
            return;
        }
        for (MobEffectInstance effect: player.getActiveEffects()){
            if (!effect.getEffect().isBeneficial() && flux.getFlux() > 8){
                player.curePotionEffects(effect.getCurativeItems().get(0));
                effectsRemoved++;
                flux.removeFlux(8D);
                if ((power == 1 && effectsRemoved >= 2) || (power == 2 && effectsRemoved >= 4) ||
                        (power == 3 && effectsRemoved >= 6)){
                    break;
                }
            }
        }
        if (flux.getFlux() < power * 4){
            return;
        }
        if (player.hasEffect(MobEffects.REGENERATION)){
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION,20 * (power * 4),power,false,false));
            flux.removeFlux(power * 4D);
        }
    }

    private void getBetaEffect(Player player, float power, IPlayerAbility flux) {
        Level world = player.level;
        BlockHitResult lookingAt = getLookingAt(player, ClipContext.Fluid.ANY);
        if (world.isEmptyBlock(lookingAt.getBlockPos()) || flux.getFlux() < power){
            player.sendSystemMessage(Component.translatable("bedres.mage_staff.lightning.fail"));
            return;
        }
        LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(world);
        lightningbolt.moveTo(Vec3.atBottomCenterOf(lookingAt.getBlockPos()));
        lightningbolt.setVisualOnly(false);
        lightningbolt.setDamage(power);
        world.addFreshEntity(lightningbolt);
        flux.removeFlux((double) (power));
    }

    public void getAlphaEffect(Player player, float speed, Vec3 playerLook, IPlayerAbility flux){
        // don't allow free flight when using an elytra, should use fireworks
        if (player.isFallFlying() || flux.getFlux() < speed*2) {
            return;
        }
        player.causeFoodExhaustion(0.2F);
        player.setSprinting(true);
        player.push(
                (playerLook.x * speed),
                (1 + playerLook.y) * speed / 2f,
                (playerLook.z * speed));
        flux.removeFlux(speed*2D);
    }

    public static BlockHitResult getLookingAt(Player player, ClipContext.Fluid rayTraceFluid) {
        double rayTraceRange = LIGHTNINGRANGE;
        HitResult result = player.pick(rayTraceRange, 0f, rayTraceFluid != ClipContext.Fluid.NONE);

        return (BlockHitResult) result;
    }

    /** Determines how much force a charged right click item will release on player letting go */
    public float getForce(ItemStack stack, int timeCharged) {
        int i = this.getUseDuration(stack) - timeCharged;
        float f = i / 30.0F;
        f = (f * f + f * 2.0F) / 6.0F;
        f *= 6f;

        if (f > 16) {
            f = 16;
        }
        return f;
    }

    public float getLightningForce(ItemStack stack, float timeCharged) {
        float i = timeCharged/this.getUseDuration(stack);
        return i*6;
    }

    public int getTieredForce(ItemStack stack, int timeCharged) {
        int i = timeCharged/this.getUseDuration(stack);
        if (i < 0.25){
            return 1;
        }else if (i < 0.50){
            return 2;
        }else if (i < 0.75){
            return 3;
        }
        return 4;
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
                double yIncrement = 0.2;
                double radius = 1.5;
                ItemStack stack = player.getMainHandItem();
                double y = getParticleY(stack);
                double a = getParticleA(stack);
                String particlDirection = getParticleDirection(stack);

                double x = (cos(a) * radius);
                double z = sin(a) * radius;
                pLevel.addParticle(ParticleTypes.REVERSE_PORTAL,player.getX() + x,player.getY() + y,player.getZ() + z,0,0,0);
                a++;
                if(particlDirection.equals("up"))
                {
                    if(y >= 2)
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
                setParticleInfo(stack,y,a,particlDirection);
            }
        }
    }

    public int getColor(ItemStack stack){
        int color = 0x000000;
        if (stack.getItem() instanceof MageStaff mageStaff){
            switch (mageStaff.getType(stack)) {
                case "alpha" -> color = 0x693A67;
                case "beta" -> color = 0xA4A152;
                case "delta" -> color = 0x2A4043;
                case "epsilon" -> color = 0x6A2727;
                case "eta" -> color = 0x5B5B5B;
                case "gama" -> color = 0x5C7047;
                case "theta" -> color = 0x30552D;
                case "zeta" -> color = 0xAD4920;
            }
        }
        return color;

    }

    public void cycleRune(ItemStack stack ,Player player){
        if (player.getCooldowns().isOnCooldown(stack.getItem())){
            return;
        }
        String type = getType(stack);
        TYPES currentRune = TYPES.fromString(type.isEmpty()? "alpha" : type);
        if (currentRune.ordinal() == TYPES.values().length-1){
            type = TYPES.values()[0].name;
        }else{
            type = TYPES.values()[currentRune.ordinal()+1].name;
        }
        setType(stack,type);
        player.getCooldowns().addCooldown(stack.getItem(), 10);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, java.util.List<net.minecraft.network.chat.Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("Staff Mode: "));
        pTooltipComponents.add(Component.translatable("mage_staff.rune.type." + getType(pStack)).withStyle(ChatFormatting.DARK_PURPLE));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public boolean shouldDisplay(ItemStack offHand) {
        return true;
    }

    public String getType(ItemStack stack){
        return NBTHelper.getStirng(stack,"rune");
    }

    public void setType(ItemStack stack, String type){
        NBTHelper.setString(stack,"rune", type);
    }

    public int getCooldown(ItemStack stack){
        return NBTHelper.getInt(stack,"cooldown");
    }

    public void setCooldown(ItemStack stack, int type){
        NBTHelper.setInteger(stack,"cooldown", type);
    }
    public void tickCooldown(ItemStack stack){
        NBTHelper.addInteger(stack,"cooldown", -1);
    }

    public void setParticleInfo(ItemStack stack, double y, double a, String direction){
        setParticleY(stack,y);
        setParticleA(stack,a);
        setParticleDirection(stack,direction);
    }

    private double getParticleA(ItemStack stack) {
        return NBTHelper.getDouble(stack,"particle_a");
    }

    private double getParticleY(ItemStack stack) {
        return NBTHelper.getDouble(stack,"particle_y");
    }

    private void setParticleA(ItemStack stack, double amount) {
        NBTHelper.setDouble(stack,"particle_a",amount);
    }

    private void setParticleY(ItemStack stack, double amount) {
        NBTHelper.setDouble(stack,"particle_y",amount);
    }

    private String getParticleDirection(ItemStack stack) {
        return NBTHelper.getStirng(stack,"particle_dir");
    }
    private void setParticleDirection(ItemStack stack, String dir) {
        NBTHelper.setString(stack,"particle_dir", dir);
    }
    @Override
    public void verifyTagAfterLoad(CompoundTag pCompoundTag) {
        if (!pCompoundTag.contains("rune")){
            pCompoundTag.putString("rune","alpha");
            pCompoundTag.putDouble("particle_a",0);
            pCompoundTag.putDouble("particle_y",0);
            pCompoundTag.putInt("cooldown",0);
            pCompoundTag.putString("particle_dir","up");
        }
        super.verifyTagAfterLoad(pCompoundTag);
    }

    public enum TYPES{
        ALPHA("alpha"),BETA("beta"),DELTA("delta"),EPSILON("epsilon"),ETA("eta"),GAMA("gama"),THETA("theta"),ZETA("zeta");

        final String name;
        TYPES(String alpha) {
            name=alpha;
        }

        public String getName() {
            return name;
        }
        public static TYPES fromString(String text) {
            for (TYPES b : TYPES.values()) {
                if (b.name.equalsIgnoreCase(text)) {
                    return b;
                }
            }
            return null;
        }
    }
}
