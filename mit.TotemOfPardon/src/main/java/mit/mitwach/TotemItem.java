package mit.mitwach;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class TotemItem {

    public ItemStack createTotem() {
        Plugin plugin = TotemOfPardon.getPlugin(TotemOfPardon.class);

        ItemStack itemStack = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta itemMeta = itemStack.getItemMeta();


        if (itemMeta != null) {
            itemMeta.addEnchant(Enchantment.DURABILITY, 100, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Item_Name")));
            itemMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "totemofpardon"), PersistentDataType.BYTE, (byte)0);

            List<String> lore = new ArrayList<>();
            List<String> configLore = plugin.getConfig().getStringList("Item_Lore");
            for (String line : configLore) {
                lore.add(ChatColor.translateAlternateColorCodes('&', line));
            }
            itemMeta.setLore(lore);

            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }
}
