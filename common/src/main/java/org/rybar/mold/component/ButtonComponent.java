package org.rybar.mold.component;

import org.jetbrains.annotations.NotNull;
import org.rybar.mold.callback.MoldCallback;

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
        Optional<String> iconId,
        Optional<MoldCallback> callback
) implements MoldComponent {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements MoldComponent.Builder<ButtonComponent, Builder> {
        private String id;
        private String text;
        private String iconId;
        private MoldCallback callback;

        @Override
        public @NotNull Builder id(String id) {
            this.id = id;
            return this;
        }

        public @NotNull Builder text(String text) {
            this.text = text;
            return this;
        }

        public @NotNull Builder icon(String iconId) {
            this.iconId = iconId;
            return this;
        }

        public @NotNull Builder onClick(MoldCallback callback) {
            this.callback = callback;
            return this;
        }

        @Override
        public @NotNull ButtonComponent build() {
            return new ButtonComponent(
                    id,
                    text,
                    Optional.ofNullable(iconId),
                    Optional.ofNullable(callback)
            );
        }
    }
}
