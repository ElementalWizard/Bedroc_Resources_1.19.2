package com.alexvr.bedres.setup;

import com.alexvr.bedres.blocks.LightBlock;
import com.alexvr.bedres.blocks.*;
import com.alexvr.bedres.blocks.bedrockiumPedestal.BedrociumPedestal;
import com.alexvr.bedres.blocks.bedrockiumPedestal.BedrociumPedestalTile;
import com.alexvr.bedres.blocks.bedrockiumTower.BedrociumTower;
import com.alexvr.bedres.blocks.bedrockiumTower.BedrockiumTowerTile;
import com.alexvr.bedres.blocks.decayingfluxedblocks.*;
import com.alexvr.bedres.blocks.enderianRitualPedestal.EnderianRitualPedestal;
import com.alexvr.bedres.blocks.enderianRitualPedestal.EnderianRitualPedestalTile;
import com.alexvr.bedres.blocks.eventAltar.EventAltar;
import com.alexvr.bedres.blocks.eventAltar.EventAltarTile;
import com.alexvr.bedres.blocks.gravityBubble.FluxedGravityBubble;
import com.alexvr.bedres.blocks.gravityBubble.FluxedGravityBubbleTile;
import com.alexvr.bedres.blocks.itemPlatform.ItemPlatform;
import com.alexvr.bedres.blocks.itemPlatform.ItemPlatformTile;
import com.alexvr.bedres.client.particles.lightParticle.LightParticleType;
import com.alexvr.bedres.entities.LightProjectileEntity;
import com.alexvr.bedres.entities.chainedblaze.ChainedBlazeEntity;
import com.alexvr.bedres.entities.fluxedcreep.FluxedCreepEntity;
import com.alexvr.bedres.entities.sporedeity.SporeDeityEntity;
import com.alexvr.bedres.items.*;
import com.alexvr.bedres.utils.BedrockReferences;
import com.alexvr.bedres.worldgen.world.altar.AltarStructure;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.alexvr.bedres.BedrockResources.MODID;

public class Registration {

    private static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MODID);
    private static final DeferredRegister<StructureType<?>> STRUCTURES = DeferredRegister.create(Registries.STRUCTURE_TYPE, MODID);
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);

    public static  void init(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
        CONTAINERS.register(bus);
        STRUCTURES.register(bus);
        ENTITIES.register(bus);
        PARTICLES.register(bus);
        SOUND_EVENTS.register(bus);
        BIOMES.register(bus);
    }

