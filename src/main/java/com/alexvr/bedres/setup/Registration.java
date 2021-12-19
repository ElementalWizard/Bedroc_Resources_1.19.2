package com.alexvr.bedres.setup;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.blocks.*;
import com.alexvr.bedres.blocks.decayingfluxedblocks.*;
import com.alexvr.bedres.blocks.itemplatform.ItemPlatform;
import com.alexvr.bedres.blocks.itemplatform.ItemPlatformTile;
import com.alexvr.bedres.blocks.scrapeTank.ScrapeTank;
import com.alexvr.bedres.blocks.scrapeTank.ScrapeTankMenu;
import com.alexvr.bedres.blocks.scrapeTank.ScrapeTankTile;
import com.alexvr.bedres.items.*;
import com.alexvr.bedres.utils.BedrockReferences;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Registration {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BedrockResources.MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BedrockResources.MODID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, BedrockResources.MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, BedrockResources.MODID);

    public static  void init(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
        CONTAINERS.register(bus);
    }

    private static final BlockBehaviour.Properties ORE_PROPERTIES = BlockBehaviour.Properties.of(Material.STONE).strength(2f);
    private static final BlockBehaviour.Properties BLOCK_PROPERTIES = BlockBehaviour.Properties.of(Material.STONE).strength(2f);
    private static final BlockBehaviour.Properties METAL_BLOCK_PROPERTIES = BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(3,800f).requiresCorrectToolForDrops();
    private static final BlockBehaviour.Properties PLANT_BLOCK_PROPERTIES = BlockBehaviour.Properties.of(Material.PLANT).dynamicShape().noCollission().lightLevel(value -> 3).instabreak();
    private static final BlockBehaviour.Properties DIRT_BLOCK_PROPERTIES = BlockBehaviour.Properties.of(Material.DIRT).strength(1f);
    private static final BlockBehaviour.Properties WOOD_BLOCK_PROPERTIES = BlockBehaviour.Properties.of(Material.WOOD).strength(2f);
    private static final BlockBehaviour.Properties LEAVES_BLOCK_PROPERTIES = BlockBehaviour.Properties.of(Material.LEAVES).noOcclusion().sound(SoundType.AZALEA_LEAVES);

    public static final RegistryObject<Block> ENDERIAN_ORE_OVERWORLD = BLOCKS.register(BedrockReferences.ENDERIAN_ORE_REGNAME + "_overworld", () -> new OreBlock(ORE_PROPERTIES));
    public static final RegistryObject<Item> ENDERIAN_ORE_OVERWORLD_ITEM = fromBlock(ENDERIAN_ORE_OVERWORLD);
    public static final RegistryObject<Block> ENDERIAN_ORE_NETHER = BLOCKS.register(BedrockReferences.ENDERIAN_ORE_REGNAME + "_nether", () -> new OreBlock(ORE_PROPERTIES));
    public static final RegistryObject<Item> ENDERIAN_ORE_NETHER_ITEM = fromBlock(ENDERIAN_ORE_NETHER);
    public static final RegistryObject<Block> ENDERIAN_ORE_END = BLOCKS.register(BedrockReferences.ENDERIAN_ORE_REGNAME + "_end", () -> new OreBlock(ORE_PROPERTIES));
    public static final RegistryObject<Item> ENDERIAN_ORE_END_ITEM = fromBlock(ENDERIAN_ORE_END);
    public static final RegistryObject<Block> ENDERIAN_ORE_DEEPSLATE = BLOCKS.register(BedrockReferences.ENDERIAN_ORE_REGNAME + "_deepslate", () -> new OreBlock(ORE_PROPERTIES));
    public static final RegistryObject<Item> ENDERIAN_ORE_DEEPSLATE_ITEM = fromBlock(ENDERIAN_ORE_DEEPSLATE);

    public static final RegistryObject<Block> ENDERIAN_BLOCK_BLOCK = BLOCKS.register(BedrockReferences.ENDERIAN_BLOCK_REGNAME, () -> new Block(METAL_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> ENDERIAN_BLOCK_ITEM = fromBlock(ENDERIAN_BLOCK_BLOCK);

    public static final RegistryObject<ScrapeTank> SCRAPE_TANK_BLOCK = BLOCKS.register(BedrockReferences.SCRAPE_TANK_REGNAME, () -> new ScrapeTank(METAL_BLOCK_PROPERTIES.noOcclusion().randomTicks().lightLevel(value -> 12)));
    public static final RegistryObject<BlockEntityType<ScrapeTankTile>> SCRAPE_TANK_TILE = BLOCK_ENTITIES.register(BedrockReferences.SCRAPE_TANK_REGNAME, () -> BlockEntityType.Builder.of(ScrapeTankTile::new, SCRAPE_TANK_BLOCK.get()).build(null));
    public static final RegistryObject<Item> SCRAPE_TANK_ITEM = fromBlock(SCRAPE_TANK_BLOCK);
    public static final RegistryObject<MenuType<ScrapeTankMenu>> SCRAPE_TANK_CONTAINER = CONTAINERS.register(BedrockReferences.SCRAPE_TANK_REGNAME, () -> IForgeMenuType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new ScrapeTankMenu(windowId, world, pos, inv, inv.player);
    }));

    public static final RegistryObject<ScraperMotor> SCRAPER_MOTOR_BLOCK = BLOCKS.register(BedrockReferences.SCRAPER_MOTOR_REGNAME, () -> new ScraperMotor(METAL_BLOCK_PROPERTIES.noOcclusion()));
    public static final RegistryObject<Item> SCRAPER_MOTOR_ITEM = fromBlock(SCRAPER_MOTOR_BLOCK);

    public static final RegistryObject<Block> ENDERIAN_BRICK_BLOCK = BLOCKS.register(BedrockReferences.ENDERIAN_BRICK_REGNAME, () -> new Block(BLOCK_PROPERTIES.requiresCorrectToolForDrops()));
    public static final RegistryObject<Item> ENDERIAN_BRICK_ITEM = fromBlock(ENDERIAN_BRICK_BLOCK);
    public static final RegistryObject<StairBlock> ENDERIAN_STAIRS_BLOCK = BLOCKS.register(BedrockReferences.ENDERIAN_STAIRS_REGNAME, () -> new StairBlock(ENDERIAN_BRICK_BLOCK.get().defaultBlockState() ,BlockBehaviour.Properties.copy(ENDERIAN_BRICK_BLOCK.get())));
    public static final RegistryObject<Item> ENDERIAN_STAIRS_ITEM = fromBlock(ENDERIAN_STAIRS_BLOCK);

    public static final RegistryObject<EnderianRitualPedestal> ENDERIAN_RITUAL_PEDESTAL_BLOCK = BLOCKS.register(BedrockReferences.ENDERIAN_RITUAL_PEDESTAL_REGNAME, () -> new EnderianRitualPedestal(METAL_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> ENDERIAN_RITUAL_PEDESTAL_ITEM = fromBlock(ENDERIAN_RITUAL_PEDESTAL_BLOCK);

    public static final RegistryObject<BedrociumSpike> SPIKE_BLOCK = BLOCKS.register(BedrockReferences.SPIKE_REGNAME, () -> new BedrociumSpike(METAL_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> SPIKE_ITEM = fromBlock(SPIKE_BLOCK);

    public static final RegistryObject<BedrociumTower> BASE_SPIKE_BLOCK = BLOCKS.register(BedrockReferences.BASE_SPIKE_REGNAME, () -> new BedrociumTower(METAL_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> BASE_SPIKE_ITEM = fromBlock(BASE_SPIKE_BLOCK);

    public static final RegistryObject<BedrociumPedestal> PEDESTAL_BLOCK = BLOCKS.register(BedrockReferences.PEDESTAL_REGNAME, () -> new BedrociumPedestal(METAL_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> PEDESTAL_ITEM = fromBlock(PEDESTAL_BLOCK);

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

    public static final RegistryObject<DFBase> DF_COOBLE_BLOCK = BLOCKS.register(BedrockReferences.DF_COOBLE_REGNAME, () -> new DFBase(BLOCK_PROPERTIES.requiresCorrectToolForDrops()));
    public static final RegistryObject<Item> DF_COOBLE_ITEM = fromBlock(DF_COOBLE_BLOCK);
    public static final RegistryObject<DFBase> DF_DIRT_BLOCK = BLOCKS.register(BedrockReferences.DF_DIRT_REGNAME, () -> new DFBase(DIRT_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> DF_DIRT_ITEM = fromBlock(DF_DIRT_BLOCK);
    public static final RegistryObject<DFBase> DF_GRASS_BLOCK = BLOCKS.register(BedrockReferences.DF_GRASS_REGNAME, () -> new DFBase(DIRT_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> DF_GRASS_ITEM = fromBlock(DF_GRASS_BLOCK);
    public static final RegistryObject<LeavesBlock> DF_OAK_LEAVE_BLOCK = BLOCKS.register(BedrockReferences.DF_OAK_LEAVES_REGNAME, () -> new DFOakLeave(LEAVES_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> DF_OAK_LEAVE_ITEM = fromBlock(DF_OAK_LEAVE_BLOCK);
    public static final RegistryObject<RotatedPillarBlock> DF_OAK_LOG_BLOCK = BLOCKS.register(BedrockReferences.DF_OAK_LOG_REGNAME, () -> new DFOakLog(WOOD_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> DF_OAK_LOG_ITEM = fromBlock(DF_OAK_LOG_BLOCK);
    public static final RegistryObject<SaplingBlock> DF_SAPPLING_BLOCK = BLOCKS.register(BedrockReferences.DF_SAPPLING_REGNAME, () -> new DFOakSappling(PLANT_BLOCK_PROPERTIES.lightLevel(value -> 0)));
    public static final RegistryObject<Item> DF_SAPPLING_ITEM = fromBlock(DF_SAPPLING_BLOCK);
    public static final RegistryObject<DFBase> DF_OAK_PLANKS_BLOCK = BLOCKS.register(BedrockReferences.DF_OAK_PLANKS_REGNAME, () -> new DFBase(WOOD_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> DF_OAK_PLANKS_ITEM = fromBlock(DF_OAK_PLANKS_BLOCK);
    public static final RegistryObject<SlabBlock> DF_OAK_SLAB_BLOCK = BLOCKS.register(BedrockReferences.DF_OAK_SLAB_REGNAME, () -> new DFOakSlabs(WOOD_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> DF_OAK_SLAB_ITEM = fromBlock(DF_OAK_SLAB_BLOCK);
    public static final RegistryObject<RotatedPillarBlock> DF_STRIPPED_OAK_LOG_BLOCK = BLOCKS.register(BedrockReferences.DF_STRIPPED_OAK_LOG_REGNAME, () -> new DFOakStrippedLog(WOOD_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> DF_STRIPPED_OAK_LOG_ITEM = fromBlock(DF_STRIPPED_OAK_LOG_BLOCK);

    public static final RegistryObject<FluxedGravityBubble> FLUXED_GRAVITY_BUBBLE_BLOCK = BLOCKS.register(BedrockReferences.FLUXED_GRAVITY_BUBBLE_REGNAME, () -> new FluxedGravityBubble(METAL_BLOCK_PROPERTIES));
    public static final RegistryObject<Item> FLUXED_GRAVITY_BUBBLE_ITEM = fromBlock(FLUXED_GRAVITY_BUBBLE_BLOCK);

    public static final RegistryObject<Block> BEDROCK_COMPRESSED_WIRE_BLOCK = BLOCKS.register(BedrockReferences.BEDROCK_COMPRESSED_WIRE_REGNAME, () ->  new Block(BLOCK_PROPERTIES.requiresCorrectToolForDrops()));
    public static final RegistryObject<Item> BEDROCK_COMPRESSED_WIRE_ITEM = fromBlock(BEDROCK_COMPRESSED_WIRE_BLOCK);

    public static final RegistryObject<VoidTears> VOID_TEAR_BLOCK = BLOCKS.register(BedrockReferences.VOID_TEAR_REGNAME, () -> new VoidTears(BLOCK_PROPERTIES));
    public static final RegistryObject<Item> VOID_TEAR_ITEM = fromBlock(VOID_TEAR_BLOCK);

    public static final RegistryObject<BedrockWireBlock> BEDROCK_WIRE_BLOCK = BLOCKS.register(BedrockReferences.BEDROCK_WIRE_REGNAME, () -> new BedrockWireBlock(BLOCK_PROPERTIES.noCollission().instabreak().dynamicShape()));
    public static final RegistryObject<Item> BEDROCK_WIRE_ITEM = fromBlock(BEDROCK_WIRE_BLOCK);

    public static final RegistryObject<ScrapeKnife> SCRAPE_KNIFE_ITEM = ITEMS.register(BedrockReferences.SCRAPE_KNIFE_REGNAME, () -> new ScrapeKnife((new Item.Properties()).tab(ModSetup.GROUP).stacksTo(1)));
    public static final RegistryObject<Item> RAW_ENDERIAN_CHUNK = ITEMS.register(BedrockReferences.RAW_ENDERIAN_REGNAME, () -> new Item((new Item.Properties()).tab(ModSetup.GROUP)));
    public static final RegistryObject<ScraperMesh> SCRAPER_MESH_ITEM = ITEMS.register(BedrockReferences.SCRAPER_MESH_REGNAME, () -> new ScraperMesh((new Item.Properties()).tab(ModSetup.GROUP)));
    public static final RegistryObject<FluxedCreepEggItem> FLUXED_CREEP_EGG_ITEM = ITEMS.register(BedrockReferences.FLUXED_CREEP_EGG_REGNAME, () -> new FluxedCreepEggItem((new Item.Properties()).tab(ModSetup.GROUP)));
    public static final RegistryObject<SporeDeityEggItem> SPORE_DEITY_EGG_ITEM = ITEMS.register(BedrockReferences.SPORE_DEITY_EGG_REGNAME, () -> new SporeDeityEggItem((new Item.Properties()).tab(ModSetup.GROUP)));
    public static final RegistryObject<Staff> STAFF_ITEM = ITEMS.register(BedrockReferences.STAFF_REGNAME, () -> new Staff((new Item.Properties()).tab(ModSetup.GROUP).stacksTo(1)));
    public static final RegistryObject<FluxOracle> FLUX_ORACLE_ITEM = ITEMS.register(BedrockReferences.FLUX_ORACLE_REGNAME, () -> new FluxOracle((new Item.Properties()).tab(ModSetup.GROUP).stacksTo(1)));
    public static final RegistryObject<Item> ENDERIAN_INGOT_ITEM = ITEMS.register(BedrockReferences.ENDERIAN_INGOT_REGNAME, () -> new Item((new Item.Properties()).tab(ModSetup.GROUP)));
    public static final RegistryObject<FluxedCupcake> FLUXED_CUPCAKE_ITEM = ITEMS.register(BedrockReferences.FLUXED_CUPCAKE_REGNAME, () -> new FluxedCupcake((new Item.Properties()).tab(ModSetup.GROUP)));
    public static final RegistryObject<Item> NEBULA_HEART_ITEM = ITEMS.register(BedrockReferences.NEBULA_HEART_REGNAME, () -> new Item((new Item.Properties()).tab(ModSetup.GROUP)));

    public  static final Tags.IOptionalNamedTag<Block> ENDERIAN_ORE = BlockTags.createOptional(new ResourceLocation(BedrockResources.MODID,"enderian_ore"));
    public  static final Tags.IOptionalNamedTag<Block> DF_LOG = BlockTags.createOptional(new ResourceLocation(BedrockResources.MODID,BedrockReferences.DF_OAK_LOG_REGNAME));
    public  static final Tags.IOptionalNamedTag<Item> ENDERIAN_ORE_ITEM = ItemTags.createOptional(new ResourceLocation(BedrockResources.MODID,"enderian_ore"));
    public  static final Tags.IOptionalNamedTag<Item> DF_LOG_ITEM = ItemTags.createOptional(new ResourceLocation(BedrockResources.MODID,BedrockReferences.DF_OAK_LOG_REGNAME));

    public static <B extends  Block>RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(),(new Item.Properties()).tab(ModSetup.GROUP)));
    }

}
