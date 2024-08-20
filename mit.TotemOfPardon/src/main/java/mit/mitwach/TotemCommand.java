package mit.mitwach;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class TotemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length > 0 && (args[0].equals("reload") || args[0].equals("rl")) && (sender.hasPermission("mit.totemofpardon") || sender.isOp())) {
            try {
                Plugin plugin = TotemOfPardon.getPlugin(TotemOfPardon.class);
                new ConfigClass(plugin.getConfig()).reloadCf();
                sender.sendMessage("&aPlugin has been reloaded");
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }  else if (sender instanceof Player p && (p.hasPermission("mit.totemofpardon") || p.isOp())) {
            p.getInventory().addItem(new TotemItem().createTotem());
            p.sendMessage(ChatColor.GREEN + "You have received totem of pardon!");
        } else if (!sender.isOp() || !sender.hasPermission("mit.totemofpardon")) {
            sender.sendMessage(ChatColor.RED + "You haven't permission to !");
        }
        return true;
    }
}
