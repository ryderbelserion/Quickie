package com.ryderbelserion.quckie;

import com.ryderbelserion.quckie.enums.Mode;

public abstract class CommandManager<S, D> {

    public abstract boolean hasPermission(final S stack, final Mode mode, final String[] permissions);

    public abstract void registerPermissions(final D permission, final String[] permissions);

    public abstract void unregisterPermissions(final String[] permissions);

}