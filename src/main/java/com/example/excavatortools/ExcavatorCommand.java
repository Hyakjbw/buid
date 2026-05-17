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
        if (args.length < 2 || !args[0].equalsIgnoreCase("give")) return false;

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage("§cNgười chơi không tồn tại.");
            return true;
        }

        String type = args.length >= 3 ? args[2].toLowerCase() : "pickaxe";
        ItemStack tool = null;

        if (type.equals("pickaxe")) {
            tool = ExcavatorTool.createTool(Material.DIAMOND_PICKAXE, "Pickaxe");
        } else if (type.equals("shovel")) {
            tool = ExcavatorTool.createTool(Material.DIAMOND_SHOVEL, "Shovel");
        } else {
            sender.sendMessage("§cLoại công cụ không hợp lệ. Dùng pickaxe hoặc shovel.");
            return true;
        }

        target.getInventory().addItem(tool);
        sender.sendMessage("§aĐã đưa " + type + " 3x3 cho " + target.getName());
        return true;
    }
}
