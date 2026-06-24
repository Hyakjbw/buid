package com.hyakjbw.ramopt.events;

import com.hyakjbw.ramopt.config.ConfigManager;
import com.hyakjbw.ramopt.service.RamMonitor;
import com.hyakjbw.ramopt.service.RamOptimizer;
import com.hyakjbw.ramopt.util.LoggerUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Event handler for client tick events
 * Monitors RAM and triggers cleanup when needed
 */
@Mod.EventBusSubscriber(modid = "ramopt", value = Dist.CLIENT)
public class ClientTickHandler {
    private static int tickCounter = 0;
    private static final RamMonitor RAM_MONITOR = RamMonitor.getInstance();
    private static final RamOptimizer RAM_OPTIMIZER = RamOptimizer.getInstance();

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        try {
            tickCounter++;
            int checkInterval = ConfigManager.RAM_CHECK_INTERVAL_TICKS.get();

            // Check RAM status at regular intervals
            if (tickCounter >= checkInterval) {
                tickCounter = 0;

                // Update RAM status
                if (RAM_MONITOR.checkAndUpdate()) {
                    // Check if aggressive cleanup is needed
                    if (RAM_MONITOR.needsAggressiveCleanup()) {
                        RAM_OPTIMIZER.performAggressiveCleanup();
                    } else if (RAM_MONITOR.needsCleanup()) {
                        // Perform normal cleanup
                        RAM_OPTIMIZER.performCleanup();
                    }
                }

                // Check if periodic cleanup should be performed
                RAM_OPTIMIZER.performPeriodicCleanup();
            }
        } catch (Exception e) {
            LoggerUtil.error("Error in client tick handler", e);
        }
    }
}
