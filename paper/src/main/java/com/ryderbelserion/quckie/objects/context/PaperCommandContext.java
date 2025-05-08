package com.ryderbelserion.quckie.objects.context;

import com.ryderbelserion.quckie.exceptions.QuickieException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PaperCommandContext extends CommandContext<CommandSourceStack> {

    public PaperCommandContext(com.mojang.brigadier.context.CommandContext<CommandSourceStack> parent) {
        super(parent);
    }

    public final CommandSender getCommandSender() {
        return getSource().getSender();
    }

    public final Player getPlayer() {
        if (!isPlayer()) {
            throw new QuickieException("The sender is not a player.");
        }

        return (Player) getCommandSender();
    }

    public final boolean isPlayer() {
        return getCommandSender() instanceof Player;
    }
}