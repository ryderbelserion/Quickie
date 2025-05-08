package com.ryderbelserion.quckie;

import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.quckie.annotations.AnnotationManager;
import com.ryderbelserion.quckie.enums.Mode;
import com.ryderbelserion.quckie.objects.Command;
import com.ryderbelserion.quckie.objects.context.PaperCommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PaperCommandManager extends CommandManager<CommandSourceStack, PermissionDefault> {

    private final List<Command<CommandSourceStack, PaperCommandContext>> commands = new ArrayList<>();

    private final Plugin plugin;
    private final PluginManager pluginManager;
    private final LifecycleEventManager<@NotNull Plugin> lifecycle;

    private final AnnotationManager annotationManager;

    public PaperCommandManager(final Plugin plugin) {
        this.plugin = plugin;

        final Server server = this.plugin.getServer();

        this.pluginManager = server.getPluginManager();

        this.lifecycle = this.plugin.getLifecycleManager();

        this.annotationManager = new AnnotationManager(this.plugin);
    }

    @Override
    public boolean hasPermission(final CommandSourceStack stack, final Mode mode, final String[] permissions) {
        final CommandSender sender = stack.getSender();

        boolean hasPermission = !(sender instanceof Player) || sender.isOp();

        if (hasPermission) {
            return true;
        }

        final Iterator<String> iterator = Arrays.stream(permissions).iterator();

        while (iterator.hasNext()) {
            final String permission = iterator.next();

            if (mode == Mode.ANY_OF) {
                hasPermission = sender.hasPermission(permission);

                break;
            }

            hasPermission = sender.hasPermission(permission);

            if (!hasPermission) {
                break;
            }
        }

        return hasPermission;
    }

    @Override
    public void registerPermissions(final PermissionDefault permissionDefault, final String[] permissions) {
        final Iterator<String> iterator = Arrays.stream(permissions).iterator();

        while (iterator.hasNext()) {
            final String node = iterator.next();

            final Permission permission = this.pluginManager.getPermission(node);

            if (permission != null) {
                continue;
            }

            this.pluginManager.addPermission(new Permission(node, permissionDefault));
        }
    }

    @Override
    public void unregisterPermissions(final String[] permissions) {
        final Iterator<String> iterator = Arrays.stream(permissions).iterator();

        while (iterator.hasNext()) {
            final String node = iterator.next();

            final Permission permission = this.pluginManager.getPermission(node);

            if (permission != null) {
                this.pluginManager.removePermission(permission);
            }
        }
    }

    @Override
    public void enable(final LiteralCommandNode<CommandSourceStack> root, final List<LiteralCommandNode<CommandSourceStack>> children) {
        this.lifecycle.registerEventHandler(LifecycleEvents.COMMANDS, command -> {
            final Commands registry = command.registrar();

            children.forEach(root::addChild);

            registry.register(root);
        });
    }

    /**
     * DO NOT USE
     */
    @ApiStatus.Internal
    @ApiStatus.Experimental()
    private AnnotationManager getAnnotationManager() {
        return this.annotationManager;
    }
}