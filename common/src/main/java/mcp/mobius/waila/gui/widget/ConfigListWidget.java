package mcp.mobius.waila.gui.widget;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.mojang.blaze3d.vertex.PoseStack;
import mcp.mobius.waila.gui.screen.ConfigScreen;
import mcp.mobius.waila.gui.widget.value.BooleanValue;
import mcp.mobius.waila.gui.widget.value.ConfigValue;
import mcp.mobius.waila.gui.widget.value.CycleValue;
import mcp.mobius.waila.gui.widget.value.EnumValue;
import mcp.mobius.waila.gui.widget.value.InputValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;

public class ConfigListWidget extends ContainerObjectSelectionList<ConfigListWidget.Entry> {

    private final ConfigScreen owner;
    private final Runnable diskWriter;

    public ConfigListWidget(ConfigScreen owner, Minecraft client, int width, int height, int top, int bottom, int itemHeight, Runnable diskWriter) {
        super(client, width, height, top, bottom, itemHeight);

        this.owner = owner;
        this.diskWriter = diskWriter;

        setRenderBackground(false);
    }

    public ConfigListWidget(ConfigScreen owner, Minecraft client, int width, int height, int top, int bottom, int itemHeight) {
        this(owner, client, width, height, top, bottom, itemHeight, null);
    }

    @Override
    public int getRowWidth() {
        return Math.min(width - 5, 300);
    }

    @Override
    protected int getScrollbarPosition() {
        return getRowRight() + 5;
    }

    public void save() {
        children()
            .stream()
            .filter(e -> e instanceof ConfigValue)
            .map(e -> (ConfigValue<?>) e)
            .forEach(ConfigValue::save);
        if (diskWriter != null)
            diskWriter.run();
    }

    public void add(mcp.mobius.waila.gui.widget.ConfigListWidget.Entry entry) {
        if (entry instanceof ConfigValue) {
            GuiEventListener element = ((ConfigValue<?>) entry).getListener();
            if (element != null)
                owner.addListener(element);
        }
        addEntry(entry);
    }

    public ConfigListWidget with(mcp.mobius.waila.gui.widget.ConfigListWidget.Entry entry) {
        add(entry);
        return this;
    }

    public ConfigListWidget withButton(String title, Button button) {
        add(new ButtonEntry(title, button));
        return this;
    }

    public ConfigListWidget withButton(String title, int width, int height, Button.OnPress pressAction) {
        add(new ButtonEntry(title, width, height, pressAction));
        return this;
    }

    public ConfigListWidget withBoolean(String optionName, boolean value, Consumer<Boolean> save) {
        add(new BooleanValue(optionName, value, save));
        return this;
    }

    public ConfigListWidget withCycle(String optionName, String[] values, String selected, Consumer<String> save, boolean createLocale) {
        add(new CycleValue(optionName, values, selected, save, createLocale));
        return this;
    }

    public ConfigListWidget withCycle(String optionName, String[] values, String selected, Consumer<String> save) {
        add(new CycleValue(optionName, values, selected, save));
        return this;
    }

    public <T extends Enum<T>> ConfigListWidget withEnum(String optionName, T[] values, T selected, Consumer<T> save) {
        add(new EnumValue<>(optionName, values, selected, save));
        return this;
    }

    public <T> ConfigListWidget withInput(String optionName, T value, Consumer<T> save, Predicate<String> validator) {
        add(new InputValue<>(optionName, value, save, validator));
        return this;
    }

    public <T> ConfigListWidget withInput(String optionName, T value, Consumer<T> save) {
        add(new InputValue<>(optionName, value, save));
        return this;
    }

    public abstract static class Entry extends ContainerObjectSelectionList.Entry<mcp.mobius.waila.gui.widget.ConfigListWidget.Entry> {

        protected final Minecraft client;

        public Entry() {
            this.client = Minecraft.getInstance();
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return Collections.emptyList();
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return Collections.emptyList();
        }

        @Override
        public abstract void render(PoseStack matrices, int index, int rowTop, int rowLeft, int width, int height, int mouseX, int mouseY, boolean hovered, float deltaTime);

    }

}
