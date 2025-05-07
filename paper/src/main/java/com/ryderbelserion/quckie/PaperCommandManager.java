package com.ryderbelserion.quckie;

import com.ryderbelserion.quckie.enums.Mode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import java.util.Arrays;
import java.util.Iterator;

public class PaperCommandManager extends CommandManager<CommandSourceStack, PermissionDefault> {

    private final Plugin plugin;
    private final PluginManager pluginManager;

    public PaperCommandManager(final Plugin plugin) {
        this.plugin = plugin;

        final Server server = this.plugin.getServer();

        this.pluginManager = server.getPluginManager();
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
}