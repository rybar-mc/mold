package org.rybar.mold.test.paper;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.rybar.mold.Mold;
import org.rybar.mold.component.ButtonComponent;
import org.rybar.mold.paper.PaperMoldRenderer;

import java.util.Optional;

public class PaperMoldTestPlugin extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        System.out.println("PaperMoldTestPlugin enabled");

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
        if (event.getBlock().getType() == Material.DIAMOND_BLOCK) {
            PaperMoldRenderer.instance().display(
                    event.getPlayer(),
                    Mold.gui()
                            .title("hello :)")
                            .java(configurer -> configurer.rows(9))
                            .component(
                                    new ButtonComponent("1", "Click me!", Optional.empty()),
                                    1
                            )
                            .component(
                                    new ButtonComponent("2", "Click me!", Optional.empty()),
                                    2
                            )
                            .build()
            );
        }
    }
}
