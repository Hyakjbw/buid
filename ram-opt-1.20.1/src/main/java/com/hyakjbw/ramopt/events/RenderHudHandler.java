package com.hyakjbw.ramopt.events;

import com.hyakjbw.ramopt.service.HudRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Event handler for HUD rendering
 * Displays RAM status on screen
 */
@Mod.EventBusSubscriber(modid = "ramopt", value = Dist.CLIENT)
public class RenderHudHandler {
    private static final HudRenderer HUD_RENDERER = HudRenderer.getInstance();

    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiEvent.Post event) {
        try {
            HUD_RENDERER.renderHud(event.getGuiGraphics());
        } catch (Exception e) {
            // Silently fail to avoid breaking rendering
        }
    }
}
