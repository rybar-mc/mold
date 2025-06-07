package org.rybar.mold.layout;

import org.jetbrains.annotations.NotNull;
import org.rybar.mold.gui.FormType;

public record Layout(JavaLayout java, BedrockLayout bedrock, FormType defaultBedrockFormType) {
    public static class Builder {
        private JavaLayout.Builder java = new JavaLayout.Builder();
        private BedrockLayout.Builder bedrock = new BedrockLayout.Builder();
        private FormType bedrockFormType = FormType.ACTION;

        public @NotNull JavaLayout.Builder java() {
            return this.java;
        }

        public @NotNull BedrockLayout.Builder bedrock() {
            return this.bedrock;
        }

        public @NotNull Builder bedrockFormType(FormType type) {
            this.bedrockFormType = type;
            return this;
        }

        public @NotNull Layout build() {
            return new Layout(this.java.build(), this.bedrock.build(), bedrockFormType);
        }
    }
}