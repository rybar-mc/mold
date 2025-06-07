package org.rybar.mold;

import org.jetbrains.annotations.NotNull;
import org.rybar.mold.gui.Gui;
import org.rybar.mold.renderer.MoldRenderer;

public final class Mold {
    private static MoldRenderer<?> RENDERER;
    private Mold() {}

    public static @NotNull Gui.Builder gui() {
        return new Gui.Builder();
    }

    public static <P> void renderer(@NotNull MoldRenderer<P> renderer) {
        RENDERER = renderer;
    }

    @SuppressWarnings("unchecked")
    public static <P> void show(@NotNull P player, @NotNull Gui gui) {
        ((MoldRenderer<P>) RENDERER).display(player, gui);
    }
}