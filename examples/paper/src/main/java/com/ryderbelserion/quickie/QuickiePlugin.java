package com.ryderbelserion.quickie;

import com.ryderbelserion.quckie.PaperCommandManager;
import com.ryderbelserion.quickie.commands.TestCommand;
import com.ryderbelserion.quickie.commands.annotations.ExampleCommand;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;

public class QuickiePlugin extends JavaPlugin {

    private PaperCommandManager manager;

    @Override
    public void onEnable() {
        this.manager = new PaperCommandManager(this);
        this.manager.enable(new TestCommand().build(), new ArrayList<>());

        this.manager.getAnnotationManager().addCommand(new ExampleCommand());
    }

    @Override
    public void onDisable() {
        this.manager.disable();
    }

    public final PaperCommandManager getManager() {
        return this.manager;
    }
}