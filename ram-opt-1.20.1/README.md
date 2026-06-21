# RAM Optimization Mod 2.0 - Minecraft 1.20.1 Forge

## Overview
Automatic RAM optimizer for Minecraft 1.20.1 Forge with real-time monitoring and customizable cleanup strategies.

### Features
- **Real-time RAM Monitoring**: Display current RAM usage on HUD
- **Automatic Cleanup**: Triggers garbage collection when RAM reaches threshold
- **Aggressive Cleanup**: Enhanced cleanup for critical RAM situations
- **Periodic Cleanup**: Optional automatic cleanup at regular intervals
- **Customizable Settings**: Full configuration via `ramopt-client.toml`
- **Multiple Notifications**: Action bar, chat messages, or silent mode
- **Performance Optimized**: Minimal impact on game performance
- **Mobile Optimized**: Designed for mobile launchers like PojavLauncher

## Configuration

Settings are located in `config/ramopt-client.toml`:

### RAM Cleanup Settings
- `ramThresholdPercentage` (default: 85): RAM percentage to trigger cleanup
- `ramCheckIntervalTicks` (default: 100): Check interval (20 ticks = 1 second)
- `enableAutoCleanup` (default: true): Enable automatic cleanup
- `enableHudDisplay` (default: true): Show RAM on HUD

### Aggressive Cleanup
- `enableAggressiveCleanup` (default: true): Enable aggressive cleanup
- `aggressiveCleanupThreshold` (default: 95): Trigger threshold for aggressive cleanup
- `enablePeriodicCleanup` (default: false): Enable periodic cleanup
- `periodicCleanupIntervalTicks` (default: 6000): Periodic cleanup interval

### HUD Display
- `hudXOffset` (default: 10): X position for HUD
- `hudYOffset` (default: 10): Y position for HUD
- `showMaxRam`, `showUsedRam`, `showFreeRam`: Toggle RAM display options
- `hudTextColor` (default: 16777215): HUD text color (RGB format)
- `showWarningMessages` (default: true): Enable color-coded warnings

### Notifications
- `showActionBarMessages` (default: true): Show cleanup in action bar
- `showChatMessages` (default: false): Show cleanup in chat
- `messageDisplayTimeSeconds` (default: 3): Message display duration

## Installation
1. Download the mod JAR file
2. Place it in your `mods` folder
3. Edit `config/ramopt-client.toml` to customize settings
4. Launch Minecraft

## Building
```bash
cd ram-opt-1.20.1
./gradlew build
```

## Architecture

### Project Structure
```
src/main/java/com/hyakjbw/ramopt/
├── RamOptMod.java           # Main entry point
├── config/
│   └── ConfigManager.java   # Configuration management
├── events/
│   ├── ClientTickHandler.java
│   └── RenderHudHandler.java
├── service/
│   ├── RamMonitor.java     # RAM status monitoring
│   ├── RamOptimizer.java   # Cleanup logic
│   └── HudRenderer.java    # HUD rendering
├── util/
│   ├── RamUtils.java
│   ├── ColorUtils.java
│   └── LoggerUtil.java
├── model/
│   └── RamStatus.java      # Data class
└── client/
    └── ModConfigScreen.java # Config GUI (placeholder)
```

## Performance
- Minimal overhead with configurable check intervals
- Efficient memory calculations
- Non-blocking cleanup operations
- Optimized HUD rendering

## Changelog

### Version 2.0.0
- Complete architectural refactor
- Added aggressive cleanup strategy
- Implemented periodic cleanup
- Extended configuration options
- Improved HUD display
- Added comprehensive logging
- Modular service-based design
- Better error handling

## License
MIT

## Author
Hyakjbw
