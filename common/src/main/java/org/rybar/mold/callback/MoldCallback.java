package org.rybar.mold.callback;

import org.jetbrains.annotations.NotNull;
import org.rybar.mold.result.MoldResult;

import java.util.function.Consumer;

/**
 * Functional interface for handling the result of a Mold UI interaction.
 * This is typically provided to the MoldUI.Builder's onSubmit or onClose methods.
 */
@FunctionalInterface
public interface MoldCallback extends Consumer<MoldResult> {
    /**
     * Performs this operation on the given argument.
     *
     * @param result the result of the UI interaction
     */
    @Override
    void accept(final @NotNull MoldResult result);
}
