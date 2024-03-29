package com.alexvr.bedres.utils;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.capability.abilities.IPlayerAbility;
import com.alexvr.bedres.capability.abilities.PlayerAbilityProvider;
import com.alexvr.bedres.client.screen.FluxOracleScreenGui;
import com.alexvr.bedres.entities.treckingcreeper.TreckingCreeperEntity;
import com.alexvr.bedres.items.MageStaff;
import com.alexvr.bedres.items.XPMedallion;
import com.alexvr.bedres.setup.Registration;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Map;

import static com.alexvr.bedres.items.Staff.REDUCED_GRAVITY;
import static com.alexvr.bedres.setup.ModConfig.*;

@Mod.EventBusSubscriber(modid = BedrockResources.MODID)
public class WorldEventHandler {
    public static FluxOracleScreenGui fxG = new FluxOracleScreenGui();
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

        ItemStack mainHandItem = player.getMainHandItem();

        if (!(mainHandItem.getItem() instanceof MageStaff) && !(mainHandItem.getItem() instanceof XPMedallion))
            return;
        KeyMapping toggle = KeyBindings.toggleMode;
        if (mainHandItem.getItem() instanceof XPMedallion){
            if (toggle.consumeClick() ) {
                ((XPMedallion)mainHandItem.getItem()).cycleMode(mainHandItem);
                return;
            }
        }else{
            if (toggle.consumeClick() ) {
                ((MageStaff)mainHandItem.getItem()).cycleRune(mainHandItem, player);
                return;
            }
        }


    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent event){
        if (event.getSource().isExplosion() &&
                event.getEntity() instanceof Player player &&
                event.getSource().getEntity() instanceof TreckingCreeperEntity treckingCreeperEntity &&
                treckingCreeperEntity.isTamed() &&
                treckingCreeperEntity.getOwnerUUID().equals(player.getUUID())){
            event.setCanceled(true);
        }
    }

