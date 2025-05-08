package com.ryderbelserion.quckie.annotations;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class AnnotationManager {

    private final Plugin plugin;

    private final ComponentLogger logger;

    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    private final LifecycleEventManager<@NotNull Plugin> lifecycle;

    public AnnotationManager(final Plugin plugin) {
        this.plugin = plugin;
        this.logger = this.plugin.getComponentLogger();
        this.lifecycle = this.plugin.getLifecycleManager();
    };

    public void addCommand(final Object object) {
        final Class<?> instance = object.getClass();

        final Command command = instance.getAnnotation(Command.class);

        if (command == null) return;

        final String name = command.value();

        final LiteralCommandNode<CommandSourceStack> node = Commands.literal(name).build();

        addMethods(instance.getDeclaredMethods()).forEach(node::addChild);

        addNodes(node, instance.getDeclaredClasses());

        this.lifecycle.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            event.registrar().register(node);
        });
    }

    private void addNodes(final LiteralCommandNode<CommandSourceStack> root, final Class<?>[] instances) {
        final Iterator<Class<?>> iterator = Arrays.asList(instances).iterator();

        while (iterator.hasNext()) {
            final Class<?> instance = iterator.next();

            if (!instance.isAnnotationPresent(Command.class)) continue;

            final Command command = instance.getAnnotation(Command.class);

            if (command == null) continue;

            final String name = command.value();

            //this.logger.warn(this.miniMessage.deserialize("<red>Method Command: {}, <green>Class Path: {}"), name, instance.getSimpleName());

            final LiteralCommandNode<CommandSourceStack> node = Commands.literal(name).build();

            addMethods(instance.getDeclaredMethods()).forEach(node::addChild);

            root.addChild(node);

            if (!iterator.hasNext()) {
                addNodes(node, instance.getDeclaredClasses());
            }
        }
    }

    private List<LiteralCommandNode<CommandSourceStack>> addMethods(final Method[] methods) {
        final List<LiteralCommandNode<CommandSourceStack>> nodes = new ArrayList<>();

        for (final Method method : methods) {
            //if (!Modifier.isPublic(method.getModifiers())) continue;

            if (!method.isAnnotationPresent(Command.class)) continue;

            final Command command = method.getAnnotation(Command.class);

            if (command == null) continue;

            final String name = command.value();

            //this.logger.warn(this.miniMessage.deserialize("<yellow>Method Command: {}, <light_purple>Class Path: {}"), name, method.getDeclaringClass().getSimpleName());

            final LiteralCommandNode<CommandSourceStack> node = Commands.literal(name).executes(context -> {
                try {
                    final Constructor<?> declared = method.getDeclaringClass().getDeclaredConstructor();

                    declared.setAccessible(true);

                    method.invoke(declared.newInstance());
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }

                return com.mojang.brigadier.Command.SINGLE_SUCCESS;
            }).build();

            nodes.add(node);
        }

        return nodes;
    }
}