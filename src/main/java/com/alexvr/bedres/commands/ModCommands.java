package com.alexvr.bedres.commands;

import com.alexvr.bedres.BedrockResources;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class ModCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> cmdTut = dispatcher.register(
                Commands.literal(BedrockResources.MODID)
                        .then(CommandTPDim.register(dispatcher))
        );


        dispatcher.register(Commands.literal("bedres").redirect(cmdTut));
    }

}
