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
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("excavatortool.admin")) {
                sender.sendMessage("§cBạn không có quyền.");
                return true;
            }
            ExcavatorTools.getInstance().getConfigManager().reload();
            sender.sendMessage("§aConfig đã được reload. Debug: " + ExcavatorTools.getInstance().getConfigManager().isDebug());
            return true;
        }

        if (args[0].equalsIgnoreCase("give")) {
            if (!sender.hasPermission("excavatortool.admin")) {
                sender.sendMessage("§cBạn không có quyền.");
                return true;
            }
            if (args.length < 3) {
                sender.sendMessage("§cDùng: /" + label + " give <player> <type>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("§cNgười chơi không tồn tại.");
                return true;
            }

            String type = args[2].toLowerCase();
            ItemStack tool = createToolFromType(type);
            if (tool == null) {
                sender.sendMessage("§cLoại không hợp lệ. Các loại: iron_pickaxe, iron_shovel, diamond_pickaxe, diamond_shovel, netherite_pickaxe, netherite_shovel");
                return true;
            }

            target.getInventory().addItem(tool);
            sender.sendMessage("§aĐã đưa Excavator " + type + " cho " + target.getName());
            ExcavatorTools.getInstance().debug("Given tool " + type + " to " + target.getName() + ", has key: " + ExcavatorTools.isExcavatorTool(tool));
            return true;
        }

        sendHelp(sender);
        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage("§e--- ExcavatorTools Help ---");
        sender.sendMessage("§e/xt give <player> <type> §7- Tặng công cụ đào 3x3");
        sender.sendMessage("§e/xt reload §7- Tải lại config.yml");
    }

    private ItemStack createToolFromType(String type) {
        switch (type) {
            case "iron_pickaxe": return ExcavatorTool.createTool(Material.IRON_PICKAXE, "Iron Pickaxe");
            case "iron_shovel": return ExcavatorTool.createTool(Material.IRON_SHOVEL, "Iron Shovel");
            case "diamond_pickaxe": return ExcavatorTool.createTool(Material.DIAMOND_PICKAXE, "Diamond Pickaxe");
            case "diamond_shovel": return ExcavatorTool.createTool(Material.DIAMOND_SHOVEL, "Diamond Shovel");
            case "netherite_pickaxe": return ExcavatorTool.createTool(Material.NETHERITE_PICKAXE, "Netherite Pickaxe");
            case "netherite_shovel": return ExcavatorTool.createTool(Material.NETHERITE_SHOVEL, "Netherite Shovel");
            default: return null;
        }
    }
}
