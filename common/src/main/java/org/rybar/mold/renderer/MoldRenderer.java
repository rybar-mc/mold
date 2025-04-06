package org.rybar.mold.renderer;

import org.jetbrains.annotations.NotNull;
import org.rybar.mold.gui.Gui;

/**
 * Interface implemented by platform-specific modules to render a {@link Gui}
 * as a native user interface for a given player and manage the interaction lifecycle.
 *
 * @param <P> The platform's specific Player object type.
 */
@FunctionalInterface
public interface MoldRenderer<P> {
    /**
     * Displays the user interface described by the given {@link Gui}
     * to the specified player.
     *
     * @param player The non-null platform-specific player object who should see the UI.
     * @param gui The non-null, platform-agnostic GUI definition to be displayed.
     */
    void display(final @NotNull P player, final @NotNull Gui gui);
}
