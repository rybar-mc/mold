package org.rybar.mold.layout;

import org.rybar.mold.component.MoldComponent;

import java.util.ArrayList;
import java.util.List;

public record BedrockLayout(List<MoldComponent> components) {
    public static class Builder {
        private List<MoldComponent> components = new ArrayList<>();

        public Builder component(final MoldComponent component) {
            this.components.add(component);
            return this;
        }

        public Builder components(final List<MoldComponent> components) {
            this.components = components;
            return this;
        }

        public BedrockLayout build() {
            return new BedrockLayout(components);
        }
    }
}
