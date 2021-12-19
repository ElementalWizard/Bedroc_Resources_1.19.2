package com.alexvr.bedres.datagen;

import com.alexvr.bedres.BedrockResources;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = BedrockResources.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        if(event.includeServer()){
            generator.addProvider(new ModRecipes(generator));
            generator.addProvider(new ModLootTablesProvider(generator));
            ModBlockTags blocktags = new ModBlockTags(generator,event.getExistingFileHelper());
            generator.addProvider(blocktags);
            generator.addProvider(new ModItemTags(generator,blocktags,event.getExistingFileHelper()));
        }
        if(event.includeClient()){
            generator.addProvider(new ModBlockStates(generator,event.getExistingFileHelper()));
            generator.addProvider(new ModItemModels(generator,event.getExistingFileHelper()));
            generator.addProvider(new ModLanguageProvider(generator,"en_us"));
        }
    }


}
