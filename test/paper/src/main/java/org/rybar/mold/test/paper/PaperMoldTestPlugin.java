package org.rybar.mold.test.paper;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.rybar.mold.Mold;
import org.rybar.mold.component.ToggleComponent;
import org.rybar.mold.gui.FormType;
import org.rybar.mold.gui.Gui;
import org.rybar.mold.paper.PaperMoldRenderer;

import java.util.Optional;

public class PaperMoldTestPlugin extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        System.out.println("PaperMoldTestPlugin enabled");

        Mold.renderer(new PaperMoldRenderer(this));

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        System.out.println("PaperMoldTestPlugin disabled");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().setOp(true);
        event.getPlayer().setGameMode(GameMode.CREATIVE);
    }

    @EventHandler
    public void onChat(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (event.getBlock().getType() == Material.DIAMOND_BLOCK) {
            Gui gui = Mold.gui()
                    .title("Simple Menu")
                    .bedrockFormType(FormType.ACTION) // Use action form on Bedrock
                    .button("btn_help", "Need Help?", 11, result -> {
                        player.sendMessage("Here's how to play...");
                    })
                    .button("btn_stats", "View Stats", 13)
                    .button("btn_settings", "Settings", 15)
                    .java(j -> j.rows(3))
                    .onSubmit(result -> {
                        System.out.println(result);
                        if (result.getClickedButtonId().equals("btn_stats")) {
                            player.sendMessage("Your stats: 10 wins, 5 losses");
                        }

                        if (result.getClickedButtonId().equals("btn_settings")) {
                            showSettingsMenu(player);
                        }
                    })
                    .onClose(result -> {
                        player.sendMessage("Menu closed!");
                    })
                    .build();

            Mold.show(player, gui);
        }
    }

    public void showSettingsMenu(Player player) {
        Gui gui = Mold.gui()
                .title("Settings")
                .bedrockFormType(FormType.CUSTOM)
                .label("lbl_header", "Adjust your settings:", 0)
                .component(new ToggleComponent("toggle_sounds", "Enable Sounds", true, Optional.empty()), 1)
                .component(new ToggleComponent("toggle_particles", "Show Particles", true, Optional.empty()), 2)
                .button("btn_save", "Save Settings", 8)
                .java(j -> j.rows(1))
                .onSubmit(result -> {
                    boolean sounds = result.getValue("toggle_sounds", Boolean.class);
                    boolean particles = result.getValue("toggle_particles", Boolean.class);

                    player.sendMessage("Settings saved! Sounds: " + sounds + ", Particles: " + particles);
                })
                .build();

        Mold.show(player, gui);
    }
}
