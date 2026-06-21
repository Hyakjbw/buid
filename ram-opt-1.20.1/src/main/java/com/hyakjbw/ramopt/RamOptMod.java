package com.hyakjbw.ramopt;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.ModLoadingContext;
import com.hyakjbw.ramopt.events.RamHudOverlay;
import com.hyakjbw.ramopt.events.RamCleaner;

// Tên modid phải khớp chính xác với khai báo trong mods.toml và build.gradle
@Mod("ramopt")
public class RamOptMod {
    
    public RamOptMod() {
        // 1. Đăng ký file config
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC, "ramopt-client.toml");

        // 2. Đăng ký các sự kiện (Các class sử dụng @Mod.EventBusSubscriber đã tự động đăng ký, 
        // dòng này để đảm bảo Forge Load đúng vòng đời nếu cần mở rộng sau này)
        MinecraftForge.EVENT_BUS.register(this);
    }
}
