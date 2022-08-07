package rama.dj;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {

    public DeluxeJoin plugin;

    public Commands(DeluxeJoin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage("/dj reload");
        } else if(args[0].equals("reload") && sender.hasPermission("deluxejoin.admin")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&eDeluxeJoin&6] &aReloading config"));
            plugin.reloadConfig();
        }
        return false;
    }
}
