package com.ryderbelserion.quckie.objects;

import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.quckie.enums.Mode;

public abstract class Command<Sender> {

    public abstract void execute(final Sender sender);

    public abstract boolean requirement(final Sender sender);

    public abstract LiteralCommandNode<Sender> literal();

    public abstract Command<Sender> build();

    public abstract Command<Sender> delete();

    public Mode getMode() {
        return Mode.ANY_OF;
    }

    public String[] permissions() {
        return null;
    }
}