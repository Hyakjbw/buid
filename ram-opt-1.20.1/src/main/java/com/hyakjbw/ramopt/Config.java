package com.hyakjbw.ramopt;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.IntValue MAX_RAM_THRESHOLD;

    static {
        BUILDER.push("General");
        MAX_RAM_THRESHOLD = BUILDER
                .comment("Ngưỡng phần trăm RAM tối đa trước khi mod tự động dọn dẹp (1-100)")
                .defineInRange("maxRamThreshold", 85, 1, 100);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
