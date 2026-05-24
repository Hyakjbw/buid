package com.example.excavatortools;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
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
    private static WorldGuardPlugin worldGuard = null;

    private final Set<UUID> processingPlayers = new HashSet<>();

    static {
        Plugin plugin = ExcavatorTools.getInstance().getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin instanceof WorldGuardPlugin) {
            worldGuard = (WorldGuardPlugin) plugin;
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        Block origin = event.getBlock();
        ItemStack tool = player.getInventory().getItemInMainHand();

        // Kiểm tra đúng công cụ
        if (tool == null || !tool.hasItemMeta()) return;
        if (!tool.getItemMeta().getPersistentDataContainer().has(KEY, PersistentDataType.BYTE)) return;

        // Chống đệ quy
        if (processingPlayers.contains(player.getUniqueId())) return;

        // Xác định hướng đào an toàn (tránh zero vector)
        Vector eye = player.getEyeLocation().toVector();
        Vector target = origin.getLocation().toVector();
        Vector direction = target.subtract(eye); // vector từ mắt đến block

        // Nếu đứng quá gần block (đào dưới chân) → mặc định đào mặt trên (DOWN)
        if (direction.lengthSquared() < 0.0001) {
            direction = new Vector(0, -1, 0);
        } else {
            direction.normalize();
        }

        BlockFace face = getTargetFace(direction);
        Set<Block> blocks = get3x3Blocks(origin, face);

        // Đánh dấu đang xử lý
        processingPlayers.add(player.getUniqueId());

        try {
            for (Block b : blocks) {
                if (b.equals(origin)) continue;
                if (b.getType() == Material.BEDROCK || b.getType() == Material.AIR) continue;

                // Kiểm tra WorldGuard (nếu có)
                if (!canBreak(player, b)) continue;

                // Phá block an toàn
                b.breakNaturally(tool);
            }
        } finally {
            // Hủy đánh dấu
            processingPlayers.remove(player.getUniqueId());
        }
    }

    private boolean canBreak(Player player, Block block) {
        if (worldGuard == null) return true;

        LocalPlayer localPlayer = worldGuard.wrapPlayer(player);
        com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(block.getLocation());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        return query.testState(loc, localPlayer, Flags.BLOCK_BREAK)
                && query.testState(loc, localPlayer, Flags.BUILD);
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
            default: // EAST/WEST
                offsets = new int[][]{{0,-1,-1},{0,-1,0},{0,-1,1},{0,0,-1},{0,0,0},{0,0,1},{0,1,-1},{0,1,0},{0,1,1}};
        }
        for (int[] offset : offsets) {
            blocks.add(center.getRelative(offset[0], offset[1], offset[2]));
        }
        return blocks;
    }
}
