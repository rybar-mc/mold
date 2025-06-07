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
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.rybar.mold.component.ButtonComponent;
import org.rybar.mold.component.LabelComponent;
import org.rybar.mold.component.MoldComponent;
import org.rybar.mold.component.ToggleComponent;
import org.rybar.mold.gui.Gui;
import org.rybar.mold.result.GuiResult;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GuiInventory implements InventoryHolder {

    private final @NotNull Gui gui;
    private final @NotNull Inventory inventory;
    private final @NotNull UUID playerUuid;
    private final Map<Integer, MoldComponent> slotToComponent = new HashMap<>();
    private final Map<String, Boolean> toggleStates = new HashMap<>();

    public GuiInventory(final @NotNull Player player, final @NotNull Gui gui) {
        this.gui = gui;
        this.playerUuid = player.getUniqueId();

        int size = gui.layout().java().rows() * 9;
        this.inventory = Bukkit.createInventory(this, size, Component.text(gui.title()));

        gui.layout().java().components().forEach((slot, component) -> {
            render(slot, component);
            slotToComponent.put(slot, component);
        });

        player.openInventory(inventory);
    }

    @SuppressWarnings("UnstableApiUsage")
    private void render(final int index, final @NotNull MoldComponent component) {
        if (index < 0 || index >= inventory.getSize()) {
            return; // Invalid slot
        }

        ItemStack stack;
        Component name;

        switch (component) {
            case LabelComponent label -> {
                stack = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
                name = Component.text(label.text());
            }
            case ButtonComponent button -> {
                stack = new ItemStack(Material.GREEN_CONCRETE);
                name = Component.text(button.text());
            }
            case ToggleComponent toggle -> {
                boolean isOn = toggleStates.getOrDefault(toggle.id(), toggle.defaultValue());
                Material material = isOn ? Material.LIME_CONCRETE : Material.RED_CONCRETE;
                stack = new ItemStack(material);
                name = Component.text(toggle.text() + ": " + (isOn ? "ON" : "OFF"));
            }
            default -> {
                stack = new ItemStack(Material.BARRIER); // fallback
                name = Component.text("Unknown component");
            }
        }

        ItemMeta meta = stack.getItemMeta();
        meta.displayName(name);
        stack.setItemMeta(meta);

        stack.setData(DataComponentTypes.LORE, ItemLore.lore()
                .addLine(Component.text("§8§o" + component.id()))
                .build());

        inventory.setItem(index, stack);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public void handleClick(int slot) {
        if (slot < 0 || slot >= inventory.getSize()) {
            return;
        }

        MoldComponent component = slotToComponent.get(slot);
        if (component == null) {
            return;
        }

        if (component instanceof ButtonComponent button) {
            GuiResult result = new GuiResult(false, button.id(),(HashMap)toggleStates);

            button.callback().ifPresent(callback -> callback.accept(result));

            gui.onSubmit().ifPresent(callback -> callback.accept(result));
        } else if (component instanceof ToggleComponent toggle) {
            boolean currentState = toggleStates.getOrDefault(toggle.id(), toggle.defaultValue());
            boolean newState = !currentState;
            toggleStates.put(toggle.id(), newState);

            render(slot, component);

            Map<String, Object> values = new HashMap<>();
            values.put(toggle.id(), newState);
            GuiResult result = new GuiResult(false, null, values);

            gui.onSubmit().ifPresent(callback -> callback.accept(result));
        }
    }

    public void handleClose() {
        gui.onClose().ifPresent(callback -> {
            GuiResult result = new GuiResult(true, null, new HashMap<>());
            callback.accept(result);
        });
    }
}