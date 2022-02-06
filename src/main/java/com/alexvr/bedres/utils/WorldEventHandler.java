package com.alexvr.bedres.utils;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.capability.abilities.IPlayerAbility;
import com.alexvr.bedres.capability.abilities.PlayerAbilityProvider;
import com.alexvr.bedres.items.MageStaff;
import com.alexvr.bedres.setup.Registration;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Map;

import static com.alexvr.bedres.items.Staff.REDUCED_GRAVITY;

@Mod.EventBusSubscriber(modid = BedrockResources.MODID)
public class WorldEventHandler {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (Minecraft.getInstance().player != null){
            Minecraft.getInstance().player.reviveCaps();
            LazyOptional<IPlayerAbility> playerFlux = Minecraft.getInstance().player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
            playerFlux.ifPresent(k -> {
                if (k.givenGravity() && !player.getInventory().contains(new ItemStack(Registration.STAFF_ITEM.get()))){
                    AttributeInstance grav = player.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
                    k.setGivenGravity(false);
                    if (grav.hasModifier(REDUCED_GRAVITY))
                    {
                        grav.removeModifier(REDUCED_GRAVITY);

                    }
                }
            });
            Minecraft.getInstance().player.invalidateCaps();
        }

        if (!player.level.isClientSide()) {
            return;
        }

        ItemStack wand = player.getMainHandItem();

