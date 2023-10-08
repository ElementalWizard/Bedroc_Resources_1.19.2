package com.alexvr.bedres.datagen;

import com.alexvr.bedres.setup.Registration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class BaseLootTableProvider extends BlockLootSubProvider {


    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    protected final Map<Block, LootTable.Builder> lootTables = new HashMap<>();
    protected final Map<ResourceLocation, LootTable.Builder> entityLootTables = new HashMap<>();

    public BaseLootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {

        this.add(Registration.ENDERIAN_ORE_OVERWORLD.get(),
                block -> createCopperLikeOreDrops(Registration.ENDERIAN_ORE_OVERWORLD.get(), Registration.RAW_ENDERIAN_CHUNK.get()));
        this.add(Registration.ENDERIAN_ORE_NETHER.get(),
                block -> createCopperLikeOreDrops(Registration.ENDERIAN_ORE_OVERWORLD.get(), Registration.RAW_ENDERIAN_CHUNK.get()));
        this.add(Registration.ENDERIAN_ORE_END.get(),
                block -> createCopperLikeOreDrops(Registration.ENDERIAN_ORE_OVERWORLD.get(), Registration.RAW_ENDERIAN_CHUNK.get()));
        this.add(Registration.ENDERIAN_ORE_DEEPSLATE.get(),
                block -> createCopperLikeOreDrops(Registration.ENDERIAN_ORE_OVERWORLD.get(), Registration.RAW_ENDERIAN_CHUNK.get()));

        dropSelf(Registration.ENDERIAN_BLOCK_BLOCK.get());
        dropSelf(Registration.HEXTILE_BLOCK.get());
        dropSelf(Registration.ROPE_BLOCK.get());
        dropSelf(Registration.ENDERIAN_BRICK_BLOCK.get());
        dropSelf(Registration.ENDERIAN_STAIRS_BLOCK.get());
        dropSelf(Registration.EVENT_ALTAR_BLOCK.get());
        dropSelf(Registration.SPIKE_BLOCK.get());
        dropSelf(Registration.BASE_SPIKE_BLOCK.get());
        dropSelf(Registration.PEDESTAL_BLOCK.get());
        dropSelf(Registration.BEDROCK_COMPRESSED_WIRE_BLOCK.get());
        dropSelf(Registration.VOID_TEAR_BLOCK.get());
        dropSelf(Registration.BEDROCK_WIRE_BLOCK.get());

        dropSelf(Registration.BLAZIUM_BLOCK.get());
        dropSelf(Registration.ENDER_HUSH_BLOCK.get());
        dropSelf(Registration.SUN_DAIZE_BLOCK.get());
        dropSelf(Registration.FLUXED_SPORES_BLOCK.get());

        dropSelf(Registration.DF_COOBLE_BLOCK.get());
        dropSelf(Registration.DF_DIRT_BLOCK.get());
        dropSelf(Registration.DF_GRASS_BLOCK.get());

        dropSelf(Registration.DF_OAK_LOG_BLOCK.get());
        dropSelf(Registration.DF_SAPPLING_BLOCK.get());
        dropSelf(Registration.DF_OAK_PLANKS_BLOCK.get());
        dropSelf(Registration.DF_OAK_SLAB_BLOCK.get());
        dropSelf(Registration.DF_STRIPPED_OAK_LOG_BLOCK.get());

        this.add(Registration.DF_OAK_LEAVE_BLOCK.get(), BlockLootSubProvider::createShearsOnlyDrop);

        this.add(Registration.DUNGEON_DIMENSION_PORTAL.get(),noDrop());
        this.add(Registration.LIGHT_BLOCK.get(),noDrop());
        this.add(Registration.RANGE_VIEW_BLOCK.get(),noDrop());

        this.add(Registration.ITEM_PLATFORM_BLOCK.get(), (p_251205_) -> this.createInventoryBoxDrop(p_251205_, Registration.ITEM_PLATFORM_TILE.get()));
        this.add(Registration.ENDERIAN_RITUAL_PEDESTAL_BLOCK.get(), (p_251205_) -> this.createInventoryBoxDrop(p_251205_, Registration.ENDERIAN_RITUAL_PEDESTAL_TILE.get()));
        this.add(Registration.FLUXED_GRAVITY_BUBBLE_BLOCK.get(), (p_251205_) -> this.createInventoryBoxDrop(p_251205_, Registration.FLUXED_GRAVITY_BUBBLE_TILE.get()));

        this.add(Registration.PEDESTAL_BLOCK.get(), this::createNameableBlockEntityTable);
        this.add(Registration.BASE_SPIKE_BLOCK.get(), this::createNameableBlockEntityTable);
        //this.add(Registration.FLUXED_CREEP.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Registration.VOID_TEAR_ITEM.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))));
        //this.add(Registration.CHAINED_BLAZE.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Items.CHAIN).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F))))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(Registration.BEDROCK_WIRE_ITEM.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 3.0F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 2.0F))))));
    }
    protected LootTable.Builder createInventoryBoxDrop(Block pBlock, BlockEntityType<?> entityType) {
        return LootTable.lootTable().withPool(this.applyExplosionCondition(pBlock, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(pBlock)
                .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                .apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("inv", "BlockEntityTag.inv"))
                .apply(SetContainerContents.setContents(entityType).withEntry(DynamicLoot.dynamicEntry(new ResourceLocation("minecraft", "contents")))))));
    }

    protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Registration.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }






}
