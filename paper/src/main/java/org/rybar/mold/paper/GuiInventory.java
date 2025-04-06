package org.rybar.mold.paper;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemLore;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.rybar.mold.component.ButtonComponent;
import org.rybar.mold.component.LabelComponent;
import org.rybar.mold.component.MoldComponent;
import org.rybar.mold.gui.Gui;

public class GuiInventory implements InventoryHolder {
    private final @NotNull Gui gui;
    private final @NotNull Inventory inventory;

    public GuiInventory(final @NotNull Player player, final @NotNull Gui gui) {
        this.gui = gui;

        this.inventory = Bukkit.createInventory(this, gui.layout().java().rows(), gui.title());

        gui.layout().java().components().forEach(this::render);
    }

    @SuppressWarnings("UnstableApiUsage")
    private void render(final int index, final @NotNull MoldComponent component) {
        switch (component) {
            case LabelComponent label -> {
                ItemStack stack = ItemStack.of(Material.ACACIA_BOAT); // TODO: Use a proper item
                stack.setData(DataComponentTypes.LORE, ItemLore.lore()
                        .addLine(Component.text(label.text()))
                        .build());

                inventory.setItem(index, stack);
            }
            case ButtonComponent button -> {
                ItemStack stack = ItemStack.of(Material.GREEN_CONCRETE); // TODO: Use a proper item
                stack.setData(DataComponentTypes.LORE, ItemLore.lore()
                        .addLine(Component.text(button.text()))
                        .build());

                inventory.setItem(index, stack);
            }
            default -> throw new IllegalStateException("Invalid component type: " + component.getClass().getName());
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