        if (!(wand.getItem() instanceof MageStaff))
            return;
        KeyMapping toggleWand = KeyBindings.toggleWang;
        if (toggleWand.consumeClick() ) {
            ((MageStaff)wand.getItem()).cycleRune(wand, player);
            return;
        }

    }
    @SubscribeEvent
    public static void onWakeUp(PlayerWakeUpEvent event){
        event.getPlayer().reviveCaps();
        LazyOptional<IPlayerAbility> playerflux = event.getPlayer().getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        playerflux.ifPresent(h -> h.setFlux(h.getFlux() + (h.getMaxFlux()/2)));
        event.getPlayer().invalidateCaps();
    }
    @SubscribeEvent
    public static void onDamage(LivingDamageEvent event){

    }

    @SubscribeEvent
    public static void onPlayerLogIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();
        player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null).ifPresent(h -> {
            player.sendMessage(new TextComponent("Sword Level: " + h.getSword()),player.getUUID());
            player.sendMessage(new TextComponent("Pick Level: " + h.getPick()),player.getUUID());
            player.sendMessage(new TextComponent("Hoe Level: " + h.getHoe()),player.getUUID());
            player.sendMessage(new TextComponent("Shovel Level: " + h.getPick()),player.getUUID());
            player.sendMessage(new TextComponent("Axe Level: " + h.getPick()),player.getUUID());
            player.sendMessage(new TextComponent("Mining Speed Level: " + h.getMiningSpeedBoost()),player.getUUID());
            player.sendMessage(new TextComponent("Jump Level: " + h.getJumpBoost()),player.getUUID());
            player.sendMessage(new TextComponent("Fall damage Status: " + h.takesFalldamage()),player.getUUID());
            player.sendMessage(new TextComponent("Flux Level: " + h.getFlux() + "/" + h.getMaxFlux()),player.getUUID());

        });

    }

    @SubscribeEvent
    public static void PlayerAttackEvent( LivingHurtEvent event) {
        if (event.getSource().getDirectEntity() instanceof Player diPlayer) {
            diPlayer.reviveCaps();
            LazyOptional<IPlayerAbility> abilities = diPlayer.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
            abilities.ifPresent(h -> {
                if (!h.getSword().equals("no")) {
                    if (h.getFlux() > 0.5){
                        h.removeFlux(.5);
                        event.setAmount(event.getAmount() + getAttackBoost(h.getSword()));
                    }
                }
            });
            diPlayer.invalidateCaps();
        }
    }

    public static float getAttackBoost(String material){
        switch (material) {
            case "wood":
                return ((SwordItem) Items.WOODEN_SWORD).getDamage();
            case "iron":
                return ((SwordItem)Items.IRON_SWORD).getDamage();
            case "diamond":
                return  ((SwordItem)Items.DIAMOND_SWORD).getDamage();
        }
        return 0;
    }

    @SubscribeEvent
    public static void PlayerFallEvent( LivingFallEvent event) {
       if (event.getEntity() instanceof Player player){
           player.reviveCaps();
           LazyOptional<IPlayerAbility> playerAbility = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
           playerAbility.ifPresent(h -> {
               if (!h.takesFalldamage()){
                   if (h.getFlux() > 0.5){
                       h.removeFlux(.5);
                       event.setDistance(0);
                       event.setDamageMultiplier(0);
                       event.setCanceled(true);
                   }
               }
           });
           player.invalidateCaps();
       }
    }

    @SubscribeEvent
    public static void PlayerJumpEvent(LivingEvent.LivingJumpEvent event) {
        event.getEntityLiving().reviveCaps();
        LazyOptional<IPlayerAbility> abilities = event.getEntityLiving().getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        abilities.ifPresent(h -> {
            if (h.getJumpBoost()>0) {
                event.getEntityLiving().setDeltaMovement(event.getEntityLiving().getDeltaMovement().add(0, h.getJumpBoost(), 0));
            }
        });
        event.getEntityLiving().invalidateCaps();
    }
    @SubscribeEvent
    public static void PlayerBreakBlockEvent(PlayerInteractEvent.HarvestCheck event) {
        Player player = event.getPlayer();
        player.reviveCaps();
        if (!(player.getMainHandItem().getItem() instanceof TieredItem) &&
                (event.getTargetBlock().getBlock().getTags().contains(BlockTags.MINEABLE_WITH_PICKAXE.getName()) || event.getTargetBlock().getBlock().getTags().contains(BlockTags.MINEABLE_WITH_AXE.getName()) ||
            event.getTargetBlock().getBlock().getTags().contains(BlockTags.MINEABLE_WITH_HOE.getName()) || event.getTargetBlock().getBlock().getTags().contains(BlockTags.MINEABLE_WITH_SHOVEL.getName()) )) {
            LazyOptional<IPlayerAbility> abilities = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
            abilities.ifPresent(h -> {
                Block block = event.getTargetBlock().getBlock();

                String blockTier = "no";
                boolean checkStone = block.getTags().contains(BlockTags.NEEDS_STONE_TOOL.getName());
                boolean checkIron = block.getTags().contains(BlockTags.NEEDS_IRON_TOOL.getName());
                boolean checkDiamond = block.getTags().contains(BlockTags.NEEDS_DIAMOND_TOOL.getName());
                if (checkStone){
                    blockTier = "stone";
                }else if (checkIron){
                    blockTier = "iron";
                }else if (checkDiamond){
                    blockTier = "diamond";
                }
                boolean harvestEvent = false;
                if (block.getTags().contains(BlockTags.MINEABLE_WITH_PICKAXE.getName()) &&  (!h.getPick().equals("no"))) {
                    switch (blockTier){
                        case "stone":
                            if (blockTier.equals(h.getPick())|| h.getPick().equals("iron")|| h.getPick().equals("diamond")){
                                harvestEvent = true;
                            }
                            break;
                        case "iron":
                            if (blockTier.equals(h.getPick()) || h.getPick().equals("diamond")){
                                harvestEvent = true;
                            }
                            break;
                        case "diamond":
                            if (blockTier.equals(h.getPick())){
                                harvestEvent = true;
                            }
                            break;
                        default:
                            harvestEvent = true;
                    }
                }else if (block.getTags().contains(BlockTags.MINEABLE_WITH_SHOVEL.getName()) &&  (!h.getShovel().equals("no"))) {
                    switch (blockTier){
                        case "stone":
                            if (blockTier.equals(h.getShovel())|| h.getShovel().equals("iron")|| h.getShovel().equals("diamond")){
                                harvestEvent = true;
                            }
                            break;
                        case "iron":
                            if (blockTier.equals(h.getShovel()) || h.getShovel().equals("diamond")){
                                harvestEvent = true;
                            }
                            break;
                        case "diamond":
                            if (blockTier.equals(h.getShovel())){
                                harvestEvent = true;
                            }
                            break;
                        default:
                            harvestEvent = true;
                    }
                }else if (block.getTags().contains(BlockTags.MINEABLE_WITH_AXE.getName()) &&  (!h.getAxe().equals("no"))) {
                    switch (blockTier){
                        case "stone":
                            if (blockTier.equals(h.getAxe())|| h.getAxe().equals("iron")|| h.getAxe().equals("diamond")){
                                harvestEvent = true;
                            }
                            break;
                        case "iron":
                            if (blockTier.equals(h.getAxe()) || h.getAxe().equals("diamond")){
                                harvestEvent = true;
                            }
                            break;
                        case "diamond":
                            if (blockTier.equals(h.getAxe())){
                                harvestEvent = true;
                            }
                            break;
                        default:
                            harvestEvent = true;
                    }
                }else if (block.getTags().contains(BlockTags.MINEABLE_WITH_HOE.getName()) &&  (!h.getHoe().equals("no"))) {
                    switch (blockTier){
                        case "stone":
                            if (blockTier.equals(h.getHoe())|| h.getHoe().equals("iron")|| h.getHoe().equals("diamond")){
                                harvestEvent = true;
                            }
                            break;
                        case "iron":
                            if (blockTier.equals(h.getHoe()) || h.getHoe().equals("diamond")){
                                harvestEvent = true;
                            }
                            break;
                        case "diamond":
                            if (blockTier.equals(h.getHoe())){
                                harvestEvent = true;
                            }
                            break;
                        default:
                            harvestEvent = true;
                    }
                }
                if (harvestEvent){

                    if (h.getFlux() > 0.5){
                        h.removeFlux(.5);
                        event.setCanHarvest(true);
                    }

                }
            });
        }
        player.invalidateCaps();
    }


    private static final Map<Block, BlockState> HOE_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.FARMLAND.defaultBlockState(),
            Blocks.DIRT_PATH, Blocks.FARMLAND.defaultBlockState(), Blocks.DIRT, Blocks.FARMLAND.defaultBlockState(), Blocks.COARSE_DIRT, Blocks.DIRT.defaultBlockState()));

    protected static final Map<Block, BlockState> SHOVEL_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.GRASS_BLOCK, Blocks.DIRT_PATH.defaultBlockState()));

    @SubscribeEvent
    public static void PlayerRightClickEvent( PlayerInteractEvent.RightClickBlock event) {
        LazyOptional<IPlayerAbility> abilities = event.getPlayer().getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        abilities.ifPresent(h -> {
            BlockHitResult hitting = GeneralHelper.selectBlock(event.getPlayer());
            if(!h.getHoe().equals("no") && !event.getPlayer().isShiftKeyDown()){

            }else if(!h.getShovel().equals("no") && event.getPlayer().isShiftKeyDown()){

            }
        });
//        net.minecraftforge.event.ForgeEventFactory.onHoeUse(new UseOnContext(event.getPlayer(),event.getHand(),GeneralHelper.selectBlock(event.getPlayer())));
    }


    @SubscribeEvent
    public static void PlayerBreakSpeedEvent(PlayerEvent.BreakSpeed event) {
        Player player = event.getPlayer();
        player.reviveCaps();
        if (!player.isShiftKeyDown()) {
            LazyOptional<IPlayerAbility> abilities = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
            abilities.ifPresent(h -> {
                float speed = 0;
                boolean toolRequired =  false;
                if (!(player.getMainHandItem().getItem() instanceof TieredItem)){
                    if (event.getState().getBlock().getTags().contains(BlockTags.NEEDS_STONE_TOOL.getName())
                            || event.getState().getBlock().getTags().contains(BlockTags.NEEDS_IRON_TOOL.getName())
                            || event.getState().getBlock().getTags().contains(BlockTags.NEEDS_DIAMOND_TOOL.getName())){
                        toolRequired = true;
                        if (event.getState().getBlock().getTags().contains(BlockTags.MINEABLE_WITH_PICKAXE.getName()) && !h.getPick().equals("no")){
                            speed+=getSpeed(h.getPick());
                        }else if (event.getState().getBlock().getTags().contains(BlockTags.MINEABLE_WITH_AXE.getName()) && !h.getAxe().equals("no")){
                            speed+=getSpeed(h.getAxe());
                        }else if (event.getState().getBlock().getTags().contains(BlockTags.MINEABLE_WITH_SHOVEL.getName()) && !h.getShovel().equals("no")){
                            speed+=getSpeed(h.getShovel());
                        }else if (event.getState().getBlock().getTags().contains(BlockTags.MINEABLE_WITH_HOE.getName()) && !h.getHoe().equals("no")){
                            speed+=getSpeed(h.getHoe());
                        }
                    }else{
                        if (event.getState().getMaterial().toString().equals(Material.WOOD.toString()) && !h.getAxe().equals("no")) {
                            speed+=getSpeed(h.getAxe());
                        } else if (  (!h.getPick().equals("no"))&&(event.getState().getMaterial() == Material.STONE || event.getState().getMaterial() == Material.METAL || event.getState().getMaterial() == Material.HEAVY_METAL)) {
                            speed += getSpeed(h.getPick());
                        } else if ((!h.getShovel().equals("no"))&&(event.getState().getMaterial() == Material.DIRT || event.getState().getMaterial() == Material.SAND || event.getState().getMaterial() == Material.SNOW || event.getState().getMaterial() == Material.CLAY || event.getState().getMaterial() == Material.PLANT)) {
                            speed += getSpeed(h.getShovel());
                        } else if ((!h.getHoe().equals("no"))&&(event.getState().getMaterial() == Material.WEB || event.getState().getMaterial() == Material.LEAVES || event.getState().getMaterial() == Material.WOOL)) {
                            speed += getSpeed(h.getHoe());
                        }
                    }
                    if (speed == 0 && h.getMiningSpeedBoost() == 0){
                        return;
                    }
                    speed += 1;
                    speed *= 1.55;
                    if (event.getState().getBlock() == Blocks.STONE) {
                        speed += 5.5;
                    }
                    float finalSpeed = speed;
                    if ((!toolRequired || event.getState().getBlock() == Blocks.STONE) && speed > 0) {
                        if (h.getFlux() > 0.5){
                            h.removeFlux(.5);
                            event.setNewSpeed((float) (event.getOriginalSpeed() + h.getMiningSpeedBoost() + finalSpeed));
                        }
                    } else if (speed > 0) {
                        if (h.getFlux() > 0.5){
                            h.removeFlux(.5);
                            event.setNewSpeed((float) ((event.getOriginalSpeed() + h.getMiningSpeedBoost() + finalSpeed) * (100.0 / 30.0)));
                        }

                    }
                } else if (h.getMiningSpeedBoost() > 0) {
                    if (h.getFlux() > 0.5){
                        h.removeFlux(.5);
                        event.setNewSpeed((float) (event.getOriginalSpeed() + h.getMiningSpeedBoost()));
                    }
                }
            });

        }
        player.invalidateCaps();
    }

    private static float getSpeed(String material) {
        return switch (material) {
            case "stone" -> Tiers.STONE.getSpeed();
            case "iron" -> Tiers.IRON.getSpeed();
            case "golden" -> Tiers.GOLD.getSpeed();
            case "diamond" -> Tiers.DIAMOND.getSpeed();
            default -> 0;
        };
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player player = event.getPlayer();
        if (event.isWasDeath()){
            event.getOriginal().reviveCaps();
            player.reviveCaps();
            LazyOptional<IPlayerAbility> playerAbility = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
            LazyOptional<IPlayerAbility> oldplayerAbility =  event.getOriginal().getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
            playerAbility.ifPresent(h -> oldplayerAbility.ifPresent(o -> {
                h.setJumpBoost(o.getJumpBoost());
                h.setTakesFallDamage(o.takesFalldamage());
                h.setMiningSpeedBoost(o.getMiningSpeedBoost());
                h.setHoe(o.getHoe());
                h.setSword(o.getSword());
                h.setShovel(o.getShovel());
                h.setAxe(o.getAxe());
                h.setPick(o.getPick());
                h.setRitualTimer(0);
                h.setRitualTotalTimer(0);
                h.setRitualPedestals(new ArrayList<>());
                h.setFOV(o.getFOV());
                h.setLookPos(o.getlookPos());
                h.setname(o.getNAme());
                h.setFluxCooldown(o.getFluxCooldown());
                h.setFlux(o.getFlux());
                h.setMaxFlux(o.getMaxFlux());
            }));
            event.getOriginal().invalidateCaps();
            player.invalidateCaps();
        }
    }

}
