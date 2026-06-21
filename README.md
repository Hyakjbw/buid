# RAM Optimization Mod - Multi-Version Support

## 🎯 Overview

Comprehensive RAM optimizer for Minecraft with multi-version support across Forge and Fabric:

- **Forge**: 1.12.2, 1.16.5, 1.18.2, 1.19.2, 1.20.1
- **Fabric**: 1.21.x, 1.26.2+

## 📦 Project Structure

```
ram-optimizer/
├── ram-opt-shared/           # Shared code for all versions
│   ├── RamStatus.java
│   ├── RamUtils.java
│   ├── ColorUtils.java
│   └── IConfigProvider.java
│
├── ram-opt-forge/           # Forge implementations
│   ├── 1.12.2/
│   ├── 1.16.5/
│   ├── 1.18.2/
│   ├── 1.19.2/
│   └── 1.20.1/
│
└── ram-opt-fabric/          # Fabric implementations
    ├── 1.21/
    └── 1.26.2/
```

## ✨ Features

### Core Features
- ✨ Real-time RAM usage monitoring
- 🧹 Automatic garbage collection
- ⚡ Aggressive cleanup for critical situations
- 📊 HUD overlay with customizable display
- ⚙️ Highly configurable settings
- 📢 Multiple notification types

### Version Support
- 🔥 Forge: 1.12.2 to 1.20.1
- 🧵 Fabric: 1.21 to 1.26.2
- 📱 Mobile-friendly (PojavLauncher optimized)

## 🛠️ Building

### Build All Versions
```bash
./gradlew build
```

### Build Specific Version
```bash
# Forge 1.20.1
./gradlew :ram-opt-forge-1.20.1:build

# Fabric 1.21
./gradlew :ram-opt-fabric-1.21:build
```

### Build Shared Library
```bash
./gradlew :ram-opt-shared:build
```

## 📋 Configuration

### Forge Versions
Edit `config/ramopt-client.toml`:

```toml
[General]
ramThresholdPercentage = 85
ramCheckIntervalTicks = 100
enableAutoCleanup = true
enableHudDisplay = true

[Aggressive]
enableAggressiveCleanup = true
aggressiveCleanupThreshold = 95

[HUD]
hudXOffset = 10
hudYOffset = 10
showUsedRam = true
showMaxRam = true
```

### Fabric Versions
Config support via ModMenu + Cloth Config:
- In-game configuration screen
- Real-time setting adjustments
- Default values in ConfigManager.java

## 🔄 Architecture

### Shared Module (`ram-opt-shared`)
Provides:
- `RamStatus` - Data class for RAM information
- `RamUtils` - Utility functions
- `ColorUtils` - Color calculations
- `IConfigProvider` - Config interface

### Forge Versions
Implements:
- Forge event system
- ForgeConfigSpec for configuration
- Version-specific event handlers
- Forge-specific HUD rendering

### Fabric Versions
Implements:
- Fabric API events
- ModMenu integration
- Cloth Config for settings GUI
- Fabric-specific rendering

## 📝 Configuration Options

### RAM Cleanup
- `ramThresholdPercentage` (1-100): Cleanup trigger threshold
- `ramCheckIntervalTicks` (10-1000): Check frequency
- `enableAutoCleanup` (true/false): Enable auto cleanup
- `enableHudDisplay` (true/false): Show HUD overlay

### Aggressive Cleanup
- `enableAggressiveCleanup`: Multi-GC cleanup strategy
- `aggressiveCleanupThreshold`: Trigger at X% RAM
- `enablePeriodicCleanup`: Regular cleanup interval
- `periodicCleanupIntervalTicks`: Cleanup frequency

### HUD Display
- `hudXOffset`, `hudYOffset`: Position
- `showMaxRam`, `showUsedRam`, `showFreeRam`: Display options
- `hudTextColor`: RGB color value
- `showWarningMessages`: Color-coded warnings

### Notifications
- `showActionBarMessages`: Action bar notifications
- `showChatMessages`: Chat notifications
- `messageDisplayTimeSeconds`: Display duration

## 🔧 Version-Specific Notes

### Forge
- **1.12.2**: Legacy version support
- **1.16.5**: Nether Update
- **1.18.2**: Caves & Cliffs
- **1.19.2**: Wild Update
- **1.20.1**: Trails & Tales

### Fabric
- **1.21**: Latest stable
- **1.26.2**: Future version support
- ModMenu integration for settings
- Cloth Config for GUI

## 📊 Performance
- Minimal overhead with configurable checks
- Efficient GC calls
- Optimized HUD rendering
- Non-blocking operations

## 🐛 Troubleshooting

### Build Fails
```bash
# Clean and rebuild
./gradlew clean build

# Check Java version (requires Java 17+)
java -version
```

### Mod Not Appearing
- Check mod folder structure
- Verify JAR is in correct `mods` directory
- Check Minecraft version compatibility

## 📄 License
MIT

## 👨‍💻 Author
Hyakjbw

## 🤝 Contributing
Feel free to submit issues and enhancement requests!
