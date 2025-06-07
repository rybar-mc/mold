package org.rybar.mold.layout;

import org.jetbrains.annotations.NotNull;
import org.rybar.mold.component.MoldComponent;

import java.util.HashMap;
import java.util.Map;

public record JavaLayout(int rows, Map<Integer, MoldComponent> components) {
    public static class Builder {
        private int rows;
        private Map<Integer, MoldComponent> components;

        public int rows() {
            return this.rows;
        }

        public Builder rows(final int rows) {
            this.rows = rows;
            return this;
        }

        public Builder component(final int slot, final @NotNull MoldComponent component) {
            if (components == null) {
                components = new HashMap<>();
            }

            components.put(slot, component);

            return this;
        }

        public JavaLayout build() {
            return new JavaLayout(rows, components);
        }
    }
}
