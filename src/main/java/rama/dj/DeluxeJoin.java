package rama.dj;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeluxeJoin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new PlayerJoinEventClass(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitEventClass(this), this);
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&eDeluxeJoin&6] &aPlugin enabled"));
        Bukkit.getPluginCommand("deluxejoin").setExecutor(new Commands(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
