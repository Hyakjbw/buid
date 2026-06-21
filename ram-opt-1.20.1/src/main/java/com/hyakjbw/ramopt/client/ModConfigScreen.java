package com.hyakjbw.ramopt.client;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.network.chat.Component;

/**
 * Configuration screen for RAM Optimization Mod settings
 * Note: For full GUI config screen, use ModConfigScreen from Forge
 * This is a placeholder for future GUI enhancements
 */
public class ModConfigScreen extends Screen {
    private final Screen parentScreen;

    public ModConfigScreen(Screen parentScreen) {
        super(Component.literal("RAM Optimization Settings"));
        this.parentScreen = parentScreen;
    }

    @Override
    protected void init() {
        // Add buttons and widgets here
        // Configuration is primarily handled through mods.toml
        // Users can also edit the config file directly
        
        this.addRenderableWidget(Button.builder(
            Component.literal("Back"),
            (button) -> this.onClose()
        ).bounds(this.width / 2 - 100, this.height - 29, 200, 20).build());
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parentScreen);
    }
}
