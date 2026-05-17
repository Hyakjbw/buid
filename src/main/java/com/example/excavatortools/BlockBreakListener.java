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
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class BlockBreakListener implements Listener {

    private static final NamespacedKey KEY = new NamespacedKey(ExcavatorTools.getInstance(), "excavator");

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
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

        // Tính vùng 3x3 dựa trên mặt bị đào
        Set<Block> blocks = get3x3Blocks(origin, face);

        // Đánh dấu đang xử lý để tránh đệ quy
        player.setMetadata("excavator_processing", new FixedMetadataValue(ExcavatorTools.getInstance(), true));

        // Phá các block trong vùng (trừ block gốc đã được phá bởi sự kiện này)
        for (Block b : blocks) {
            if (b.equals(origin)) continue; // block gốc sự kiện đã xử lý
            if (b.getType() == Material.BEDROCK || b.getType() == Material.AIR) continue;

            // Dùng breakNaturally để server và anti-xray cập nhật đúng
            b.breakNaturally(tool);
        }

        // Xóa cờ
        player.removeMetadata("excavator_processing", ExcavatorTools.getInstance());
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
