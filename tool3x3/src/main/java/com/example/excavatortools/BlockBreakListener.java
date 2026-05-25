package com.example.excavatortools;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BlockBreakListener implements Listener {

    private static final NamespacedKey KEY = new NamespacedKey(ExcavatorTools.getInstance(), "excavator");
    private final Set<UUID> processingPlayers = new HashSet<>();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        // Log 1: Sự kiện được gọi
        ExcavatorTools.getInstance().getLogger().info("[DEBUG] BlockBreakEvent fired, cancelled=" + event.isCancelled());

        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        Block origin = event.getBlock();
        ItemStack tool = player.getInventory().getItemInMainHand();

        // Log 2: Thông tin tool
        ExcavatorTools.getInstance().getLogger().info("[DEBUG] Player: " + player.getName() + ", Tool: " + (tool != null ? tool.getType() : "null"));

        if (tool == null || !tool.hasItemMeta()) {
            ExcavatorTools.getInstance().getLogger().info("[DEBUG] Tool null or no meta, return");
            return;
        }

        // Log 3: Kiểm tra PersistentData
        boolean hasKey = tool.getItemMeta().getPersistentDataContainer().has(KEY, PersistentDataType.BYTE);
        ExcavatorTools.getInstance().getLogger().info("[DEBUG] Has excavator key: " + hasKey);
        if (!hasKey) return;

        if (processingPlayers.contains(player.getUniqueId())) {
            ExcavatorTools.getInstance().getLogger().info("[DEBUG] Player in processing set, return");
            return;
        }

        // Xác định mặt đào (như trước, không đổi)
        BlockFace face;
        if (tool.getType().name().endsWith("_SHOVEL")) {
            face = (origin.getY() <= player.getEyeLocation().getBlockY()) ? BlockFace.UP : BlockFace.DOWN;
        } else {
            Vector eye = player.getEyeLocation().toVector();
            Vector target = origin.getLocation().toVector();
            Vector direction = target.subtract(eye);
            if (direction.lengthSquared() < 0.0001) direction = new Vector(0, -1, 0);
            else direction.normalize();
            face = getTargetFace(direction);
        }

        Set<Block> blocks = get3x3Blocks(origin, face);
        ExcavatorTools.getInstance().getLogger().info("[DEBUG] Blocks to break: " + blocks.size());

        processingPlayers.add(player.getUniqueId());

        try {
            for (Block b : blocks) {
                if (b.equals(origin)) continue;
                if (b.getType() == Material.BEDROCK || b.getType() == Material.AIR) continue;

                // Kiểm tra WorldGuard bằng reflection (không crash nếu không có WG)
                if (!canBreakSafe(player, b)) {
                    ExcavatorTools.getInstance().getLogger().info("[DEBUG] Block " + b.getType() + " at " + b.getLocation() + " blocked by WG");
                    continue;
                }

                b.breakNaturally(tool);
            }
        } finally {
            processingPlayers.remove(player.getUniqueId());
        }
    }

    private boolean canBreakSafe(Player player, Block block) {
        // Dùng reflection để tránh lỗi class loading nếu không có WorldGuard
        try {
            Plugin wgPlugin = ExcavatorTools.getInstance().getServer().getPluginManager().getPlugin("WorldGuard");
            if (wgPlugin == null) return true;

            // Lấy class WorldGuardPlugin và gọi phương thức tương tự như cũ qua reflection
            Class<?> wgClass = wgPlugin.getClass();
            Object localPlayer = wgClass.getMethod("wrapPlayer", Player.class).invoke(wgPlugin, player);

            // WorldGuard.getInstance().getPlatform().getRegionContainer()
            Class<?> worldGuardClass = Class.forName("com.sk89q.worldguard.WorldGuard");
            Object worldGuard = worldGuardClass.getMethod("getInstance").invoke(null);
            Object platform = worldGuardClass.getMethod("getPlatform").invoke(worldGuard);
            Object regionContainer = platform.getClass().getMethod("getRegionContainer").invoke(platform);
            Object query = regionContainer.getClass().getMethod("createQuery").invoke(regionContainer);

            // Flags.BLOCK_BREAK, BUILD
            Class<?> flagsClass = Class.forName("com.sk89q.worldguard.protection.flags.Flags");
            Object blockBreakFlag = flagsClass.getField("BLOCK_BREAK").get(null);
            Object buildFlag = flagsClass.getField("BUILD").get(null);

            // BukkitAdapter.adapt(block.getLocation())
            Class<?> adapterClass = Class.forName("com.sk89q.worldedit.bukkit.BukkitAdapter");
            Object weLoc = adapterClass.getMethod("adapt", org.bukkit.Location.class).invoke(null, block.getLocation());

            boolean canBreak = (boolean) query.getClass().getMethod("testState", Object.class, Class.forName("com.sk89q.worldguard.LocalPlayer"), Class.forName("com.sk89q.worldguard.protection.flags.Flag"))
                    .invoke(query, weLoc, localPlayer, blockBreakFlag);
            boolean canBuild = (boolean) query.getClass().getMethod("testState", Object.class, Class.forName("com.sk89q.worldguard.LocalPlayer"), Class.forName("com.sk89q.worldguard.protection.flags.Flag"))
                    .invoke(query, weLoc, localPlayer, buildFlag);

            return canBreak && canBuild;
        } catch (Exception e) {
            // Nếu không có WorldGuard, hoặc lỗi reflection -> cho phép
            return true;
        }
    }

    private BlockFace getTargetFace(Vector direction) {
        double x = Math.abs(direction.getX());
        double y = Math.abs(direction.getY());
        double z = Math.abs(direction.getZ());
        if (x > y && x > z) return direction.getX() > 0 ? BlockFace.WEST : BlockFace.EAST;
        if (y > z) return direction.getY() > 0 ? BlockFace.DOWN : BlockFace.UP;
        return direction.getZ() > 0 ? BlockFace.NORTH : BlockFace.SOUTH;
    }

    private Set<Block> get3x3Blocks(Block center, BlockFace face) {
        Set<Block> blocks = new HashSet<>();
        int[][] offsets;
        switch (face) {
            case UP:
            case DOWN:
                offsets = new int[][]{{-1,0,-1},{-1,0,0},{-1,0,1},{0,0,-1},{0,0,0},{0,0,1},{1,0,-1},{1,0,0},{1,0,1}};
                break;
            case NORTH:
            case SOUTH:
                offsets = new int[][]{{-1,-1,0},{-1,0,0},{-1,1,0},{0,-1,0},{0,0,0},{0,1,0},{1,-1,0},{1,0,0},{1,1,0}};
                break;
            default:
                offsets = new int[][]{{0,-1,-1},{0,-1,0},{0,-1,1},{0,0,-1},{0,0,0},{0,0,1},{0,1,-1},{0,1,0},{0,1,1}};
        }
        for (int[] offset : offsets) {
            blocks.add(center.getRelative(offset[0], offset[1], offset[2]));
        }
        return blocks;
    }
}
