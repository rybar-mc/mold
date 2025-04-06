package org.rybar.mold.component;

import java.util.Optional;

/**
 * Represents a clickable button component.
 * On Bedrock, maps typically to an ActionForm or MessageForm button.
 * On Java, maps typically to a clickable ItemStack in an inventory.
 *
 * @param id The unique identifier for this button.
 * @param text The text label displayed on the button.
 * @param iconId An optional platform-interpreted icon identifier (e.g., "minecraft:diamond_sword", "textures/items/custom_icon.png").
 */
public record ButtonComponent(
        String id,
        String text,
        Optional<String> iconId
) implements MoldComponent {}
