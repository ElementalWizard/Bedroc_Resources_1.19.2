package com.alexvr.bedres.blocks.scrapeTank;

import com.alexvr.bedres.utils.BedrockReferences;
import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class ScrapeTank extends Block implements EntityBlock {

    public ScrapeTank(Properties props) {
        super(props);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter reader, List<Component> list, TooltipFlag flags) {
        list.add(Component.translatable(BedrockReferences.SCRAPE_TANK_TOOLTIP));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return null;
        }
        return (level1, blockPos, blockState, t) -> {
            if (t instanceof ScrapeTankTile tile) {
                tile.tickServer();
            }
        };
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
        if (!world.isClientSide) {
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof ScrapeTankTile) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return Component.translatable(BedrockReferences.SCREEN_SCRAPE_TANK);
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
                        return new ScrapeTankMenu(i, world, pos, playerInventory, playerEntity);
                    }
                };
                NetworkHooks.openScreen((ServerPlayer) player, containerProvider, tileEntity.getBlockPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }
    private static final Vec3 COLORS = new Vec3(.1, .2, .1);

    @Override
    public void animateTick(BlockState p_49888_, Level p_49889_, BlockPos p_49890_, RandomSource p_49891_) {
        int speed = 4;
        p_49889_.getBlockEntity(p_49890_).getCapability( ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
            for (int i = 0; i < h.getStackInSlot(0).getCount(); i++) {
                p_49889_.addParticle(new DustParticleOptions(new Vector3f(COLORS), 1.0F), (double)p_49890_.getX() + + p_49891_.nextDouble(), (double)p_49890_.getY() + p_49891_.nextDouble(), (double)p_49890_.getZ() + + p_49891_.nextDouble(),p_49891_.nextDouble() * speed , p_49891_.nextDouble()* speed, p_49891_.nextDouble()* speed);
            }
        });
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ScrapeTankTile(pPos, pState);
    }
}
