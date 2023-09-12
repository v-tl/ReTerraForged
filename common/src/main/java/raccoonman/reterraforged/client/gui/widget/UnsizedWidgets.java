package raccoonman.reterraforged.client.gui.widget;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Supplier;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import raccoonman.reterraforged.client.gui.Toasts;
import raccoonman.reterraforged.client.gui.Tooltips;
import raccoonman.reterraforged.client.gui.widget.Slider.Format;

public final class UnsizedWidgets {
	
	public static EditBox createEditBox(Font font, Consumer<String> responder) {
		EditBox box = new EditBox(font, -1, -1, -1, -1, CommonComponents.EMPTY);
		box.setResponder(responder);
		return box;
	}
	
	public static Label createLabel(String text) {
		return new Label(-1, -1, -1, -1, Component.translatable(text));
	}

	private static Slider createSlider(float initial, float min, float max, String text, Slider.Format format, DoubleConsumer callback) {
		Slider slider = new Slider(-1, -1, -1, -1, initial, min, max, Component.translatable(text), format);
		slider.setTooltip(Tooltips.create(Tooltips.translationKey(text)));
		slider.setCallback(callback);
		return slider;
	}

	public static Slider createFloatSlider(float initial, float min, float max, String text, DoubleConsumer callback) {
		return createSlider(initial, min, max, text, Format.FLOAT, callback);
	}
	
	public static Slider createIntSlider(int initial, int min, int max, String text, DoubleConsumer callback) {
		return createSlider(initial, min, max, text, Format.INT, callback);
	}
	
	public static <T extends Enum<T>> CycleButton<T> createCycle(Supplier<T[]> values, T initial, String text, CycleButton.OnValueChange<T> callback) {
		CycleButton<T> button = CycleButton.<T>builder((e) -> {
			return Component.literal(e.name());
		}).withValues(values.get()).withInitialValue(initial).create(-1, -1, -1, -1, Component.translatable(text), callback);
		button.setTooltip(Tooltips.create(Tooltips.translationKey(text)));
		button.onPress();
		return button;
	}
	
	public static Button createThrowingButton(String text, Toasts.ThrowingRunnable run) {
		return Button.builder(Component.translatable(text), (b) -> {
			Toasts.tryOrToast(Tooltips.failTranslationKey(text), run);
		}).build();
	}
}