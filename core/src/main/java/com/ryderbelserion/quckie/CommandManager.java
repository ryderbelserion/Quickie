package com.ryderbelserion.quckie;

import com.mojang.brigadier.tree.LiteralCommandNode;
import com.ryderbelserion.quckie.enums.Mode;
import java.util.List;

public abstract class CommandManager<S, D> {

    public abstract boolean hasPermission(final S stack, final Mode mode, final String[] permissions);

    public abstract void registerPermissions(final D permission, final String[] permissions);

    public abstract void unregisterPermissions(final String[] permissions);

    public abstract void enable(final LiteralCommandNode<S> root, final List<LiteralCommandNode<S>> children);

}