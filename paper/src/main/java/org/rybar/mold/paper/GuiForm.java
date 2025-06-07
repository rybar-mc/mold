package org.rybar.mold.paper;

import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.Form;
import org.geysermc.cumulus.form.ModalForm;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.util.FormImage;
import org.geysermc.floodgate.api.FloodgateApi;
import org.jetbrains.annotations.NotNull;
import org.rybar.mold.component.ButtonComponent;
import org.rybar.mold.component.LabelComponent;
import org.rybar.mold.component.ToggleComponent;
import org.rybar.mold.component.MoldComponent;
import org.rybar.mold.gui.FormType;
import org.rybar.mold.gui.Gui;
import org.rybar.mold.result.GuiResult;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GuiForm {
    private final @NotNull Gui gui;
    private final @NotNull UUID playerUuid;
    private final Map<String, MoldComponent> componentsById = new HashMap<>();

    public GuiForm(final @NotNull Player player, final @NotNull Gui gui) {
        this.gui = gui;
        this.playerUuid = player.getUniqueId();

        gui.layout().bedrock().components().forEach(component ->
                componentsById.put(component.id(), component)
        );

        Form form = buildForm();
        FloodgateApi.getInstance().sendForm(playerUuid, form);
    }

    private Form buildForm() {
        FormType formType = gui.layout().defaultBedrockFormType();

        return switch (formType) {
            case MODAL -> buildModalForm();
            case CUSTOM -> buildCustomForm();
            case ACTION, INVENTORY -> buildSimpleForm();
        };
    }

    private SimpleForm buildSimpleForm() {
        SimpleForm.Builder builder = SimpleForm.builder()
                .title(gui.title())
                .content(getContentText());

        gui.layout().bedrock().components().forEach(component -> {
            if (component instanceof ButtonComponent button) {
                builder.button(button.text(), button.iconId().isEmpty()
                        ? null
                        : FormImage.of(FormImage.Type.PATH, button.iconId().get())
                );
            }
        });

        return builder.validResultHandler(response -> {
            int clickedIndex = response.clickedButtonId();
            if (clickedIndex < gui.layout().bedrock().components().size()) {
                MoldComponent component = gui.layout().bedrock().components().get(clickedIndex);
                if (component instanceof ButtonComponent button) {
                    handleButtonClick(button);
                }
            }
        }).build();
    }

    private ModalForm buildModalForm() {
        if (gui.layout().bedrock().components().size() < 2) {
            throw new IllegalStateException("Modal form requires at least 2 button components");
        }

        ButtonComponent button1 = (ButtonComponent) gui.layout().bedrock().components().get(0);
        ButtonComponent button2 = (ButtonComponent) gui.layout().bedrock().components().get(1);

        return ModalForm.builder()
                .title(gui.title())
                .content(getContentText())
                .button1(button1.text())
                .button2(button2.text())
                .validResultHandler(response -> {
                    ButtonComponent clickedButton = response.clickedFirst() ? button1 : button2;
                    handleButtonClick(clickedButton);
                })
                .build();
    }

    private CustomForm buildCustomForm() {
        CustomForm.Builder builder = CustomForm.builder()
                .title(gui.title());

        gui.layout().bedrock().components().forEach(component -> {
            switch (component) {
                case LabelComponent label -> builder.label(label.text());
                case ToggleComponent toggle -> builder.toggle(toggle.text(), toggle.defaultValue());
                case ButtonComponent button -> {
                    builder.label(button.text());
                }
                default -> {}
            }
        });

        return builder.validResultHandler(response -> {
            // Create a map of component values
            /*Map<String, Object> values = new HashMap<>();
            for (int i = 0; i < response.valueCount(); i++) {
                if (i < gui.layout().bedrock().components().size()) {
                    MoldComponent component = gui.layout().bedrock().components().get(i);
                    values.put(component.id(), response.valueAt(i));
                }
            }*/
            Map<String, Object> values = new HashMap<>();

            GuiResult result = new GuiResult(false, null, values);
            gui.onSubmit().ifPresent(callback -> callback.accept(result));
        }).build();
    }

    private String getContentText() {
        return gui.layout().bedrock().components().stream()
                .filter(c -> c instanceof LabelComponent)
                .map(c -> ((LabelComponent) c).text())
                .findFirst()
                .orElse("");
    }

    private void handleButtonClick(ButtonComponent button) {
        Map<String, Object> values = new HashMap<>();
        values.put(button.id(), true);

        GuiResult result = new GuiResult(false, button.id(), values);

        button.callback().ifPresent(callback -> callback.accept(result));

        gui.onSubmit().ifPresent(callback -> callback.accept(result));
    }
}