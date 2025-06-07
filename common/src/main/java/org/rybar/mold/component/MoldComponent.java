package org.rybar.mold.component;

import org.jetbrains.annotations.NotNull;

public interface MoldComponent {
    @NotNull String id();

    interface Builder<T extends MoldComponent, B extends Builder<T, B>> {
        @NotNull T build();
        @NotNull B id(String id);
    }
}
