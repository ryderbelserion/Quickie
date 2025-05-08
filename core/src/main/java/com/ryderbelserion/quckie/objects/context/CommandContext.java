package com.ryderbelserion.quckie.objects.context;

public abstract class CommandContext<S> {

    private final com.mojang.brigadier.context.CommandContext<S> parent;

    public CommandContext(final com.mojang.brigadier.context.CommandContext<S> parent) {
        this.parent = parent;
    }

    public com.mojang.brigadier.context.CommandContext<S> getParent() {
        return this.parent;
    }

    public final S getSource() {
        return getParent().getSource();
    }
}