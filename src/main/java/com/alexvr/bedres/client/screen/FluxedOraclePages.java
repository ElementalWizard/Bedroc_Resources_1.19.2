package com.alexvr.bedres.client.screen;

import com.alexvr.bedres.capability.abilities.PlayerAbilityProvider;
import com.alexvr.bedres.recipes.ModRecipeRegistry;
import com.alexvr.bedres.recipes.eventRituals.EventRitualsRecipes;
import com.alexvr.bedres.recipes.ritualAltar.RitualAltarRecipes;
import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.utils.RenderHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class FluxedOraclePages {
    public String name;
    public String description;
    public FluxOracleScreenGui parentScreen;
    public ResourceLocation image;
    public ResourceLocation base_image = new ResourceLocation("bedres", "textures/gui/widget/widget_base.png");
    public int pageNumber = 1;
    public int pages = 1;
    public int pageID;
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick){}

    public void nextPage(){
        if (pageNumber<pages){
            pageNumber++;
        }
    }
    public void prevPage(){
        if (pageNumber>1){
            pageNumber--;
        }
    }

    public static class AltarPage extends FluxedOraclePages{
        public List<AltarRecipePage> recipes;

        public AltarPage(FluxOracleScreenGui screen){
            this.name = "bedres.page_name.altar";
            this.description = "The altar is a multiblock structure found in the world composed of 4 Bedrocium Towers and 1 Bedrocium Pedestal." +
                    " This structure can be used for many things, most importantly crafting and infusing items/blocks." +
                    " You can right click any of the slots of the towers or the pedestal to place an item in it." +
                    " Once a correct recipe is placed the output will be displayed on top of the altar." +
                    " Right click the pedestal with bedrock scrapes to start crafting.";
            this.parentScreen = screen;
            this.image = new ResourceLocation("bedres", "textures/gui/widget/altar.png");
            recipes = new ArrayList<>();
//            for (RitualAltarRecipes recipe: ModRecipeRegistry.getRitualAltarRecipes()){
//                AltarRecipePage recipePage = new AltarRecipePage(screen,recipe);
//                recipePage.name = recipe.getResultItem().getDisplayName().getString();
//                recipePage.description = recipe.getIngredientList().toString();
//                recipes.add(recipePage);
//            }
            this.pages = recipes.size() + 1;
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            switch (pageNumber){
                case 1://TODO translate
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4, Component.translatable(name),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal(description)),parentScreen.width/3,parentScreen.height/3,175,DyeColor.GRAY.getTextColor());
                    RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
                            new ItemStack(Registration.PEDESTAL_ITEM.get()),pPoseStack,(parentScreen.width/3) ,(parentScreen.height/4));
                    break;
                default:
                    if (pageNumber-2 < recipes.size()){
                        recipes.get(pageNumber-2).render(pPoseStack,pMouseX,pMouseY,pPartialTick);
                    }
                    break;
            }
        }

    }
    public static class AltarRecipePage extends FluxedOraclePages{
        RitualAltarRecipes recipe;
        public AltarRecipePage(FluxOracleScreenGui screen,RitualAltarRecipes recipe){
            this.parentScreen = screen;
            this.recipe = recipe;

        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.translatable(name),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
            int x = (parentScreen.width/3)-1;
            int y= ((parentScreen.height*2)/5)-1;
            int moduleIndex= 0;
            int moduleX= 1;
            int moduleY= 0;
            boolean nextModule = false;
            parentScreen.renderTexture(pPoseStack, x, y,167,107,new ResourceLocation("bedres", "textures/gui/jei/category/pedestal.png"));

            for (int i =0; i<recipe.getIngredientList().size()-1;i++){
                switch (i % 4) {
                    case 0 -> {
                        x = (moduleX * 36) + (parentScreen.width/3);
                        y =(moduleY * 35) + ((parentScreen.height*2)/5);
                        nextModule = false;
                    }
                    case 1 -> {
                        x = 19 + (moduleX * 36) + (parentScreen.width/3);
                        y =(moduleY * 35) + ((parentScreen.height*2)/5);
                    }
                    case 2 -> {
                        x =(moduleX * 36) + (parentScreen.width/3);
                        y = 18 + (moduleY * 35) + ((parentScreen.height*2)/5);
                    }
                    case 3 -> {
                        x = 19 + (moduleX * 36) + (parentScreen.width/3);
                        y = 18 + (moduleY * 35) + ((parentScreen.height*2)/5);
                        nextModule = true;
                    }
                }
                if (nextModule){
                    moduleIndex++;
                    switch (moduleIndex){
                        case 1 -> {moduleX = 0;moduleY = 1;}
                        case 2 -> {moduleX = 2;moduleY = 1;}
                        case 3 -> {moduleX = 1;moduleY = 2;}
                        case 4 -> {moduleX = 0;moduleY = 0;}
                        case 5 -> {moduleX = 2;moduleY = 0;}
                        case 6 -> {moduleX = 0;moduleY = 2;}
                        case 7 -> {moduleX = 2;moduleY = 2;}
                    }
                }
                if (i+1 < recipe.getIngredientList().size()){
                    RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
                            recipe.getIngredientList().get(i+1),pPoseStack,x ,y);
                }
            }
            RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
                    recipe.getIngredientList().get(0),pPoseStack,(parentScreen.width*3)/7 ,(parentScreen.height*4)/7);
            RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
                    recipe.getResultItem(),pPoseStack,(parentScreen.width*5)/8 ,(parentScreen.height*4)/7);

            super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        }

    }
    public static class FluxPage extends FluxedOraclePages{
        public FluxPage(FluxOracleScreenGui screen){
            this.name = "bedres.page_name.flux";
            this.description = "Flux is the energy that allows you to do things in this mod. Here you can see your current abilities:";
            this.parentScreen = screen;
            this.image = new ResourceLocation("bedres", "textures/gui/widget/widget_base.png");

        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            switch (pageNumber){
                case 1:
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.translatable(name),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal(description)),parentScreen.width/3,parentScreen.height/3,175,DyeColor.GRAY.getTextColor());
                    RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
                            new ItemStack(Registration.BEDROCK_WIRE_ITEM.get()),pPoseStack,(parentScreen.width/3) ,(parentScreen.height/4));
                    Player player = Minecraft.getInstance().player;
                    if (player != null ){
                        player.reviveCaps();
                        player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY,null).ifPresent(h -> {
                            int descHeight = Minecraft.getInstance().font.wordWrapHeight(description,175);
                            parentScreen.renderString(parentScreen.width/2,parentScreen.height/3 + descHeight + Minecraft.getInstance().font.lineHeight,Component.literal("Flux: ").append(Component.literal(h.getFlux() +"/" + h.getMaxFlux()).withStyle(ChatFormatting.DARK_AQUA)),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                            parentScreen.renderString(parentScreen.width/2,parentScreen.height/3 + descHeight + (Minecraft.getInstance().font.lineHeight*2),Component.literal("Pick Tier: ").append(Component.literal((h.getPick().equals("no") ? "NA": h.getPick())).withStyle(ChatFormatting.DARK_AQUA)),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                            parentScreen.renderString(parentScreen.width/2,parentScreen.height/3 + descHeight + (Minecraft.getInstance().font.lineHeight * 3),Component.literal("Axe Tier: ").append(Component.literal((h.getAxe().equals("no") ? "NA": h.getAxe())).withStyle(ChatFormatting.DARK_AQUA)),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                            parentScreen.renderString(parentScreen.width/2,parentScreen.height/3 + descHeight + (Minecraft.getInstance().font.lineHeight * 4),Component.literal("Sword Tier: ").append(Component.literal((h.getSword().equals("no") ? "NA": h.getSword())).withStyle(ChatFormatting.DARK_AQUA)),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                            parentScreen.renderString(parentScreen.width/2,parentScreen.height/3 + descHeight + (Minecraft.getInstance().font.lineHeight * 5),Component.literal("Shovel Tier: ").append(Component.literal((h.getShovel().equals("no") ? "NA": h.getShovel())).withStyle(ChatFormatting.DARK_AQUA)),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                            parentScreen.renderString(parentScreen.width/2,parentScreen.height/3 + descHeight + (Minecraft.getInstance().font.lineHeight * 6),Component.literal("Hoe Tier: ").append(Component.literal((h.getHoe().equals("no") ? "NA": h.getHoe())).withStyle(ChatFormatting.DARK_AQUA)),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                            parentScreen.renderString(parentScreen.width/2,parentScreen.height/3 + descHeight + (Minecraft.getInstance().font.lineHeight * 7),Component.literal("Mining Speed Boost: ").append(Component.literal(String.valueOf(h.getMiningSpeedBoost())).withStyle(ChatFormatting.DARK_AQUA)),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                            parentScreen.renderString(parentScreen.width/2,parentScreen.height/3 + descHeight + (Minecraft.getInstance().font.lineHeight * 8),Component.literal("Jump Boost: ").append(Component.literal(String.valueOf(h.getJumpBoost())).withStyle(ChatFormatting.DARK_AQUA)),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                            parentScreen.renderString(parentScreen.width/2,parentScreen.height/3 + descHeight + (Minecraft.getInstance().font.lineHeight * 9),Component.literal("Fall Damage: ").append(Component.literal((h.takesFalldamage() ? "Taken": "Ignored")).withStyle(ChatFormatting.DARK_AQUA)),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());

                        });
                        player.invalidateCaps();
                    }
                    break;
            }
        }
    }

    public static class ScrapePage extends FluxedOraclePages{
        public ScrapePage(FluxOracleScreenGui screen){
            this.name = "bedres.page_name.scrape";
            this.description = "Bedrock Scrapes are the introductory item to this mod." +
                    " By shift using a Scrape knife on bedrock you will scrape some of it of and inhale some bedrock particles which arent great for you until you learn to manage them. " +
                    "Right clicking this on a block will place it down like redstone. ";
            this.parentScreen = screen;
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            switch (pageNumber){
                case 1:
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.translatable(name),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal(description)),parentScreen.width/3,parentScreen.height/3,175,DyeColor.GRAY.getTextColor());
                    RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
                            new ItemStack(Registration.BEDROCK_WIRE_ITEM.get()),pPoseStack,(parentScreen.width/3) ,(parentScreen.height/4));
                    break;
            }
        }
    }

    public static class KnifePage extends FluxedOraclePages{
        public KnifePage(FluxOracleScreenGui screen){
            this.name = "bedres.page_name.knife";
            this.description = "The Scrape knife is used to scrape bedrock particles of bedrock.";
            this.parentScreen = screen;
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            switch (pageNumber){
                case 1:
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.translatable(name),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal(description)),parentScreen.width/3,parentScreen.height/3,175,DyeColor.GRAY.getTextColor());
                    RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
                            new ItemStack(Registration.SCRAPE_KNIFE_ITEM.get()),pPoseStack,(parentScreen.width/3) ,(parentScreen.height/4));
                    break;
            }
        }
    }

    public static class BlaziumPage extends FluxedOraclePages{
        public BlaziumPage(FluxOracleScreenGui screen){
            this.name = "bedres.page_name.blazium";
            this.description = "Blazium is a flower that will spawn in very hostile environment. Because of this it has evolved to protect itself from anything trying to harvest it for its blaze powder." +
                    " Any creature that breaks it will get burnt, and to prevent anyone from trying to get it by removing its roots it has learned to be independent from the block bellow it.";
            this.parentScreen = screen;
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            switch (pageNumber){
                case 1:
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.translatable(name),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal(description)),parentScreen.width/3,parentScreen.height/3,175,DyeColor.GRAY.getTextColor());
                    RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
                            new ItemStack(Registration.BLAZIUM_ITEM.get()),pPoseStack,(parentScreen.width/3) ,(parentScreen.height/4));
                    break;
            }
        }
    }

    public static class EnderHushPage extends FluxedOraclePages{
        public EnderHushPage(FluxOracleScreenGui screen){
            this.name = "bedres.page_name.ender_hush";
            this.description = "Ender Hush is a plant that was born from the specs left behind by endermen teleporting or moving blocks around.";
            this.parentScreen = screen;
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            switch (pageNumber){
                case 1:
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.translatable(name),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal(description)),parentScreen.width/3,parentScreen.height/3,175,DyeColor.GRAY.getTextColor());
                    RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
                            new ItemStack(Registration.ENDER_HUSH_ITEM.get()),pPoseStack,(parentScreen.width/3) ,(parentScreen.height/4));
                    break;
            }
        }
    }

    public static class DaizePage extends FluxedOraclePages{
        public DaizePage(FluxOracleScreenGui screen){
            this.name = "bedres.page_name.daize";
            this.description = "Sun Daize is a flower believed to have been the predecessors of the sun flower.";
            this.parentScreen = screen;
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            switch (pageNumber){
                case 1:
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.translatable(name),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal(description)),parentScreen.width/3,parentScreen.height/3,175,DyeColor.GRAY.getTextColor());
                    RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
                            new ItemStack(Registration.SUN_DAIZE_ITEM.get()),pPoseStack,(parentScreen.width/3) ,(parentScreen.height/4));
                    break;
            }
        }
    }

    public static class EnderianOrePage extends FluxedOraclePages{
        int model = 0;
        public EnderianOrePage(FluxOracleScreenGui screen){
            this.name = "bedres.page_name.e_ore";
            this.description = "A very rare ore found at the very bottom of the overworld, the end, or nether, its more rare than diamonds. It is said that its an natural combination of gold, ender pearls, and obsidian";
            this.parentScreen = screen;
        }

        public void randomizeOre(){
            model = new Random().nextInt(1,4);
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            switch (pageNumber){
                case 1:
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.translatable(name),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal(description)),parentScreen.width/3,parentScreen.height/3,175,DyeColor.GRAY.getTextColor());
                    Item item = Registration.ENDERIAN_ORE_OVERWORLD_ITEM.get();
                    switch (model){
                        case 1:
                            item = Registration.ENDERIAN_ORE_END_ITEM.get();
                            break;
                        case 2:
                            item = Registration.ENDERIAN_ORE_NETHER_ITEM.get();
                            break;
                        case 3:
                            item = Registration.ENDERIAN_ORE_DEEPSLATE_ITEM.get();
                            break;
                    }
                    RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
                            new ItemStack(item),pPoseStack,(parentScreen.width/3) ,(parentScreen.height/4));
                    break;
            }
        }
    }

    public static class EnderianIngotPage extends FluxedOraclePages{
        public EnderianIngotPage(FluxOracleScreenGui screen){
            this.name = "bedres.page_name.e_ingot";
            this.description = "Given the rarity of the ore, legend tells of a shrine, or altar capable of crafting these by infusing a gold ingot with four ender pearls, four obsidian and two amethyst";
            this.parentScreen = screen;
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            switch (pageNumber){
                case 1:
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.translatable(name),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal(description)),parentScreen.width/3,parentScreen.height/3,175,DyeColor.GRAY.getTextColor());
                    RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
                            new ItemStack(Registration.ENDERIAN_INGOT_ITEM.get()),pPoseStack,(parentScreen.width/3) ,(parentScreen.height/4));
                    break;
            }
        }
    }

    public static class ScrapeTankPage extends FluxedOraclePages{
        public ScrapeTankPage(FluxOracleScreenGui screen){
            this.name = "bedres.page_name.tank";
            this.description = "A tank that can only accept scrapes of bedrock.";
            this.parentScreen = screen;
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            switch (pageNumber){
                case 1:
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.translatable(name),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal(description)),parentScreen.width/3,parentScreen.height/3,175,DyeColor.GRAY.getTextColor());
                    RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
                            new ItemStack(Registration.SCRAPE_TANK_ITEM.get()),pPoseStack,(parentScreen.width/3) ,(parentScreen.height/4));
                    break;
            }
        }
    }

    public static class ItemPlatformPage extends FluxedOraclePages{
        public ItemPlatformPage(FluxOracleScreenGui screen){
            this.name = "bedres.page_name.item_platform";
            this.description = "Gold blocks infused into Item Platform allowing blocks to be held in space." +
                    " Due to their nature they will place in the direction you are facing, regardless of block face clicked. They seem to shine with arcane essence.";
            this.parentScreen = screen;
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            switch (pageNumber){
                case 1:
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.translatable(name),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal(description)),parentScreen.width/3,parentScreen.height/3,175,DyeColor.GRAY.getTextColor());
                    RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
                            new ItemStack(Registration.ITEM_PLATFORM_ITEM.get()),pPoseStack,(parentScreen.width/3) ,(parentScreen.height/4));
                    break;
            }
        }
    }

    public static class EventItemPlatformPage extends FluxedOraclePages{
        public EventItemPlatformPage(FluxOracleScreenGui screen){
            this.name = "bedres.page_name.e_item_platform";
            this.description = "Item platforms can be further stabilized with Enderian Bricks to create a platform that can be used for Player infusion Rituals." +
                    " They seem the perfect conduit to infuse myself with items ";
            this.parentScreen = screen;
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            switch (pageNumber){
                case 1:
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.translatable(name),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal(description)),parentScreen.width/3,parentScreen.height/3,175,DyeColor.GRAY.getTextColor());
                    RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
                            new ItemStack(Registration.ENDERIAN_RITUAL_PEDESTAL_ITEM.get()),pPoseStack,(parentScreen.width/3) ,(parentScreen.height/4));
                    break;
            }
        }
    }

    public static class EventRitualPage extends FluxedOraclePages{

        public List<FluxedOraclePages> recipes;
        public EventRitualPage(FluxOracleScreenGui screen){
            this.name = "bedres.page_name.event_ritual";
            this.description = "Event Rituals are to improve the players abilities. The recipes are direction specific for the wire and the platforms, recipes assume up is north and can also be found in JEI. The items can be in any pedestal";
            this.parentScreen = screen;
            recipes = new ArrayList<>();
//            for (EventRitualsRecipes recipe: ModRecipeRegistry.getEventRitualRecipes()){
//                EventRitualRecipePage recipePage = new EventRitualRecipePage(screen,recipe);
//                recipePage.name = recipe.getEvent();
//                recipePage.description = EventRitualsRecipes.getDescription(recipe);
//                //recipePage.image = RECIPE_GUI_EVENT_ALTAR;
//                recipes.add(recipePage);
//            }
            this.pages = recipes.size() + 1;
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            switch (pageNumber){
                case 1:
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.translatable(name),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal(description)),parentScreen.width/3,parentScreen.height/3,175,DyeColor.GRAY.getTextColor());
                    RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
                            new ItemStack(Registration.EVENT_ALTAR_ITEM.get()),pPoseStack,(parentScreen.width/3) ,(parentScreen.height/4));
                    break;
                default:
                    if (pageNumber-2 < recipes.size()){
                        recipes.get(pageNumber-2).render(pPoseStack,pMouseX,pMouseY,pPartialTick);
                    }
                    break;
            }
        }
    }
    public static class EventRitualRecipePage extends FluxedOraclePages{

        public EventRitualsRecipes recipe;
        public EventRitualRecipePage(FluxOracleScreenGui screen, EventRitualsRecipes recipe){
            this.parentScreen = screen;
            this.recipe = recipe;
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.translatable(name),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
            Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal(description)),parentScreen.width/3,parentScreen.height/3,175,DyeColor.GRAY.getTextColor());
            Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal(recipe.getIngredientList().toString())),parentScreen.width/3,(parentScreen.height/3)+ Minecraft.getInstance().font.wordWrapHeight(description,175),175,DyeColor.GRAY.getTextColor());

            super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        }
    }

    public static class SporeMushPage extends FluxedOraclePages{
        public SporeMushPage(FluxOracleScreenGui screen){
            this.name = "bedres.page_name.spore_mush";
            this.description = "It seems that once you have enough flux on your system little spores start detaching from your body and falling off as to try and spread the infection, i should probably pick them up." +
                    " Maybe i can make something with them to empower myself.";
            this.parentScreen = screen;
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            switch (pageNumber){
                case 1:
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.translatable(name),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal(description)),parentScreen.width/3,parentScreen.height/3,175,DyeColor.GRAY.getTextColor());
                    RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
                            new ItemStack(Registration.FLUXED_SPORES_ITEM.get()),pPoseStack,(parentScreen.width/3) ,(parentScreen.height/4));
                    break;
            }
        }
    }

    public static class CupcakePage extends FluxedOraclePages{
        public CupcakePage(FluxOracleScreenGui screen){
            this.name = "bedres.page_name.cupcake";
            this.description = "I can combine spores with the same components of a cake and infuse them in a wooden bowl in Item Infusion Altar a cupcake will result that might refill around a portion of my current flux.";
            this.parentScreen = screen;
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            switch (pageNumber){
                case 1:
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.translatable(name),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal(description)),parentScreen.width/3,parentScreen.height/3,175,DyeColor.GRAY.getTextColor());
                    RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
                            new ItemStack(Registration.FLUXED_CUPCAKE_ITEM.get()),pPoseStack,(parentScreen.width/3) ,(parentScreen.height/4));
                    break;
            }
        }
    }

    public static class GavityBubblePage extends FluxedOraclePages{
        public GavityBubblePage(FluxOracleScreenGui screen){
            this.name = "bedres.page_name.g_bubble";
            this.description = "It seem to be able to create a bubble that can let me control gravity in a defined area at a cost." +
                    " Shift Right clicking will allow me to see the area of effect, right clicking with bedrock scapes or compressed scrapes allows me to fuel this.";
            this.parentScreen = screen;
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            switch (pageNumber){
                case 1:
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.translatable(name),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal(description)),parentScreen.width/3,parentScreen.height/3,175,DyeColor.GRAY.getTextColor());
                    RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
                            new ItemStack(Registration.FLUXED_GRAVITY_BUBBLE_ITEM.get()),pPoseStack,(parentScreen.width/3) ,(parentScreen.height/4));
                    break;
            }
        }
    }

    public static class GravStaffPage extends FluxedOraclePages{
        public GravStaffPage(FluxOracleScreenGui screen){
            this.name = "bedres.page_name.g_staff";
            this.description = "I seem to be able to create a staff that can let me control gravity to a certain degree." +
                    " When activated it will occasionally infuse some flux into itself and after using the staff for a while i get hungry. " +
                    "Regardless when its activated my vertical velocity is suspended and the height i had fallen is reduced, making me feel like jumping on the moon. ";
            this.parentScreen = screen;
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            switch (pageNumber){
                case 1:
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.translatable(name),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal(description)),parentScreen.width/3,parentScreen.height/3,175,DyeColor.GRAY.getTextColor());
                    RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
                            new ItemStack(Registration.STAFF_ITEM.get()),pPoseStack,(parentScreen.width/3) ,(parentScreen.height/4));
                    break;
            }
        }
    }

    public static class MageStaffPage extends FluxedOraclePages{
        public MageStaffPage(FluxOracleScreenGui screen){
            this.name = "bedres.page_name.m_staff";
            this.description = "I have created a staff that can have different Effects based on the rune activated and the amount of time charged will dictate its power";
            this.parentScreen = screen;
            pages = 9;
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            int descHeight = Minecraft.getInstance().font.wordWrapHeight(description,175);

            switch (pageNumber){
                case 1:
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.translatable(name),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal(description)),parentScreen.width/3,parentScreen.height/3,175,DyeColor.GRAY.getTextColor());
                    RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
                            new ItemStack(Registration.MAGE_STAFF_ITEM.get()),pPoseStack,(parentScreen.width/3) ,(parentScreen.height/4));
