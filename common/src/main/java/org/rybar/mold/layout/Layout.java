package org.rybar.mold.layout;

import org.jetbrains.annotations.NotNull;

public record Layout(JavaLayout java, BedrockLayout bedrock) {
    public static class Builder {
        private JavaLayout.Builder java = new JavaLayout.Builder();
        private BedrockLayout.Builder bedrock = new BedrockLayout.Builder();

        public @NotNull JavaLayout.Builder java() {
            return this.java;
        }

        public @NotNull BedrockLayout.Builder bedrock() {
            return this.bedrock;
        }

        public @NotNull Layout build() {
            return new Layout(this.java.build(), this.bedrock.build());
        }
    }
}
