package com.ryderbelserion.quckie.objects;

import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.quckie.enums.Mode;
import com.ryderbelserion.quckie.objects.context.CommandContext;

public abstract class Command<S, I extends CommandContext<S>> {

    public abstract void execute(final I sender);

    public abstract boolean requirement(final S sender);

    public abstract LiteralCommandNode<S> literal();

    public abstract LiteralCommandNode<S> build();

    public abstract Command<S, I> delete();

    public Mode getMode() {
        return Mode.ANY_OF;
    }

    public String[] permissions() {
        return null;
    }
}