//                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal("Slingshot: ")
//                            .append(Component.literal("Will Slingshot you in the direction you're aiming").withStyle(ChatFormatting.DARK_AQUA))),
//                            parentScreen.width/2,parentScreen.height/3 + descHeight + Minecraft.getInstance().font.lineHeight,175, DyeColor.WHITE.getTextColor());

                    break;
                case 2:
                    parentScreen.renderTexture(pPoseStack, parentScreen.width/4, parentScreen.height/16,256,256,new ResourceLocation("bedres", "textures/effect/alpha_rune.png"));
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.literal("Slingshot: "),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal("Will Slingshot you in the direction you're aiming")),
                            parentScreen.width/3,parentScreen.height/3,175, DyeColor.GRAY.getTextColor());
                    break;
                case 3:
                    parentScreen.renderTexture(pPoseStack, parentScreen.width/4, parentScreen.height/16,256,256,new ResourceLocation("bedres", "textures/effect/beta_rune.png"));
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.literal("Heaven Smite: "),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal("Will strike your enemies from the heavens")),
                            parentScreen.width/3,parentScreen.height/3,
                            175, DyeColor.GRAY.getTextColor());
                    break;
                case 4:
                    parentScreen.renderTexture(pPoseStack, parentScreen.width/4, parentScreen.height/16,256,256,new ResourceLocation("bedres", "textures/effect/delta_rune.png"));
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.literal("Clear Effects/Regen: "),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal("Will clear effects and give you regen")),
                            parentScreen.width/3,parentScreen.height/3,
                            175, DyeColor.GRAY.getTextColor());
                    break;
                case 5:
                    parentScreen.renderTexture(pPoseStack, parentScreen.width/4, parentScreen.height/16,256,256,new ResourceLocation("bedres", "textures/effect/epsilon_rune.png"));
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.literal("Life Steal: "),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal("Will take health from the enemy to heal and saturate you")),
                            parentScreen.width/3,parentScreen.height/3,
                            175, DyeColor.GRAY.getTextColor());
                    break;
                case 6:
                    parentScreen.renderTexture(pPoseStack, parentScreen.width/4, parentScreen.height/16,256,256,new ResourceLocation("bedres", "textures/effect/eta_rune.png"));
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.literal("TBD: "),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal("TBD")),
                            parentScreen.width/3,parentScreen.height/3,
                            175, DyeColor.GRAY.getTextColor());
                    break;
                case 7:
                    parentScreen.renderTexture(pPoseStack, parentScreen.width/4, parentScreen.height/16,256,256,new ResourceLocation("bedres", "textures/effect/gama_rune.png"));
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.literal("Poison: "),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal("Will incrementally make the enemy most near you to get a stronger poison")),
                            parentScreen.width/3,parentScreen.height/3,
                            175, DyeColor.GRAY.getTextColor());
                    break;
                case 8:
                    parentScreen.renderTexture(pPoseStack, parentScreen.width/4, parentScreen.height/16,256,256,new ResourceLocation("bedres", "textures/effect/theta_rune.png"));
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.literal("Green Thumb: "),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal("Right click will simulate bonemeal the block selected and Shift RC will force random ticks on a 3x3 area where youre aiming ")),
                            parentScreen.width/3,parentScreen.height/3,
                            175, DyeColor.GRAY.getTextColor());
                    break;
                case 9:
                    parentScreen.renderTexture(pPoseStack, parentScreen.width/4, parentScreen.height/16,256,256,new ResourceLocation("bedres", "textures/effect/zeta_rune.png"));
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.literal("Light Projectile: "),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal("Will shoot out a projectile that will create a light block on collision with block or light entity on fire.")),
                            parentScreen.width/3,parentScreen.height/3,
                            175, DyeColor.GRAY.getTextColor());
                    break;
            }
        }
    }
    public static class NebulaPage extends FluxedOraclePages{
        public NebulaPage(FluxOracleScreenGui screen){
            this.name = "bedres.page_name.nebula";
            this.description = "Legend tells of a creature, a creature like no other, stronger than any dragon and any wither." +
                    " Capable of launching fireballs, of shooting you with projectiles with many effects, and that can teleport around, a god, a deity if you will." +
                    " This creature is foretold to only appears to those consumed by flux, consumed to the very core. Others believe this creature is actually a spore creature that when enough of it is inside your body can split from you and take a mind of its own, after which it tries to get rid of its late host. ";
            this.parentScreen = screen;
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            switch (pageNumber){
                case 1:
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.translatable(name),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal(description)),parentScreen.width/3,parentScreen.height/3,175,DyeColor.GRAY.getTextColor());
                    RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
                            new ItemStack(Registration.NEBULA_HEART_ITEM.get()),pPoseStack,(parentScreen.width/3) ,(parentScreen.height/4));
                    break;
            }
        }
    }
    public static class CreeperPage extends FluxedOraclePages{
        public CreeperPage(FluxOracleScreenGui screen){
            this.name = "bedres.page_name.creeper";
            this.description = "These Tiny Creepers con in all sorts of color based on biome." +
                    " You can tame and heal them with gunnpowder, bedrock scrapes, and TNT." +
                    " On death they will spawn a charm that can be used to re summon them." +
                    " Taming them will give them a backpack you can dye any color." +
                    " They will attack any mob they see or player that attacks them. Inventory is WIP";
            this.parentScreen = screen;
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            switch (pageNumber){
                case 1:
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.translatable(name),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal(description)),parentScreen.width/3,parentScreen.height/3,175,DyeColor.GRAY.getTextColor());
//                    RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
//                            new ItemStack(Registration.TRECKING_CREEPER_EGG_ITEM.get()),pPoseStack,(parentScreen.width/3) ,(parentScreen.height/4));
                    break;
            }
        }
    }
    public static class HextPage extends FluxedOraclePages{
        public HextPage(FluxOracleScreenGui screen){
            this.name = "bedres.page_name.hex";
            this.description = "HexTiles are decoration Tiles that can change color with the right click of a dye. If you hold the same dye in both hands color will spread to adjacent hextiles.";
            this.parentScreen = screen;
        }

        @Override
        public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
            switch (pageNumber){
                case 1:
                    parentScreen.renderString(parentScreen.width/2,parentScreen.height/4,Component.translatable(name),pPoseStack,pPartialTick,true, DyeColor.WHITE.getTextColor());
                    Minecraft.getInstance().font.drawWordWrap(FormattedText.composite(Component.literal(description)),parentScreen.width/3,parentScreen.height/3,175,DyeColor.GRAY.getTextColor());
                    RenderHelper.renderGUIItemStack(Minecraft.getInstance().getItemRenderer(),
                            new ItemStack(Registration.HEXTILE_ITEM.get()),pPoseStack,(parentScreen.width/3) ,(parentScreen.height/4));
                    break;
            }
        }
    }
}
