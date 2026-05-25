package com.example.excavatortools;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            sender.sendMessage("§aConfig đã được reload.");
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
        Material material;
        String displayName;
        List<String> lore = new ArrayList<>();
        Map<Enchantment, Integer> enchants = new java.util.HashMap<>();

        switch (type) {
            case "iron_pickaxe":
                material = Material.IRON_PICKAXE;
                displayName = "§aCuốc Sắt 3x3";
                lore.add("§7Đào 3x3 siêu tốc!");
                lore.add("§bEfficiency IV | Unbreaking III | Fortune II | Mending");
                lore.add("§eTỉ lệ: §c10%");
                enchants.put(Enchantment.EFFICIENCY, 4);
                enchants.put(Enchantment.UNBREAKING, 3);
                enchants.put(Enchantment.FORTUNE, 2);
                enchants.put(Enchantment.MENDING, 1);
                break;

            case "iron_shovel":
                material = Material.IRON_SHOVEL;
                displayName = "§aXẻng Sắt 3x3";
                lore.add("§7Đào 3x3 siêu nhanh!");
                lore.add("§bEfficiency IV | Unbreaking III | Fortune II | Mending");
                lore.add("§eTỉ lệ: §c10%");
                enchants.put(Enchantment.EFFICIENCY, 4);
                enchants.put(Enchantment.UNBREAKING, 3);
                enchants.put(Enchantment.FORTUNE, 2);
                enchants.put(Enchantment.MENDING, 1);
                break;

            case "diamond_pickaxe":
                material = Material.DIAMOND_PICKAXE;
                displayName = "§e⛏ Excavator Diamond Pickaxe";
                lore.add("§7Đào vùng 3x3");
                lore.add("§7Độ bền cao hơn 1.5 lần");
                // Không enchant mặc định cho diamond, nhưng bạn có thể thêm nếu muốn
                break;

            case "diamond_shovel":
                material = Material.DIAMOND_SHOVEL;
                displayName = "§e⛏ Excavator Diamond Shovel";
                lore.add("§7Đào vùng 3x3");
                lore.add("§7Độ bền cao hơn 1.5 lần");
                break;

            case "netherite_pickaxe":
                material = Material.NETHERITE_PICKAXE;
                displayName = "§8Cuốc Netherite 3x3";
                lore.add("§6✦ Công Cụ Netherite Tối Thượng ✦");
                lore.add("§bEfficiency V §7| §fUnbreaking III");
                lore.add("§6Fortune III §7| §bMending");
                lore.add("§7Đào 3x3 siêu tốc!");
                lore.add("§eTỉ lệ: §c15%");
                enchants.put(Enchantment.EFFICIENCY, 5);
                enchants.put(Enchantment.UNBREAKING, 3);
                enchants.put(Enchantment.FORTUNE, 3);
                enchants.put(Enchantment.MENDING, 1);
                break;

            case "netherite_shovel":
                material = Material.NETHERITE_SHOVEL;
                displayName = "§8Xẻng Netherite 3x3";
                lore.add("§6✦ Công Cụ Netherite Tối Thượng ✦");
                lore.add("§bEfficiency V §7| §fUnbreaking III");
                lore.add("§6Fortune III §7| §bMending");
                lore.add("§7Đào 3x3 siêu nhanh!");
                lore.add("§eTỉ lệ: §c15%");
                enchants.put(Enchantment.EFFICIENCY, 5);
                enchants.put(Enchantment.UNBREAKING, 3);
                enchants.put(Enchantment.FORTUNE, 3);
                enchants.put(Enchantment.MENDING, 1);
                break;

            default:
                return null;
        }

        // Tạo ItemStack
        ItemStack tool = new ItemStack(material);
        ItemMeta meta = tool.getItemMeta();

        // Tên và lore
        meta.setDisplayName(displayName);
        meta.setLore(lore);

        // Gắn tag nhận diện Excavator
        NamespacedKey key = ExcavatorTools.getExcavatorKey();
        meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);

        // Độ bền cao hơn 1.5 lần
        if (meta instanceof Damageable damageable) {
            int defaultMax = material.getMaxDurability();
            int newMax = (int) (defaultMax * 1.5);
            damageable.setMaxDamage(newMax);
            damageable.setDamage(0);
        }

        tool.setItemMeta(meta);

        // Thêm enchantment (sau khi setItemMeta để an toàn, nhưng có thể thêm trực tiếp)
        for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
            tool.addUnsafeEnchantment(entry.getKey(), entry.getValue());
        }

        return tool;
    }
}
