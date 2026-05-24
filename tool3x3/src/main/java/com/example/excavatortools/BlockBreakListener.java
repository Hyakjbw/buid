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
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class BlockBreakListener implements Listener {

    private static final NamespacedKey KEY = new NamespacedKey(ExcavatorTools.getInstance(), "excavator");
    private static WorldGuardPlugin worldGuard = null;

    static {
        Plugin plugin = ExcavatorTools.getInstance().getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin instanceof WorldGuardPlugin) {
            worldGuard = (WorldGuardPlugin) plugin;
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        // Nếu sự kiện gốc bị hủy (do plugin khác như WorldGuard) thì không làm gì
        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        Block origin = event.getBlock();
        ItemStack tool = player.getInventory().getItemInMainHand();

        // Kiểm tra item có phải excavator tool không
        if (tool == null || !tool.hasItemMeta()) return;
        if (!tool.getItemMeta().getPersistentDataContainer().has(KEY, PersistentDataType.BYTE)) return;

        // Tránh vòng lặp vô hạn khi phá block con
        if (player.hasMetadata("excavator_processing")) return;

        // Xác định hướng đào
        Vector direction = player.getEyeLocation().toVector().subtract(origin.getLocation().toVector()).normalize();
        BlockFace face = getTargetFace(direction);

        // Tính vùng 3x3
        Set<Block> blocks = get3x3Blocks(origin, face);

        // Đánh dấu đang xử lý
        player.setMetadata("excavator_processing", new FixedMetadataValue(ExcavatorTools.getInstance(), true));

        // Duyệt và phá an toàn
        for (Block b : blocks) {
            if (b.equals(origin)) continue; // block gốc đã được sự kiện chính xử lý
            if (b.getType() == Material.BEDROCK || b.getType() == Material.AIR) continue;

            // ✅ KIỂM TRA QUYỀN WORLDGUARD TRƯỚC KHI PHÁ
            if (!canBuild(player, b)) continue;

            // Phá block bằng breakNaturally để kích hoạt event và anti‑xray
            b.breakNaturally(tool);
        }

        // Xoá cờ đánh dấu
        player.removeMetadata("excavator_processing", ExcavatorTools.getInstance());
    }

    /**
     * Kiểm tra người chơi có quyền phá block tại vị trí này không (dùng WorldGuard)
     */
    private boolean canBuild(Player player, Block block) {
        if (worldGuard == null) return true; // không có WorldGuard thì cho phép

        LocalPlayer localPlayer = worldGuard.wrapPlayer(player);
        com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(block.getLocation());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();

        // Nếu không có region nào ở đó, trả về true; nếu có, kiểm tra cờ BUILD
        return query.testState(loc, localPlayer, Flags.BUILD);
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
                offsets = new int[][]{{-1,0,-1}, {-1,0,0}, {-1,0,1}, {0,0,-1}, {0,0,0}, {0,0,1}, {1,0,-1}, {1,0,0}, {1,0,1}};
                break;
            case NORTH:
            case SOUTH:
                offsets = new int[][]{{-1,-1,0}, {-1,0,0}, {-1,1,0}, {0,-1,0}, {0,0,0}, {0,1,0}, {1,-1,0}, {1,0,0}, {1,1,0}};
                break;
            default: // EAST/WEST
                offsets = new int[][]{{0,-1,-1}, {0,-1,0}, {0,-1,1}, {0,0,-1}, {0,0,0}, {0,0,1}, {0,1,-1}, {0,1,0}, {0,1,1}};
        }
        for (int[] offset : offsets) {
            Block b = center.getRelative(offset[0], offset[1], offset[2]);
            blocks.add(b);
        }
        return blocks;
    }
}
