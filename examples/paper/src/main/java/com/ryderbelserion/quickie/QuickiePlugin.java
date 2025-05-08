package com.ryderbelserion.quickie;

import com.ryderbelserion.quckie.PaperCommandManager;
import com.ryderbelserion.quckie.objects.Command;
import com.ryderbelserion.quickie.commands.TestCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.List;

public class QuickiePlugin extends JavaPlugin {

    private PaperCommandManager manager;

    private final List<Command<CommandSourceStack>> commands = new ArrayList<>();

    @Override
    public void onEnable() {
        this.manager = new PaperCommandManager(this);
        this.manager.enable(new TestCommand().build(), new ArrayList<>());
    }

    @Override
    public void onDisable() {
        this.commands.forEach(Command::delete); // clean up our shit
    }

    public final PaperCommandManager getManager() {
        return this.manager;
    }
}