package com.alexvr.bedres.items;

import com.alexvr.bedres.entities.LightProjectileEntity;
import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.utils.NBTHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
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
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class MageStaff extends Item {

    public MageStaff(Properties pProperties) {
        super(pProperties);
    }
    private static final double LIGHTNINGRANGE = 16;
    private static final int LIFESTEALCOUNTERMAX = 10;
    private static final int POISONCOUNTERMAX = 60;
    private static final int BONEMEALCOUNTERMAX = 20;
    private static final int GREENTICKCOUNTERMAX = 5;
    String particlDirection = "up";
    int height = 2;
    double yIncrement = 0.2;
    double radius = 1.5;
    double a = 0;
    double x = 0;
    double y = 0;
    double z = 0;
    public String type = "alpha";
    public int lifeStealCounter = 0;
    public int poisonCounter = 0;
    public int greenTickCounter = 0;

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if (type.equals("zeta") && !pLevel.isClientSide()){
            getZetaEffect(pPlayer,pLevel);
            return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
        }
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (!(pLivingEntity instanceof Player)) {
            return;
        }
        Player player = (Player) pLivingEntity;

        if (type.equals("alpha")){
            getAlphaEffect(player,getForce(pStack, pTimeCharged) / 3F,player.getLookAngle());
        }else if (type.equals("beta")){
            getBetaEffect(player,getLightningForce(pStack,pTimeCharged));
        }else if (type.equals("delta")){
            getDeltaEffect(player, getTieredForce(pStack,pTimeCharged));
        }

        if (pLevel.isClientSide()) {
            player.getCooldowns().addCooldown(pStack.getItem(), 10);
        }
        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        if (pLivingEntity instanceof Player player){
            if (player.getCooldowns().isOnCooldown(pStack.getItem()) || !player.isUsingItem()){
                return;
            }
            if (type.equals("epsilon")){
                if (lifeStealCounter <= 0){
                    getEpsilonEffect(player);
                    lifeStealCounter = LIFESTEALCOUNTERMAX;
                }else{
                    lifeStealCounter --;
                }
            }

            if (type.equals("gama")){
                if (poisonCounter <= 0){
                    getGamaEffect(player);
                    poisonCounter = POISONCOUNTERMAX;
                }else{
                    poisonCounter --;
                }
            }
            if (pLevel instanceof ServerLevel serverLevel){
                if (type.equals("theta")){
                    if (greenTickCounter <= 0){
                        getThetaGreenTickEffect(player, serverLevel, player.isShiftKeyDown());
                        if (player.isShiftKeyDown()){
                            greenTickCounter = BONEMEALCOUNTERMAX;
                        }else{
                            greenTickCounter = GREENTICKCOUNTERMAX;
                        }
                    }else{
                        greenTickCounter --;
                    }
                }
            }
        }
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    }

    private void getZetaEffect(Player player, Level level) {

        var projectile = new LightProjectileEntity(Registration.LIGHT_PROJ_ENTITY.get(), player, level);
        projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1.5F, 0);
        level.addFreshEntity(projectile);

    }

    private void getThetaGreenTickEffect(Player pPlayer, ServerLevel level, boolean isShiftDown) {
        BlockHitResult lookingAt = getLookingAt(pPlayer, ClipContext.Fluid.NONE);
        if (isShiftDown){
            if (pPlayer.level.getBlockState(lookingAt.getBlockPos()).getBlock() instanceof BonemealableBlock bonemealableBlock){
                bonemealableBlock.performBonemeal(level, new Random(),lookingAt.getBlockPos(),pPlayer.level.getBlockState(lookingAt.getBlockPos()));
                pPlayer.getCooldowns().addCooldown(pPlayer.getUseItem().getItem(), 10);
            }else{
                pPlayer.sendMessage(new TranslatableComponent("bedres.mage_staff.green_thumb.fail"),pPlayer.getUUID());
            }
        }else{
            BlockPos sourcePos = lookingAt.getBlockPos();
            for (int x = - 4; x<= 4;x++){
                for (int z = - 4; z<=4;z++){
                    BlockPos newPos = sourcePos.offset(x,0,z);
                    if (level.getBlockState(newPos).getBlock() instanceof IPlantable plantable){
                        plantable.getPlant(level,newPos).randomTick(level,newPos, new Random());
                    }
                }
            }
        }
    }

    private void getGamaEffect(Player player) {
        Level world = player.level;
        LivingEntity mob = world.getNearestEntity(LivingEntity.class, TargetingConditions.forCombat().range(8.0D),player,player.getX(),player.getY(),player.getZ(),player.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
        if (mob != null){
            int duration = 100;
            int amp = 0;
            if (mob.hasEffect(MobEffects.POISON)){
                amp = Math.min(mob.getEffect(MobEffects.POISON).getAmplifier() + 1,5);
                mob.removeEffect(MobEffects.POISON);
            }
            mob.addEffect(new MobEffectInstance(MobEffects.POISON,duration,amp,false, true));
        }
    }

    private void getEtaEffect(Player player) {

    }

    private void getEpsilonEffect(Player player) {
        Level world = player.level;
        LivingEntity mob = world.getNearestEntity(LivingEntity.class, TargetingConditions.forCombat().range(8.0D),player,player.getX(),player.getY(),player.getZ(),player.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
        if (mob != null){
            mob.hurt(DamageSource.MAGIC,1.5f);
            player.heal(0.75f);
            player.getFoodData().setSaturation(player.getFoodData().getSaturationLevel()+0.25f);
        }
    }

    private void getDeltaEffect(Player player, int power) {
        float effectsRemoved = 0;
        for (MobEffectInstance effect: player.getActiveEffects()){
            if (!effect.getEffect().isBeneficial()){
                player.curePotionEffects(effect.getCurativeItems().get(0));
                effectsRemoved++;
                if ((power == 1 && effectsRemoved >= 2) || (power == 2 && effectsRemoved >= 4) ||
                        (power == 3 && effectsRemoved >= 6)){
                    break;
                }
            }
        }
        if (player.hasEffect(MobEffects.REGENERATION)){
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION,20 * (power * 4),power,false,false));
        }
    }

    private void getBetaEffect(Player player, float power) {
        Level world = player.level;
        BlockHitResult lookingAt = getLookingAt(player, ClipContext.Fluid.ANY);
        if (world.isEmptyBlock(lookingAt.getBlockPos())){
            player.sendMessage(new TranslatableComponent("bedres.mage_staff.lightning.fail"),player.getUUID());
            return;
        }
        LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(world);
        lightningbolt.moveTo(Vec3.atBottomCenterOf(lookingAt.getBlockPos()));
        lightningbolt.setVisualOnly(false);
        lightningbolt.setDamage(power);
        world.addFreshEntity(lightningbolt);
    }

    public void getAlphaEffect(Player player, float speed, Vec3 playerLook){
        // don't allow free flight when using an elytra, should use fireworks
        if (player.isFallFlying()) {
            return;
        }
        player.causeFoodExhaustion(0.2F);
        player.setSprinting(true);
        player.push(
                (playerLook.x * speed),
                (1 + playerLook.y) * speed / 2f,
                (playerLook.z * speed));
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

    public int getColor(ItemStack stack){
        int color = 0x000000;
        if (stack.getItem() instanceof MageStaff mageStaff){
            switch (mageStaff.type) {
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
        TYPES currentRune = TYPES.fromString(type);
        if (currentRune.ordinal() == TYPES.values().length-1){
            type = TYPES.values()[0].name;
        }else{
            type = TYPES.values()[currentRune.ordinal()+1].name;
        }

        NBTHelper.setString(stack,"rune",type);
        player.getCooldowns().addCooldown(stack.getItem(), 10);
    }

    @Override
    public void verifyTagAfterLoad(CompoundTag pCompoundTag) {
        if (pCompoundTag.contains("rune")){
            type = pCompoundTag.getString("rune");
        }
        super.verifyTagAfterLoad(pCompoundTag);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, java.util.List<net.minecraft.network.chat.Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TextComponent("Staff Mode: "));
        pTooltipComponents.add(new TranslatableComponent("mage_staff.rune.type." + type).withStyle(ChatFormatting.DARK_PURPLE));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
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
