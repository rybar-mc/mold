package org.rybar.mold.component;

import org.jetbrains.annotations.NotNull;
import java.util.Optional;

public record ToggleComponent(
        String id,
        String text,
        boolean defaultValue,
        Optional<String> iconId
) implements MoldComponent {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements MoldComponent.Builder<ToggleComponent, Builder> {
        private String id;
        private String text;
        private boolean defaultValue;
        private String iconId;

        @Override
        public @NotNull Builder id(String id) {
            this.id = id;
            return this;
        }

        public @NotNull Builder text(String text) {
            this.text = text;
            return this;
        }

        public @NotNull Builder defaultValue(boolean value) {
            this.defaultValue = value;
            return this;
        }

        public @NotNull Builder icon(String iconId) {
            this.iconId = iconId;
            return this;
        }

        @Override
        public @NotNull ToggleComponent build() {
            return new ToggleComponent(id, text, defaultValue, Optional.ofNullable(iconId));
        }
    }
}