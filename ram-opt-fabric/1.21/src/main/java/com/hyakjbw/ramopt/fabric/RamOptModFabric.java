package com.hyakjbw.ramopt.fabric;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RamOptModFabric implements ModInitializer {
    public static final String MOD_ID = "ramopt";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing RAM Optimization Mod (Fabric 1.21)...");
        LOGGER.info("RAM Optimization Mod initialized!");
    }
}
