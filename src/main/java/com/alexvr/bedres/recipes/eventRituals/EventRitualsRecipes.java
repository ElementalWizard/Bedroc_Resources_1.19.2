package com.alexvr.bedres.recipes.eventRituals;

public class EventRitualsRecipes /*implements Recipe<EventRitualsContext>*/ {

/*    private final ResourceLocation id;
    private final ItemStack destination;
    private final String event;
    private final List<ItemStack> ingredients;
    private final List<String> pattern; // w for wire, i for item
    public static int patternRadius = 2; // pattern size is (patternRadius*2) + 1

    public EventRitualsRecipes(ItemStack destination,String event, List<String> pattern, ItemStack... ingredients) {
        this.id = ForgeRegistries.ITEMS.getKey(destination.getItem());
        this.event = event;
        this.destination = destination;
        this.ingredients = new ArrayList<>(ingredients.length);
        this.pattern = pattern;
        Collections.addAll(this.ingredients, ingredients);
    }

    public static List<ItemStack> getItemsFromTiles(List<EnderianRitualPedestalTile> tiles){
        List<ItemStack> items = new ArrayList<>();
        for (EnderianRitualPedestalTile pedestalTile: tiles){
            pedestalTile.getCapability(ForgeCapabilities.ITEM_HANDLER,null).ifPresent(h -> {
                if (!h.getStackInSlot(0).isEmpty()){
                    AtomicBoolean found = new AtomicBoolean(false);
                    items.forEach(item -> {
                        if (!found.get() && item.is(h.getStackInSlot(0).getItem())){
                            item.grow(1);
                            found.set(true);
                        }
                    });
                    if (!found.get()){
                        items.add(h.getStackInSlot(0).copy());
                    }
                }
            });
        }
        return items;
    }

    public static List<ItemStack> getItemsForRecipeFromWordl(Level level, BlockPos position, int xRadius, int zRadius){
        List<ItemStack> items = new ArrayList<>();
        items.clear();
        for (int x = position.getX() - xRadius; x <= position.getX() + xRadius; x++){
            for (int z = position.getZ() - zRadius; z <= position.getZ() + zRadius; z++){
                BlockPos newPosition = new BlockPos(x,position.getY(),z);
                if (level.getBlockEntity(newPosition) instanceof EnderianRitualPedestalTile tower){
                    tower.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(h -> {
                        boolean grew = false;
                        ItemStack stack = h.getStackInSlot(0).copy();
                        for (ItemStack stack2: items) {
                            if (stack.is(stack2.getItem())){
                                stack2.grow(1);
                                grew = true;
                                break;
                            }
                        }
                        if (!grew){
                            items.add(stack) ;
                        }
                    });
                }
            }
        }
        return items;
    }
    public static String getDescription(EventRitualsRecipes recipe) {
        switch (recipe.getEvent()) {
            case "world" -> {
                return WorldEvent.getDescriptions(recipe.getResultItem( Minecraft.getInstance().level.registryAccess()));
            }
            case "player_upgrade" -> {
                return PlayerUpgradeEvent.getDescriptions(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
            }
            case "tool" -> {
                return  ToolUpgradeEvent.getDescriptions(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
            }
            default -> {
                return "";
            }
        }
    }
    public static List<String> getPatterForRecipeFromWorld(Level level, BlockPos position, int xRadius, int zRadius){
        List<String> patter = new ArrayList<>((zRadius*2)+1);
        for (int z = position.getZ() - zRadius; z <= position.getZ() + zRadius; z++){
            StringBuilder row = new StringBuilder();
            for (int x = position.getX() - xRadius; x <= position.getX() + xRadius; x++){
                BlockPos newPosition = new BlockPos(x,position.getY(),z);
                if (level.getBlockEntity(newPosition) instanceof EnderianRitualPedestalTile){
                    row.append('i');
                }else if (level.getBlockState(newPosition).is(Registration.BEDROCK_WIRE_BLOCK.get())){
                    row.append('w');
                }else{
                    row.append(' ');
                }
            }
            patter.add(row.toString());
        }
        return patter;
    }

    public static List<EnderianRitualPedestalTile> getTilesForRecipeFromWorld(Level level, BlockPos position, int xRadius, int zRadius){
        List<EnderianRitualPedestalTile> tiles = new ArrayList<>();
        for (int z = position.getZ() - zRadius; z <= position.getZ() + zRadius; z++){
            for (int x = position.getX() - xRadius; x <= position.getX() + xRadius; x++){
                BlockPos newPosition = new BlockPos(x,position.getY(),z);
                if (level.getBlockEntity(newPosition) instanceof EnderianRitualPedestalTile pedestalTile){
                    tiles.add(pedestalTile);
                }
            }
        }
        return tiles;
    }
    public static EventRitualsRecipes findRecipeFromOutput(ItemStack destination) {
        for (EventRitualsRecipes recipe : ModRecipeRegistry.getEventRitualRecipes()) {
            if (ItemHandlerHelper.canItemStacksStack(recipe.getDestination(), destination)) {
                return recipe;
            }
        }
        return null;
    }
    public static List<EventRitualsRecipes> findRecipeFromPattern(List<String> pat) {
        List<EventRitualsRecipes> validRecipes = new ArrayList<>();
        for (EventRitualsRecipes recipe : ModRecipeRegistry.getEventRitualRecipes()) {
            boolean validRec = true;
            if (recipe.getPattern().size() != pat.size()){
                continue;
            }
            for (int i = 0;i< recipe.getPattern().size();i++){
               if (!recipe.getPattern().get(i).equals(pat.get(i))){
                   validRec = false;
                   break;
               }
            }
            if (validRec){
                validRecipes.add(recipe);
            }

        }
        return validRecipes;
    }

    public static EventRitualsRecipes findRecipeFromIngrent(List<ItemStack> ing) {
        for (EventRitualsRecipes recipe : ModRecipeRegistry.getEventRitualRecipes()) {
            boolean validRec = true;
            if (ing.size() != recipe.getIngredientList().size()){
                continue;
            }
            List<ItemStack> ingCopy = new ArrayList<>(ing);

            for (ItemStack stack: recipe.getIngredientList()){
                boolean valid = false;
                for (ItemStack stack2: ing){
                    if (stack2.is(stack.getItem()) && stack2.equals(stack,false)){
                        valid = true;
                        ingCopy.remove(stack2);
                        break;
                    }
                }
                if (!valid){
                    validRec = false;
                    break;
                }
            }
            if (validRec && ingCopy.isEmpty()){
                return recipe;
            }

        }
        return null;
    }

    public static EventRitualsRecipes findRecipeFromEvent(String event) {
        for (EventRitualsRecipes recipe : ModRecipeRegistry.getEventRitualRecipes()) {
            if (recipe.getEvent().equals(event)){
                return recipe;
            }
        }
        return null;
    }

    public ItemStack getDestination() {
        return destination;
    }

    public List<ItemStack> getIngredientList() {
        return ingredients;
    }

    public String getEvent(){
        return event;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        getIngredientList().forEach(ing -> {
            nonnulllist.add(Ingredient.of(ing));
        });

        return nonnulllist;
    }

    @Override
    public boolean matches(EventRitualsContext inv, Level worldIn) {
        return false;
    }

    @Override
    public ItemStack assemble(EventRitualsContext pContainer, RegistryAccess pRegistryAccess) {
        return null;
    }


    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return destination;
    }

    public List<String> getPattern() {
        return pattern;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;//ModRecipeRegistry.EVENT_RITUAL_RECIPES.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeRegistry.EVENT_RITUAL;
    }*/
}
