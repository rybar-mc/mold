package org.rybar.mold.paper;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.geysermc.floodgate.api.FloodgateApi;
import org.jetbrains.annotations.NotNull;
import org.rybar.mold.gui.Gui;
import org.rybar.mold.renderer.MoldRenderer;

public class PaperMoldRenderer implements MoldRenderer<Player> {
    public PaperMoldRenderer(final @NotNull JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new InventoryListener(), plugin);
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
