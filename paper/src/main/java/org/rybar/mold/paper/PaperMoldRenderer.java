package org.rybar.mold.paper;

import org.bukkit.entity.Player;
import org.geysermc.floodgate.api.FloodgateApi;
import org.jetbrains.annotations.NotNull;
import org.rybar.mold.gui.Gui;
import org.rybar.mold.renderer.MoldRenderer;

public class PaperMoldRenderer implements MoldRenderer<Player> {
    private static final PaperMoldRenderer instance = new PaperMoldRenderer();

    public static PaperMoldRenderer instance() {
        return instance;
    }

    @Override
    public void display(@NotNull Player player, @NotNull Gui gui) {
        if (FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId())) {
            new GuiForm(player, gui);
        } else {
            player.openInventory(new GuiInventory(player, gui).getInventory());
        }
    }
}
