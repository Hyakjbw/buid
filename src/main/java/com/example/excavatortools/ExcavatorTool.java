package com.example.excavatortools;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class ExcavatorTool {

    public static ItemStack createTool(Material material, String typeName) {
        ItemStack tool = new ItemStack(material);
        ItemMeta meta = tool.getItemMeta();
        meta.setDisplayName("§e⛏ Excavator " + typeName);
        meta.setLore(List.of("§7Đào vùng 3x3", "§7Độ bền cao hơn 1.5 lần"));
        // Gắn tag nhận diện
        NamespacedKey key = new NamespacedKey(ExcavatorTools.getInstance(), "excavator");
        meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);

        // Thiết lập độ bền cao hơn 1.5 lần
        if (meta instanceof Damageable damageable) {
            int defaultMax = material.getMaxDurability();
            int newMax = (int) (defaultMax * 1.5);
            damageable.setMaxDamage(newMax);
            damageable.setDamage(0); // mới hoàn toàn
        }

        tool.setItemMeta(meta);
        return tool;
    }
}
