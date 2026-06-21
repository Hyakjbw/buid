package com.hyakjbw.ramopt.events;

import com.hyakjbw.ramopt.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "ramopt", value = Dist.CLIENT)
public class RamCleaner {
    
    private static int tickCounter = 0;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        // Chỉ chạy ở cuối chu kỳ tick để tránh lag
        if (event.phase != TickEvent.Phase.END) return;
        
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return; // Chỉ bắt đầu theo dõi khi đã vào thế giới (World)

        tickCounter++;
        if (tickCounter >= 100) { // 100 ticks = 5 giây
            tickCounter = 0;

            long maxMemory = Runtime.getRuntime().maxMemory();
            long totalMemory = Runtime.getRuntime().totalMemory();
            long freeMemory = Runtime.getRuntime().freeMemory();
            long usedMemory = totalMemory - freeMemory;

            int percentUsed = (int) (usedMemory * 100L / maxMemory);
            int threshold = Config.MAX_RAM_THRESHOLD.get();

            if (percentUsed >= threshold) {
                // Hiển thị thông báo trên Action Bar (true)
                mc.player.displayClientMessage(
                    Component.literal("§e[RAM Opt] Dung lượng đạt " + percentUsed + "%. Đang giải phóng bộ nhớ..."), 
                    true
                );
                
                // Gọi bộ thu gom rác của Java
                System.gc();
            }
        }
    }
}
