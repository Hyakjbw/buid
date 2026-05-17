package com.example.excavatortools;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ExcavatorCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 2 || !args[0].equalsIgnoreCase("give")) {
            sender.sendMessage("§cCách dùng: /excavatortool give <player> <type>");
            sender.sendMessage("§cVí dụ type: iron_pickaxe, diamond_shovel, netherite_pickaxe...");
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage("§cNgười chơi không tồn tại.");
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage("§cBạn phải chỉ định loại công cụ.");
            return true;
        }

        String type = args[2].toLowerCase();
        ItemStack tool = createToolFromType(type);

        if (tool == null) {
            sender.sendMessage("§cLoại không hợp lệ. Các loại hỗ trợ:");
            sender.sendMessage("§7iron_pickaxe, iron_shovel, diamond_pickaxe, diamond_shovel, netherite_pickaxe, netherite_shovel");
            return true;
        }

        target.getInventory().addItem(tool);
        sender.sendMessage("§aĐã đưa Excavator " + type + " cho " + target.getName());
        return true;
    }

    private ItemStack createToolFromType(String type) {
        switch (type) {
            case "iron_pickaxe":
                return ExcavatorTool.createTool(Material.IRON_PICKAXE, "Iron Pickaxe");
            case "iron_shovel":
                return ExcavatorTool.createTool(Material.IRON_SHOVEL, "Iron Shovel");
            case "diamond_pickaxe":
                return ExcavatorTool.createTool(Material.DIAMOND_PICKAXE, "Diamond Pickaxe");
            case "diamond_shovel":
                return ExcavatorTool.createTool(Material.DIAMOND_SHOVEL, "Diamond Shovel");
            case "netherite_pickaxe":
                return ExcavatorTool.createTool(Material.NETHERITE_PICKAXE, "Netherite Pickaxe");
            case "netherite_shovel":
                return ExcavatorTool.createTool(Material.NETHERITE_SHOVEL, "Netherite Shovel");
            default:
                return null;
        }
    }
}
