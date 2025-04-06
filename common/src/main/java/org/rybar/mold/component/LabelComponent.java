package org.rybar.mold.component;

/**
 * Represents a static text label component, primarily for display purposes.
 * On Bedrock, could map to text elements within a MessageForm.
 * On Java, could map to the name or lore of a static ItemStack.
 *
 * @param id The unique identifier for this label.
 * @param text The static text content of the label.
 */
public record LabelComponent(
        String id,
        String text
) implements MoldComponent {}
