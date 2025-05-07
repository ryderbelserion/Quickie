package com.ryderbelserion.quickie;

import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.quckie.PaperCommandManager;
import com.ryderbelserion.quckie.objects.Command;
import com.ryderbelserion.quickie.commands.TestCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.List;

public class QuickiePlugin extends JavaPlugin {

    private PaperCommandManager manager;

    private final List<Command<CommandSourceStack>> commands = new ArrayList<>();

    @Override
    public void onEnable() {
        this.manager = new PaperCommandManager(this);

        final TestCommand command = new TestCommand();

        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, registry -> {
            final Commands access = registry.registrar();

            final LiteralCommandNode<CommandSourceStack> root = command.build().literal().createBuilder().build();

            this.commands.add(command);

            access.register(root);
        });
    }

    @Override
    public void onDisable() {
        this.commands.forEach(Command::delete); // clean up our shit
    }

    public final PaperCommandManager getManager() {
        return this.manager;
    }
}