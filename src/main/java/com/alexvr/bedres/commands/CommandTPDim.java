package com.alexvr.bedres.commands;

import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.world.dimension.ModDimensions;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class CommandTPDim implements Command<CommandSourceStack> {
    private static final CommandTPDim CMD = new CommandTPDim();

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
        return Commands.literal("dim")
                .requires(cs -> cs.hasPermission(0))
                .executes(CMD);
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {

        ServerPlayer player = context.getSource().getPlayerOrException();
        int x = player.blockPosition().getX();
        int y = player.blockPosition().getY()+2;
        int z = player.blockPosition().getZ();
        if (player.getCommandSenderWorld().dimension().equals(ModDimensions.MYSDIM)) {
            ServerLevel world = player.getServer().getLevel(Level.OVERWORLD);
            teleport(player, world, new BlockPos(x, y, z));
        } else {
            ServerLevel world = context.getSource().getLevel().getServer().getLevel(ModDimensions.MYSDIM);
            BlockPos pos2 = world.findNearestMapFeature(Registration.DUNGEON.get(),player.blockPosition(),1024,true).atY(65).east(15);
            for (int i = -2; i < 3; i++) {
                for (int k = -2; k < 3; k++) {
                    assert world != null;
                    world.setBlock(new BlockPos(pos2).offset(i,-1.0,k), Registration.ENDERIAN_BRICK_BLOCK.get().defaultBlockState(),3);
                }
            }
            teleport(player, world, new BlockPos(pos2));
        }
        return 0;
    }

    public static void teleport(ServerPlayer entity, ServerLevel destination, BlockPos pos) {
        entity.changeDimension(destination, new ITeleporter() {
            @Override
            public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                entity = repositionEntity.apply(false);
                entity.teleportTo(pos.getX(), pos.getY(), pos.getZ());
                return entity;
            }
        });
    }
}
