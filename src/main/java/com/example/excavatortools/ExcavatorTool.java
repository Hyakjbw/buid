package com.example.excavatortools;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class ExcavatorTool {

    public static ItemStack createTool(Material material, String typeName) {
        ItemStack tool = new ItemStack(material);
        ItemMeta meta = tool.getItemMeta();
        meta.setDisplayName("§e⛏ Excavator " + typeName);
        meta.setLore(List.of("§7Đào vùng 3x3"));
        meta.setUnbreakable(true);
        // Gắn tag để nhận diện
        NamespacedKey key = new NamespacedKey(ExcavatorTools.getInstance(), "excavator");
        meta.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);
        tool.setItemMeta(meta);
        return tool;
    }
}
