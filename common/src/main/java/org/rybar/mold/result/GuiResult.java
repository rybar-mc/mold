package org.rybar.mold.result;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class GuiResult {
    private final boolean cancelled;
    private final @Nullable String buttonId;
    private final @NotNull Map<String, ComponentValue> values;

    public GuiResult(boolean cancelled, @Nullable String buttonId, final @Nullable Map<String, Object> rawValues) {
        this.cancelled = cancelled;
        this.buttonId = buttonId;

        Map<String, ComponentValue> wrappedValues = new HashMap<>();
        if (rawValues != null) {
            rawValues.forEach((id, rawVal) -> {
                if (id != null) {
                    wrappedValues.put(id, new ComponentValue(rawVal));
                }
            });
        }

        this.values = Collections.unmodifiableMap(wrappedValues);
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public boolean hasClickedButton() {
        return buttonId != null;
    }

    public @Nullable String getClickedButtonId() {
        return buttonId;
    }

    public @Nullable ComponentValue getValue(@NotNull String id) {
        return values.get(id);
    }

    public <T> T getValue(@NotNull String id, @NotNull Class<T> type) {
        final ComponentValue value = getValue(id);
        if (value == null) return null;

        if (type == String.class) {
            return type.cast(value.asString());
        }

        if (type == Integer.class) {
            return type.cast(value.asInteger());
        }

        if (type == Boolean.class) {
            return type.cast(value.asBoolean());
        }

        throw new IllegalArgumentException("Unsupported type: " + type.getName());
    }

    public @NotNull Map<String, ComponentValue> getAllValues() {
        return values;
    }

    @Override
    public String toString() {
        return "GuiResult{" +
                "cancelled=" + cancelled +
                ", buttonId=" + buttonId +
                ", values=" + values +
                '}';
    }
}