//    @SubscribeEvent
//    public static void onBiomesLoad(BiomeLoadingEvent event) {
//        Biome.BiomeCategory biomecat = event.getCategory();
//        event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(Registration.TRECKING_CREEPER.get(), TRECKING_CREEPER_WEIGHT.get(), TRECKING_CREEPER_MIN_GROUP.get(), TRECKING_CREEPER_MAX_GROUP.get()));
//        if (biomecat == Biome.BiomeCategory.NETHER){
//            event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(Registration.BABY_GHAST.get(), BABY_GHAST_WEIGHT.get(), BABY_GHAST_MIN_GROUP.get(), BABY_GHAST_MAX_GROUP.get()));
//            event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(Registration.CHAINED_BLAZE.get(), CHAINED_BLAZE_WEIGHT.get(), CHAINED_BLAZE_MIN_GROUP.get(), CHAINED_BLAZE_MAX_GROUP.get()));
//        }
//        if (biomecat == Biome.BiomeCategory.THEEND){
//            event.getSpawns().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(Registration.FLUXED_CREEP.get(), FLUXED_CREEP_WEIGHT.get(), FLUXED_CREEP_MIN_GROUP.get(), FLUXED_CREEP_MAX_GROUP.get()));
//        }
//    }
// TODO json spawn


    @SubscribeEvent
    public static void onEntitySpawn(LivingSpawnEvent event) {
        if (event.getEntity() instanceof TreckingCreeperEntity treckingCreeperEntity && !treckingCreeperEntity.getTypeAssignedDir()){
            //treckingCreeperEntity.setType(Biome.getBiomeCategory(event.getWorld().getBiome(treckingCreeperEntity.getOnPos())));
        }//TODO Creeper
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
        event.getEntity().reviveCaps();
        LazyOptional<IPlayerAbility> abilities = event.getEntity().getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        abilities.ifPresent(h -> {
            if (h.getJumpBoost()>0) {
                event.getEntity().setDeltaMovement(event.getEntity().getDeltaMovement().add(0, h.getJumpBoost(), 0));
            }
        });
        event.getEntity().invalidateCaps();
    }
    @SubscribeEvent
    public static void PlayerBreakBlockEvent(PlayerInteractEvent.HarvestCheck event) {
        Player player = event.getEntity();
        player.reviveCaps();
        if (!(player.getMainHandItem().getItem() instanceof TieredItem) &&
                (event.getTargetBlock().getTags().anyMatch( tag -> tag.equals(BlockTags.MINEABLE_WITH_PICKAXE) || tag.equals(BlockTags.MINEABLE_WITH_AXE) ||
                        tag.equals(BlockTags.MINEABLE_WITH_HOE) || tag.equals(BlockTags.MINEABLE_WITH_SHOVEL) ))) {
            LazyOptional<IPlayerAbility> abilities = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
            abilities.ifPresent(h -> {
                Block block = event.getTargetBlock().getBlock();

                String blockTier = "no";
                boolean checkStone = block.defaultBlockState().getTags().anyMatch(tag -> tag.equals(BlockTags.NEEDS_STONE_TOOL));
                boolean checkIron = block.defaultBlockState().getTags().anyMatch(tag -> tag.equals(BlockTags.NEEDS_IRON_TOOL));
                boolean checkDiamond = block.defaultBlockState().getTags().anyMatch(tag -> tag.equals(BlockTags.NEEDS_DIAMOND_TOOL));
                if (checkStone){
                    blockTier = "stone";
                }else if (checkIron){
                    blockTier = "iron";
                }else if (checkDiamond){
                    blockTier = "diamond";
                }
                boolean harvestEvent = false;
                if (block.defaultBlockState().getTags().anyMatch(tag -> tag.equals(BlockTags.MINEABLE_WITH_PICKAXE)) &&  (!h.getPick().equals("no"))) {
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
                }else if (block.defaultBlockState().getTags().anyMatch(tag -> tag.equals(BlockTags.MINEABLE_WITH_SHOVEL)) &&  (!h.getShovel().equals("no"))) {
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
                }else if (block.defaultBlockState().getTags().anyMatch(tag -> tag.equals(BlockTags.MINEABLE_WITH_AXE)) &&  (!h.getAxe().equals("no"))) {
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
                }else if (block.defaultBlockState().getTags().anyMatch(tag -> tag.equals(BlockTags.MINEABLE_WITH_HOE)) &&  (!h.getHoe().equals("no"))) {
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
        LazyOptional<IPlayerAbility> abilities = event.getEntity().getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        abilities.ifPresent(h -> {
            BlockHitResult hitting = GeneralHelper.selectBlock(event.getEntity());
            if(!h.getHoe().equals("no") && !event.getEntity().isShiftKeyDown()){

            }else if(!h.getShovel().equals("no") && event.getEntity().isShiftKeyDown()){

            }
        });
//        net.minecraftforge.event.ForgeEventFactory.onHoeUse(new UseOnContext(event.getEntity()(),event.getHand(),GeneralHelper.selectBlock(event.getEntity()())));
    }


    @SubscribeEvent
    public static void PlayerBreakSpeedEvent(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        player.reviveCaps();
        if (!player.isShiftKeyDown()) {
            LazyOptional<IPlayerAbility> abilities = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
            abilities.ifPresent(h -> {
                float speed = 0;
                boolean toolRequired =  false;
                if (!(player.getMainHandItem().getItem() instanceof TieredItem)){
                    if (event.getState().getTags().anyMatch(tag -> tag.equals(BlockTags.NEEDS_STONE_TOOL) || tag.equals(BlockTags.NEEDS_IRON_TOOL)
                            || tag.equals(BlockTags.NEEDS_DIAMOND_TOOL))){
                        toolRequired = true;
                        if (event.getState().getTags().anyMatch(tag -> tag.equals((BlockTags.MINEABLE_WITH_PICKAXE))) && !h.getPick().equals("no")){
                            speed+=getSpeed(h.getPick());
                        }else if (event.getState().getTags().anyMatch(tag -> tag.equals((BlockTags.MINEABLE_WITH_AXE))) && !h.getAxe().equals("no")){
                            speed+=getSpeed(h.getAxe());
                        }else if (event.getState().getTags().anyMatch(tag -> tag.equals((BlockTags.MINEABLE_WITH_SHOVEL))) && !h.getShovel().equals("no")){
                            speed+=getSpeed(h.getShovel());
                        }else if (event.getState().getTags().anyMatch(tag -> tag.equals((BlockTags.MINEABLE_WITH_HOE))) && !h.getHoe().equals("no")){
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
        Player player = event.getOriginal();
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
