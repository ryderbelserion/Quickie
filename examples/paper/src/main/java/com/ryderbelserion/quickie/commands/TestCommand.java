package com.ryderbelserion.quickie.commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.quckie.PaperCommandManager;
import com.ryderbelserion.quckie.enums.Mode;
import com.ryderbelserion.quckie.objects.Command;
import com.ryderbelserion.quckie.objects.PaperCommand;
import com.ryderbelserion.quckie.objects.context.PaperCommandContext;
import com.ryderbelserion.quickie.QuickiePlugin;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;

public class TestCommand extends PaperCommand {

    private final QuickiePlugin plugin = JavaPlugin.getPlugin(QuickiePlugin.class);

    private final PaperCommandManager manager = this.plugin.getManager();

    @Override
    public String[] permissions() {
        return new String[]{"quickie.use", "quickie.test"};
    }

    @Override
    public void execute(final PaperCommandContext context) {
        final CommandSourceStack stack = context.getSource();

        final CommandSender sender = stack.getSender();

        sender.sendRichMessage("<red>This is a test command.");
    }

    @Override
    public boolean requirement(final CommandSourceStack stack) {
        return this.manager.hasPermission(stack, getMode(), permissions());
    }

    @Override
    public LiteralCommandNode<CommandSourceStack> literal() {
        return Commands.literal("quickie").requires(this::requirement).executes(context -> {
            execute(new PaperCommandContext(context));

            return -1;
        }).build();
    }

    @Override
    public LiteralCommandNode<CommandSourceStack> build() {
        this.manager.registerPermissions(PermissionDefault.OP, permissions());

        return literal().createBuilder().build();
    }

    @Override
    public PaperCommand delete() {
        this.manager.unregisterPermissions(permissions());

        return this;
    }

    @Override
    public final Mode getMode() {
        return Mode.ALL_OF;
    }
}