package com.ramopt;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = "ramoptimizer", name = "Pojav RAM Optimizer", version = "1.0", acceptedMinecraftVersions = "[1.12.2]")
public class RamOptimizer {
    private long lastGcTime = 0;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new RamHud());
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            checkAndOptimizeRam();
        }
    }

    private void checkAndOptimizeRam() {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
        double usedPercent = ((double) usedMemory / maxMemory) * 100;
        long currentTime = System.currentTimeMillis();

        if (usedPercent > ConfigHandler.ramThreshold.get()) {
            if (currentTime - lastGcTime > (ConfigHandler.gcCooldown.get() * 1000L)) {
                System.gc();
                lastGcTime = currentTime;
            }
        }
    }
}
