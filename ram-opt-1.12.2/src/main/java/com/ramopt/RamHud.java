package com.ramopt;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class RamHud {
    private String ramText = "";
    private int tickCounter = 0;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            tickCounter++;
            if (tickCounter >= 20) { // Cập nhật mỗi 1 giây
                updateRamText();
                tickCounter = 0;
            }
        }
    }

    private void updateRamText() {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory() / 1024 / 1024;
        long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
        ramText = String.format("RAM: %dMB / %dMB", usedMemory, maxMemory);
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL && ConfigHandler.showHud.get()) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.fontRenderer != null) {
                mc.fontRenderer.drawStringWithShadow(ramText, 5, 5, 0x55FF55);
            }
        }
    }
}
