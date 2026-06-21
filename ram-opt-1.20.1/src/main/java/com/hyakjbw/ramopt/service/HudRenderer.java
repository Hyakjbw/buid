package com.hyakjbw.ramopt.service;

import com.hyakjbw.ramopt.config.ConfigManager;
import com.hyakjbw.ramopt.model.RamStatus;
import com.hyakjbw.ramopt.util.ColorUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

/**
 * Service responsible for rendering HUD overlay
 */
public class HudRenderer {
    private static final HudRenderer INSTANCE = new HudRenderer();
    private static final int LINE_HEIGHT = 10;
    private static final int LINE_SPACING = 2;

    private HudRenderer() {}

    public static HudRenderer getInstance() {
        return INSTANCE;
    }

    /**
     * Render RAM status on HUD
     * @param guiGraphics graphics context
     */
    public void renderHud(GuiGraphics guiGraphics) {
        if (!ConfigManager.ENABLE_HUD_DISPLAY.get()) {
            return;
        }

        try {
            RamMonitor monitor = RamMonitor.getInstance();
            RamStatus status = monitor.getLastStatus();

            if (status == null) {
                return;
            }

            int x = ConfigManager.HUD_X_OFFSET.get();
            int y = ConfigManager.HUD_Y_OFFSET.get();
            int color = ConfigManager.HUD_TEXT_COLOR.get();

            // Determine color based on RAM usage
            if (ConfigManager.SHOW_WARNING_MESSAGES.get()) {
                color = ColorUtils.getColorForRamUsage(status.getPercentUsed());
            }

            int lineY = y;

            // Render header
            String header = "§e=== RAM Status ===";
            guiGraphics.drawString(null, Component.literal(header).getString(), x, lineY, color, false);
            lineY += LINE_HEIGHT + LINE_SPACING;

            // Render used RAM
            if (ConfigManager.SHOW_USED_RAM.get()) {
                String usedText = String.format("Used: %s (%d%%)", 
                    status.getFormattedUsedMemory(), 
                    status.getPercentUsed());
                guiGraphics.drawString(null, usedText, x, lineY, color, false);
                lineY += LINE_HEIGHT + LINE_SPACING;
            }

            // Render free RAM
            if (ConfigManager.SHOW_FREE_RAM.get()) {
                String freeText = String.format("Free: %s", status.getFormattedFreeMemory());
                guiGraphics.drawString(null, freeText, x, lineY, color, false);
                lineY += LINE_HEIGHT + LINE_SPACING;
            }

            // Render max RAM
            if (ConfigManager.SHOW_MAX_RAM.get()) {
                String maxText = String.format("Max: %s", status.getFormattedMaxMemory());
                guiGraphics.drawString(null, maxText, x, lineY, color, false);
            }
        } catch (Exception e) {
            // Silently fail to avoid breaking the render cycle
        }
    }
}