//    public static Biome FLUXED_BIOME = BIOMES.register("fluxed_biome", DecayingFluxedBiome.createBiome());


    public static final RegistryObject<SoundEvent> FLUXED_CREEP_IDLE = SOUND_EVENTS.register("fluxed_creep_idle",
            () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(MODID, "fluxed_creep_idle"),16));
    public static final RegistryObject<SoundEvent> FLUXED_CREEP_ROAR = SOUND_EVENTS.register("fluxed_creep_roar",
            () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(MODID, "fluxed_creep_roar"),16));
    public static final SoundType FLUXED_CREEP_TYPE = new ForgeSoundType(1.0F, 1.0F, FLUXED_CREEP_IDLE, FLUXED_CREEP_IDLE, FLUXED_CREEP_IDLE, FLUXED_CREEP_ROAR, FLUXED_CREEP_ROAR);

    public static final RegistryObject<LightParticleType> LIGHT_PARTICLE_TYPE = PARTICLES.register(BedrockReferences.LIGHT_PARTICLE_REGNAME, LightParticleType::new);

    public static final RegistryObject<EntityType<LightProjectileEntity>> LIGHT_PROJ_ENTITY = ENTITIES.register(BedrockReferences.LIGHT_PROJ_REGNAME, () -> EntityType.Builder.<LightProjectileEntity>of(LightProjectileEntity::new, MobCategory.MISC)
            .sized(1f,1f)
            .setTrackingRange(64).setUpdateInterval(3).fireImmune()
            .setShouldReceiveVelocityUpdates(true)
            .build(BedrockReferences.LIGHT_PROJ_REGNAME));

    public static final RegistryObject<EntityType<SporeDeityEntity>> SPORE_DEITY = ENTITIES.register(BedrockReferences.SPORE_DEITY_REGNAME, () -> EntityType.Builder.of(SporeDeityEntity::new, MobCategory.MONSTER)
            .sized(0.8f, 1.95f)
            .clientTrackingRange(8)
            .setShouldReceiveVelocityUpdates(false)
            .build(BedrockReferences.SPORE_DEITY_REGNAME));
    public static final RegistryObject<Item> SPORE_DEITY_EGG_ITEM = ITEMS.register(BedrockReferences.SPORE_DEITY_REGNAME, () -> new ForgeSpawnEggItem(SPORE_DEITY, 0x000000, 0xffffff, new Item.Properties()));

    public static final RegistryObject<EntityType<FluxedCreepEntity>> FLUXED_CREEP = ENTITIES.register(BedrockReferences.FLUXED_CREEP_REGNAME, () -> EntityType.Builder.of(FluxedCreepEntity::new, MobCategory.MONSTER)
            .sized(0.8f, 1f)
            .clientTrackingRange(8)
            .setShouldReceiveVelocityUpdates(false)
            .build(BedrockReferences.FLUXED_CREEP_REGNAME));
    public static final RegistryObject<Item> FLUXED_CREEP_EGG_ITEM = ITEMS.register(BedrockReferences.FLUXED_CREEP_REGNAME, () -> new ForgeSpawnEggItem(FLUXED_CREEP, 0x8e7cc3, 0x000000, new Item.Properties()));

    public static final RegistryObject<EntityType<ChainedBlazeEntity>> CHAINED_BLAZE = ENTITIES.register(BedrockReferences.CHAINED_BLAZE_REGNAME, () -> EntityType.Builder.of(ChainedBlazeEntity::new, MobCategory.MONSTER)
            .sized(0.7f, 1.5f)
            .clientTrackingRange(8)
            .setShouldReceiveVelocityUpdates(false)
            .build(BedrockReferences.CHAINED_BLAZE_REGNAME));
    public static final RegistryObject<Item> CHAINED_BLAZE_EGG_ITEM = ITEMS.register(BedrockReferences.CHAINED_BLAZE_REGNAME, () -> new ForgeSpawnEggItem(CHAINED_BLAZE, 0x351c75, 0x5b5b5b, new Item.Properties()));



    private static final BlockBehaviour.Properties ORE_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(2f);
    private static final BlockBehaviour.Properties BLOCK_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(2f);
    private static final BlockBehaviour.Properties METAL_BLOCK_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3,800f).sound(SoundType.METAL).requiresCorrectToolForDrops();
    private static final BlockBehaviour.Properties PLANT_BLOCK_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).dynamicShape().noCollission().sound(SoundType.GRASS).lightLevel(value -> 3).instabreak();
    private static final BlockBehaviour.Properties DIRT_BLOCK_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(1f).sound(SoundType.GRASS);
    private static final BlockBehaviour.Properties WOOD_BLOCK_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).strength(2f).sound(SoundType.WOOD);
    private static final BlockBehaviour.Properties LEAVES_BLOCK_PROPERTIES = BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).noOcclusion().sound(SoundType.GRASS);

    public static final RegistryObject<Block> ENDERIAN_ORE_OVERWORLD = BLOCKS.register(BedrockReferences.ENDERIAN_ORE_REGNAME + "_overworld", () -> new DropExperienceBlock(ORE_PROPERTIES));
    public static final RegistryObject<Item> ENDERIAN_ORE_OVERWORLD_ITEM = fromBlock(ENDERIAN_ORE_OVERWORLD);
    public static final RegistryObject<Block> ENDERIAN_ORE_NETHER = BLOCKS.register(BedrockReferences.ENDERIAN_ORE_REGNAME + "_nether", () -> new DropExperienceBlock(ORE_PROPERTIES));
    public static final RegistryObject<Item> ENDERIAN_ORE_NETHER_ITEM = fromBlock(ENDERIAN_ORE_NETHER);
    public static final RegistryObject<Block> ENDERIAN_ORE_END = BLOCKS.register(BedrockReferences.ENDERIAN_ORE_REGNAME + "_end", () -> new DropExperienceBlock(ORE_PROPERTIES));
    public static final RegistryObject<Item> ENDERIAN_ORE_END_ITEM = fromBlock(ENDERIAN_ORE_END);
    public static final RegistryObject<Block> ENDERIAN_ORE_DEEPSLATE = BLOCKS.register(BedrockReferences.ENDERIAN_ORE_REGNAME + "_deepslate", () -> new DropExperienceBlock(ORE_PROPERTIES));
    public static final RegistryObject<Item> ENDERIAN_ORE_DEEPSLATE_ITEM = fromBlock(ENDERIAN_ORE_DEEPSLATE);

    public static final RegistryObject<Block> ENDERIAN_BLOCK_BLOCK = BLOCKS.register(BedrockReferences.ENDERIAN_BLOCK_REGNAME, () ->new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(5.0F, 6.0F).sound(SoundType.METAL)));
    public static final RegistryObject<Item> ENDERIAN_BLOCK_ITEM = fromBlock(ENDERIAN_BLOCK_BLOCK);

    public static final RegistryObject<EventAltar> EVENT_ALTAR_BLOCK = BLOCKS.register(BedrockReferences.EVENT_ALTAR_REGNAME, () -> new EventAltar(METAL_BLOCK_PROPERTIES.noOcclusion()));
    public static final RegistryObject<Item> EVENT_ALTAR_ITEM = fromBlock(EVENT_ALTAR_BLOCK);
    public static final RegistryObject<BlockEntityType<EventAltarTile>> EVENT_ALTAR_TILE = BLOCK_ENTITIES.register(BedrockReferences.EVENT_ALTAR_REGNAME, () -> BlockEntityType.Builder.of(EventAltarTile::new, EVENT_ALTAR_BLOCK.get()).build(null));


    public static final RegistryObject<DungeonDimensionPortalBlock> DUNGEON_DIMENSION_PORTAL = BLOCKS.register(BedrockReferences.DUNGEON_DIM_PORTAL_REGNAME, DungeonDimensionPortalBlock::new);

    public static final RegistryObject<Rope> ROPE_BLOCK = BLOCKS.register(BedrockReferences.ROPE_REGNAME, () -> new Rope(BLOCK_PROPERTIES.noOcclusion().instabreak().noCollission().friction(3)));
    public static final RegistryObject<Item> ROPE_ITEM = fromBlock(ROPE_BLOCK);

    public static final RegistryObject<HexTile> HEXTILE_BLOCK = BLOCKS.register(BedrockReferences.HEXTILE_REGNAME, () ->new HexTile(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_MAGENTA).requiresCorrectToolForDrops().strength(2.0F, 6.0F)));
    public static final RegistryObject<Item> HEXTILE_ITEM = fromBlock(HEXTILE_BLOCK);

    public static final RegistryObject<Block> ENDERIAN_BRICK_BLOCK = BLOCKS.register(BedrockReferences.ENDERIAN_BRICK_REGNAME, () ->new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).requiresCorrectToolForDrops().strength(2.0F, 6.0F)));
    public static final RegistryObject<Item> ENDERIAN_BRICK_ITEM = fromBlock(ENDERIAN_BRICK_BLOCK);
    public static final RegistryObject<StairBlock> ENDERIAN_STAIRS_BLOCK = BLOCKS.register(BedrockReferences.ENDERIAN_STAIRS_REGNAME, () ->  new StairBlock(ENDERIAN_BRICK_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(ENDERIAN_BRICK_BLOCK.get())));
    public static final RegistryObject<Item> ENDERIAN_STAIRS_ITEM = fromBlock(ENDERIAN_STAIRS_BLOCK);

    public static final RegistryObject<EnderianRitualPedestal> ENDERIAN_RITUAL_PEDESTAL_BLOCK = BLOCKS.register(BedrockReferences.ENDERIAN_RITUAL_PEDESTAL_REGNAME, () -> new EnderianRitualPedestal(METAL_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> ENDERIAN_RITUAL_PEDESTAL_ITEM = fromBlock(ENDERIAN_RITUAL_PEDESTAL_BLOCK);
    public static final RegistryObject<BlockEntityType<EnderianRitualPedestalTile>> ENDERIAN_RITUAL_PEDESTAL_TILE = BLOCK_ENTITIES.register(BedrockReferences.ENDERIAN_RITUAL_PEDESTAL_REGNAME, () -> BlockEntityType.Builder.of(EnderianRitualPedestalTile::new, ENDERIAN_RITUAL_PEDESTAL_BLOCK.get()).build(null));

    public static final RegistryObject<BedrociumSpike> SPIKE_BLOCK = BLOCKS.register(BedrockReferences.SPIKE_REGNAME, () -> new BedrociumSpike(METAL_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> SPIKE_ITEM = fromBlock(SPIKE_BLOCK);

    public static final RegistryObject<BedrociumTower> BASE_SPIKE_BLOCK = BLOCKS.register(BedrockReferences.BASE_SPIKE_REGNAME, () -> new BedrociumTower(METAL_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> BASE_SPIKE_ITEM = fromBlock(BASE_SPIKE_BLOCK);
    public static final RegistryObject<BlockEntityType<BedrockiumTowerTile>> BASE_SPIKE_TILE = BLOCK_ENTITIES.register(BedrockReferences.BASE_SPIKE_REGNAME, () -> BlockEntityType.Builder.of(BedrockiumTowerTile::new, BASE_SPIKE_BLOCK.get()).build(null));


    public static final RegistryObject<BedrociumPedestal> PEDESTAL_BLOCK = BLOCKS.register(BedrockReferences.PEDESTAL_REGNAME, () -> new BedrociumPedestal(METAL_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> PEDESTAL_ITEM = fromBlock(PEDESTAL_BLOCK);
    public static final RegistryObject<BlockEntityType<BedrociumPedestalTile>> PEDESTAL_TILE = BLOCK_ENTITIES.register(BedrockReferences.PEDESTAL_REGNAME, () -> BlockEntityType.Builder.of(BedrociumPedestalTile::new, PEDESTAL_BLOCK.get()).build(null));

    public static final RegistryObject<FaceAttachedHorizontalDirectionalBlock> ITEM_PLATFORM_BLOCK = BLOCKS.register(BedrockReferences.ITEM_PLATFORM_REGNAME, () -> new ItemPlatform(METAL_BLOCK_PROPERTIES.noOcclusion().noCollission().dynamicShape()));
    public static final RegistryObject<Item> ITEM_PLATFORM_ITEM = fromBlock(ITEM_PLATFORM_BLOCK);
    public static final RegistryObject<BlockEntityType<ItemPlatformTile>> ITEM_PLATFORM_TILE = BLOCK_ENTITIES.register(BedrockReferences.ITEM_PLATFORM_REGNAME, () -> BlockEntityType.Builder.of(ItemPlatformTile::new, ITEM_PLATFORM_BLOCK.get()).build(null));

    public static final RegistryObject<Blazium> BLAZIUM_BLOCK = BLOCKS.register(BedrockReferences.BLAZIUM_REGNAME, () -> new Blazium(PLANT_BLOCK_PROPERTIES.lightLevel(value -> 8).explosionResistance(2f)));
    public static final RegistryObject<Item> BLAZIUM_ITEM = fromBlock(BLAZIUM_BLOCK);
    public static final RegistryObject<EnderHush> ENDER_HUSH_BLOCK = BLOCKS.register(BedrockReferences.ENDER_HUSH_REGNAME, () -> new EnderHush(PLANT_BLOCK_PROPERTIES.explosionResistance(1f)));
    public static final RegistryObject<Item> ENDER_HUSH_ITEM = fromBlock(ENDER_HUSH_BLOCK);
    public static final RegistryObject<SunDaize> SUN_DAIZE_BLOCK = BLOCKS.register(BedrockReferences.SUN_DAIZE_REGNAME, () -> new SunDaize(PLANT_BLOCK_PROPERTIES.lightLevel(value -> 14).explosionResistance(1f)));
    public static final RegistryObject<Item> SUN_DAIZE_ITEM = fromBlock(SUN_DAIZE_BLOCK);
    public static final RegistryObject<FluxedSpores> FLUXED_SPORES_BLOCK = BLOCKS.register(BedrockReferences.FLUXED_SPORES_REGNAME, () -> new FluxedSpores(PLANT_BLOCK_PROPERTIES.lightLevel(value -> 0)));
    public static final RegistryObject<Item> FLUXED_SPORES_ITEM = fromBlock(FLUXED_SPORES_BLOCK);

    public static final RegistryObject<LightBlock> LIGHT_BLOCK = BLOCKS.register(BedrockReferences.LIGHT_BLOCK_REGNAME, () -> new LightBlock(BLOCK_PROPERTIES));

    public static final RegistryObject<DFBase> DF_COOBLE_BLOCK = BLOCKS.register(BedrockReferences.DF_COBBLE_REGNAME, () -> new DFBase(BLOCK_PROPERTIES.requiresCorrectToolForDrops()));
    public static final RegistryObject<Item> DF_COOBLE_ITEM = fromBlock(DF_COOBLE_BLOCK);
    public static final RegistryObject<DFBase> DF_DIRT_BLOCK = BLOCKS.register(BedrockReferences.DF_DIRT_REGNAME, () -> new DFBase(DIRT_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> DF_DIRT_ITEM = fromBlock(DF_DIRT_BLOCK);
    public static final RegistryObject<DFBase> DF_GRASS_BLOCK = BLOCKS.register(BedrockReferences.DF_GRASS_REGNAME, () -> new DFBase(DIRT_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> DF_GRASS_ITEM = fromBlock(DF_GRASS_BLOCK);
    public static final RegistryObject<LeavesBlock> DF_OAK_LEAVE_BLOCK = BLOCKS.register(BedrockReferences.DF_OAK_LEAVES_REGNAME, () -> new DFOakLeave(LEAVES_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> DF_OAK_LEAVE_ITEM = fromBlock(DF_OAK_LEAVE_BLOCK);
    public static final RegistryObject<RotatedPillarBlock> DF_OAK_LOG_BLOCK = BLOCKS.register(BedrockReferences.DF_OAK_LOG_REGNAME, () -> new DFOakLog(WOOD_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> DF_OAK_LOG_ITEM = fromBlock(DF_OAK_LOG_BLOCK);
    public static final RegistryObject<BushBlock> DF_SAPPLING_BLOCK = BLOCKS.register(BedrockReferences.DF_SAPPLING_REGNAME, () -> new DFOakSappling(PLANT_BLOCK_PROPERTIES.lightLevel(value -> 0).sound(SoundType.GRASS)));
    public static final RegistryObject<Item> DF_SAPPLING_ITEM = fromBlock(DF_SAPPLING_BLOCK);
    public static final RegistryObject<DFBase> DF_OAK_PLANKS_BLOCK = BLOCKS.register(BedrockReferences.DF_OAK_PLANKS_REGNAME, () -> new DFBase(WOOD_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> DF_OAK_PLANKS_ITEM = fromBlock(DF_OAK_PLANKS_BLOCK);
    public static final RegistryObject<SlabBlock> DF_OAK_SLAB_BLOCK = BLOCKS.register(BedrockReferences.DF_OAK_SLAB_REGNAME, () -> new DFOakSlabs(WOOD_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> DF_OAK_SLAB_ITEM = fromBlock(DF_OAK_SLAB_BLOCK);
    public static final RegistryObject<RotatedPillarBlock> DF_STRIPPED_OAK_LOG_BLOCK = BLOCKS.register(BedrockReferences.DF_STRIPPED_OAK_LOG_REGNAME, () -> new DFOakStrippedLog(WOOD_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> DF_STRIPPED_OAK_LOG_ITEM = fromBlock(DF_STRIPPED_OAK_LOG_BLOCK);

    public static final RegistryObject<FluxedGravityBubble> FLUXED_GRAVITY_BUBBLE_BLOCK = BLOCKS.register(BedrockReferences.FLUXED_GRAVITY_BUBBLE_REGNAME, () -> new FluxedGravityBubble(METAL_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> FLUXED_GRAVITY_BUBBLE_ITEM = fromBlock(FLUXED_GRAVITY_BUBBLE_BLOCK);
    public static final RegistryObject<BlockEntityType<FluxedGravityBubbleTile>> FLUXED_GRAVITY_BUBBLE_TILE = BLOCK_ENTITIES.register(BedrockReferences.FLUXED_GRAVITY_BUBBLE_REGNAME, () -> BlockEntityType.Builder.of(FluxedGravityBubbleTile::new, FLUXED_GRAVITY_BUBBLE_BLOCK.get()).build(null));

    public static final RegistryObject<Block> BEDROCK_COMPRESSED_WIRE_BLOCK = BLOCKS.register(BedrockReferences.BEDROCK_COMPRESSED_WIRE_REGNAME, () ->  new Block(BLOCK_PROPERTIES.requiresCorrectToolForDrops()));
    public static final RegistryObject<Item> BEDROCK_COMPRESSED_WIRE_ITEM = fromBlock(BEDROCK_COMPRESSED_WIRE_BLOCK);

    public static final RegistryObject<Block> RANGE_VIEW_BLOCK = BLOCKS.register(BedrockReferences.RANGE_VIEW_REGNAME, () -> new Block(BLOCK_PROPERTIES.dynamicShape().noCollission().noOcclusion()));
    public static final RegistryObject<VoidTears> VOID_TEAR_BLOCK = BLOCKS.register(BedrockReferences.VOID_TEAR_REGNAME, () -> new VoidTears(BLOCK_PROPERTIES));
    public static final RegistryObject<Item> VOID_TEAR_ITEM = fromBlock(VOID_TEAR_BLOCK);

    public static final RegistryObject<BedrockWireBlock> BEDROCK_WIRE_BLOCK = BLOCKS.register(BedrockReferences.BEDROCK_WIRE_REGNAME, () -> new BedrockWireBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).noCollission().sound(SoundType.WOOL).instabreak().pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Item> BEDROCK_WIRE_ITEM = fromBlock(BEDROCK_WIRE_BLOCK);

    public static final RegistryObject<ScrapeKnife> SCRAPE_KNIFE_ITEM = ITEMS.register(BedrockReferences.SCRAPE_KNIFE_REGNAME, () -> new ScrapeKnife((new Item.Properties()).stacksTo(1)));
    public static final RegistryObject<Item> RAW_ENDERIAN_CHUNK = ITEMS.register(BedrockReferences.RAW_ENDERIAN_REGNAME, () -> new Item((new Item.Properties())));
    public static final RegistryObject<Staff> STAFF_ITEM = ITEMS.register(BedrockReferences.STAFF_REGNAME, () -> new Staff((new Item.Properties()).stacksTo(1)));
    public static final RegistryObject<Item> ENDERIAN_INGOT_ITEM = ITEMS.register(BedrockReferences.ENDERIAN_INGOT_REGNAME, () -> new Item((new Item.Properties())));
    public static final RegistryObject<FluxedCupcake> FLUXED_CUPCAKE_ITEM = ITEMS.register(BedrockReferences.FLUXED_CUPCAKE_REGNAME, () -> new FluxedCupcake((new Item.Properties())));
    public static final RegistryObject<Item> NEBULA_HEART_ITEM = ITEMS.register(BedrockReferences.NEBULA_HEART_REGNAME, () -> new Item((new Item.Properties())));
    public static final RegistryObject<XPMedallion> XP_MEDALLION_ITEM = ITEMS.register(BedrockReferences.XP_MEDALLION_REGNAME, () -> new XPMedallion((new Item.Properties())));
    public static final RegistryObject<MageStaff> MAGE_STAFF_ITEM = ITEMS.register(BedrockReferences.MAGE_STAFF_REGNAME, () -> new MageStaff( 0, -3.2f, (new Item.Properties().stacksTo(1))));
    public static final RegistryObject<FluxedOracle> FLUX_ORACLE_ITEM = ITEMS.register(BedrockReferences.FLUX_ORACLE_REGNAME, () -> new FluxedOracle((new Item.Properties().stacksTo(1))));

    public  static final TagKey<Block> ENDERIAN_ORE = BlockTags.create(new ResourceLocation(MODID,"enderian_ore"));
    public  static final TagKey<Block> DF_LOG = BlockTags.create(new ResourceLocation(MODID,BedrockReferences.DF_OAK_LOG_REGNAME));
    public  static final TagKey<Item> ENDERIAN_ORE_ITEM = ItemTags.create(new ResourceLocation(MODID,"enderian_ore"));
    public  static final TagKey<Item> DF_LOG_ITEM = ItemTags.create(new ResourceLocation(MODID,BedrockReferences.DF_OAK_LOG_REGNAME));

    public static final RegistryObject<StructureType<?>> ALTAR_STRUCTURE = STRUCTURES.register("altar_structure", () ->typeConvert(AltarStructure.CODEC));
//
//    public static final RegistryObject<StructureFeature<JigsawConfiguration>> DUNGEON = STRUCTURES.register("dungeon", () -> new DungeonStructure(false));

    public static <B extends  Block>RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(),(new Item.Properties())));
    }
    // Helper method to register since compiler will complain about typing otherwise
    private static <S extends Structure> StructureType<S> typeConvert(Codec<S> codec) {
        return () -> codec;
    }
}
