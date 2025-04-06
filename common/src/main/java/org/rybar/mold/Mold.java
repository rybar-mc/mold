package org.rybar.mold;

import org.jetbrains.annotations.NotNull;
import org.rybar.mold.gui.Gui;

public final class Mold {
    public static @NotNull Gui.Builder gui() {
        return new Gui.Builder();
    }
}
