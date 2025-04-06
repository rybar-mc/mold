package org.rybar.mold.paper;

import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.ModalForm;
import org.geysermc.floodgate.api.FloodgateApi;
import org.jetbrains.annotations.NotNull;
import org.rybar.mold.component.ButtonComponent;
import org.rybar.mold.gui.Gui;

public class GuiForm {
    private final @NotNull Gui gui;

    public GuiForm(final @NotNull Player player, final @NotNull Gui gui) {
        this.gui = gui;

        // TODO: make properly
        var form = ModalForm.builder()
                .title(gui.title())
                .content("test")
                .button1(((ButtonComponent)gui.layout().bedrock().components().get(0)).text())
                .button2(((ButtonComponent)gui.layout().bedrock().components().get(1)).text())
                .build();

        FloodgateApi.getInstance().sendForm(player.getUniqueId(), form);
    }
}
