package org.rybar.mold.result;

import org.jetbrains.annotations.Nullable;

public final class ComponentValue {
    private final Object value;

    ComponentValue(Object value) {
        this.value = value;
    }

    public @Nullable String asString() {
        return (value instanceof String s) ? s : null;
    }

    public boolean asBoolean() {
        return value instanceof Boolean b && b;
    }

    public int asInteger() {
        return value instanceof Number n ? n.intValue() : 0;
    }

    public Object rawValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ComponentValue{" +
                "value=" + value +
                '}';
    }
}
