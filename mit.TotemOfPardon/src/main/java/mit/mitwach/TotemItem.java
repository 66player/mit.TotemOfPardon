package mit.mitwach;

import it.unimi.dsi.fastutil.Pair;
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

    public Pair<ItemStack, ItemMeta> createTotem() {
        Plugin plugin = TotemOfPardon.getPlugin(TotemOfPardon.class);

        ItemStack itemStack = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta itemMeta = itemStack.getItemMeta();


        itemMeta.addEnchant(Enchantment.DURABILITY, 100, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Item_Name")));
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "totemofpardon"), PersistentDataType.BYTE, (byte)0);

        List<String> lore = new ArrayList<>();
        for (int i = 0; i < plugin.getConfig().getList("Item_Lore").size(); i++) {
            lore.add(ChatColor.translateAlternateColorCodes('&', (String) plugin.getConfig().getList("Item_Lore").get(i)));
        }
        itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);

        return Pair.of(itemStack, itemMeta);
    }
}
