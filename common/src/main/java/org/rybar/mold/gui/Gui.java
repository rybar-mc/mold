package org.rybar.mold.gui;

import org.jetbrains.annotations.NotNull;
import org.rybar.mold.component.ButtonComponent;
import org.rybar.mold.component.LabelComponent;
import org.rybar.mold.component.MoldComponent;
import org.rybar.mold.layout.BedrockLayout;
import org.rybar.mold.layout.JavaLayout;
import org.rybar.mold.layout.Layout;

import java.util.function.Consumer;

public record Gui(String title, Layout layout) {
    public Gui(final @NotNull String title, final @NotNull Layout layout) {
        this.title = title;
        this.layout = layout;
    }

    public static class Builder {
        private String title;
        private final Layout.Builder layout = new Layout.Builder();

        public Builder title(final @NotNull String title) {
            this.title = title;
            return this;
        }

        public Builder label(final @NotNull LabelComponent component, int slotOnJava) {
            return this.component(component, slotOnJava);
        }

        public Builder button(final @NotNull ButtonComponent component, int slotOnJava) {
            return this.component(component, slotOnJava);
        }

        public <T extends MoldComponent> Builder component(final @NotNull T component, int slotOnJava) {
            this.layout.java().component(slotOnJava, component);
            this.layout.bedrock().component(component);

            return this;
        }

        public Builder java(final @NotNull Consumer<JavaLayout.Builder> configurer) {
            configurer.accept(this.layout.java());

            return this;
        }

        public Builder bedrock(final @NotNull Consumer<BedrockLayout.Builder> configurer) {
            configurer.accept(this.layout.bedrock());

            return this;
        }

        public Gui build() {
            return new Gui(this.title, this.layout.build());
        }
    }
}
