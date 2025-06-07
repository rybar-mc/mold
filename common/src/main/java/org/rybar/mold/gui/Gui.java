package org.rybar.mold.gui;

import org.jetbrains.annotations.NotNull;
import org.rybar.mold.callback.MoldCallback;
import org.rybar.mold.component.ButtonComponent;
import org.rybar.mold.component.LabelComponent;
import org.rybar.mold.component.MoldComponent;
import org.rybar.mold.layout.BedrockLayout;
import org.rybar.mold.layout.JavaLayout;
import org.rybar.mold.layout.Layout;

import java.util.Optional;
import java.util.function.Consumer;

public record Gui(
        String title,
        Layout layout,
        Optional<MoldCallback> onSubmit,
        Optional<MoldCallback> onClose
) {
    public Gui(
            final @NotNull String title,
            final @NotNull Layout layout,
            final @NotNull Optional<MoldCallback> onSubmit,
            final @NotNull Optional<MoldCallback> onClose
    ) {
        this.title = title;
        this.layout = layout;
        this.onSubmit = onSubmit;
        this.onClose = onClose;
    }

    public static class Builder {
        private String title;
        private final Layout.Builder layout = new Layout.Builder();
        private MoldCallback onSubmit;
        private MoldCallback onClose;

        public Builder title(final @NotNull String title) {
            this.title = title;
            return this;
        }

        public Builder label(final @NotNull LabelComponent component, int slotOnJava) {
            return this.component(component, slotOnJava);
        }

        public Builder label(final @NotNull String id, final @NotNull String text, int slotOnJava) {
            return this.component(new LabelComponent(id, text), slotOnJava);
        }

        public Builder button(final @NotNull ButtonComponent component, int slotOnJava) {
            return this.component(component, slotOnJava);
        }

        public Builder button(final @NotNull String id, final @NotNull String text, int slotOnJava) {
            return this.component(new ButtonComponent(id, text, Optional.empty(), Optional.empty()), slotOnJava);
        }

        public Builder button(final @NotNull String id, final @NotNull String text, int slotOnJava, MoldCallback callback) {
            return this.component(new ButtonComponent(id, text, Optional.empty(), Optional.ofNullable(callback)), slotOnJava);
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

        public Builder bedrockFormType(FormType type) {
            this.layout.bedrockFormType(type);
            return this;
        }

        public Builder onSubmit(MoldCallback callback) {
            this.onSubmit = callback;
            return this;
        }

        public Builder onClose(MoldCallback callback) {
            this.onClose = callback;
            return this;
        }

        public Gui build() {
            if (layout.java().rows() == 0) {
                layout.java().rows(3);
            }

            return new Gui(
                    this.title,
                    this.layout.build(),
                    Optional.ofNullable(this.onSubmit),
                    Optional.ofNullable(this.onClose)
            );
        }
    }
}