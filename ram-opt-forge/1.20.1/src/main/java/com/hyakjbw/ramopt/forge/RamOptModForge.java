package com.hyakjbw.ramopt.forge;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.ModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("ramopt")
public class RamOptModForge {
    public static final String MOD_ID = "ramopt";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public RamOptModForge() {
        LOGGER.info("Initializing RAM Optimization Mod (Forge 1.20.1)...");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigManager.CLIENT_SPEC, "ramopt-client.toml");
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("RAM Optimization Mod initialized!");
    }
}
