package com.ramopt;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigValue;

@Config(modid = "ramoptimizer")
public class ConfigHandler {
    @Config.Comment("Phần trăm RAM sử dụng để kích hoạt dọn rác (Ví dụ: 80)")
    public static ConfigValue<Integer> ramThreshold = Config.writeInt("ramThreshold", "RAM Threshold (%)", 80, 1, 100);

    @Config.Comment("Thời gian chờ giữa 2 lần dọn rác (giây) để tránh lag")
    public static ConfigValue<Integer> gcCooldown = Config.writeInt("gcCooldown", "GC Cooldown (Seconds)", 30, 5, 3600);

    @Config.Comment("Bật/Tắt hiển thị RAM trên màn hình")
    public static ConfigValue<Boolean> showHud = Config.writeInt("showHud", "Show RAM HUD", true);
}
