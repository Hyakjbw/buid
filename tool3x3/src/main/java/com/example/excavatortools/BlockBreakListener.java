package com.example.excavatortools;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BlockBreakListener implements Listener {

    private final Set<UUID> processingPlayers = new HashSet<>();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        Block origin = event.getBlock();
        ItemStack tool = player.getInventory().getItemInMainHand();

        if (!ExcavatorTools.isExcavatorTool(tool)) return;
        if (processingPlayers.contains(player.getUniqueId())) return;

        // Xác định mặt đào
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
        processingPlayers.add(player.getUniqueId());

        try {
            for (Block b : blocks) {
                if (b.equals(origin)) continue;
                if (b.getType() == Material.BEDROCK || b.getType() == Material.AIR) continue;

                // breakNaturally sẽ tự tạo BlockBreakEvent riêng, WorldGuard sẽ kiểm tra nếu có
                b.breakNaturally(tool);
            }
        } finally {
            processingPlayers.remove(player.getUniqueId());
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
