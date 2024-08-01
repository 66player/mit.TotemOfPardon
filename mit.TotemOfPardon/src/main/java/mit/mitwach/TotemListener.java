package mit.mitwach;

import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TotemListener implements Listener {

    public static ItemStack[] savedInventory;
    public static ItemStack[] savedArmor;
    public static ItemStack savedOffHand;
    public static float pExp;
    public static boolean hasPlayerTotemOfPardon;

    @EventHandler
    public static void onTotemPopup(EntityResurrectEvent e) {
        Plugin plugin = TotemOfPardon.getPlugin(TotemOfPardon.class);
        if (e.getEntity().getType().equals(EntityType.PLAYER)) {
            Player p = (Player) e.getEntity();
            ItemStack itemInMainHand = p.getInventory().getItemInMainHand();
            ItemStack itemInOffHand = p.getInventory().getItemInOffHand();
            if ((itemInMainHand.hasItemMeta() && itemInMainHand.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "totemofpardon"), PersistentDataType.BYTE))) {
                Location loc = p.getLocation();
                p.getInventory().setItemInMainHand(null);
                savedInventory = p.getInventory().getContents();
                savedArmor = p.getInventory().getArmorContents();
                savedOffHand = p.getInventory().getItemInOffHand();
                pExp = p.getExp();
                p.getInventory().clear();
                hasPlayerTotemOfPardon = true;
                e.setCancelled(true);
                p.getWorld().strikeLightningEffect(loc);
                if (plugin.getConfig().getBoolean("Boss_Bar_Enabled")) {
                    showBossBar(p);
                }
            } else if (itemInOffHand.hasItemMeta() && itemInOffHand.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(plugin, "totemofpardon"), PersistentDataType.BYTE)) {
                Location loc = p.getLocation();
                p.getInventory().setItemInOffHand(null);
                savedInventory = p.getInventory().getContents();
                savedArmor = p.getInventory().getArmorContents();
                savedOffHand = p.getInventory().getItemInOffHand();
                pExp = p.getExp();
                p.getInventory().clear();
                hasPlayerTotemOfPardon = true;
                e.setCancelled(true);
                p.getWorld().strikeLightningEffect(loc);
                if (plugin.getConfig().getBoolean("Boss_Bar_Enabled")) {
                    showBossBar(p);
                }
            }
        }
    }

    @EventHandler
    public static void onPlayerRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (hasPlayerTotemOfPardon) {
            p.getInventory().setContents(savedInventory);
            p.getInventory().setArmorContents(savedArmor);
            p.getInventory().setItemInOffHand(savedOffHand);
            p.setExp(pExp);
            hasPlayerTotemOfPardon = false;
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.GREEN + "Totem Of Pardon by 0nyc")) {
            event.setCancelled(false);
        }
    }

    public static void showBossBar(Player player) {
        Plugin plugin = TotemOfPardon.getPlugin(TotemOfPardon.class);
        String message = plugin.getConfig().getString("Boss_Bar_Message", "Your boss bar message here!");
        message = message.replace("{PLAYER}", player.getDisplayName());
        message = ChatColor.translateAlternateColorCodes('&', message);

        String barColorString = plugin.getConfig().getString("Boss_Bar_Color", "PURPLE");
        BarColor barColor;
        barColor = BarColor.valueOf(barColorString.toUpperCase());


        BossBar bossBar = Bukkit.createBossBar(message, barColor, BarStyle.SEGMENTED_12, BarFlag.PLAY_BOSS_MUSIC, BarFlag.DARKEN_SKY);
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(player1);
        }
        bossBar.setProgress(1.0);

        new BukkitRunnable() {
            @Override
            public void run() {
                double progress = bossBar.getProgress();
                if (progress < 0.05) {
                    bossBar.removeAll();
                    bossBar.removeFlag(BarFlag.PLAY_BOSS_MUSIC);
                    bossBar.removeFlag(BarFlag.DARKEN_SKY);
                    bossBar.setVisible(false);
                    this.cancel();
                } else {
                    bossBar.setProgress(Math.max(0, progress - 0.05));
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }
}
