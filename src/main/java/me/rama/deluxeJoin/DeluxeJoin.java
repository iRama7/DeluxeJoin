package me.rama.deluxeJoin;

import me.rama.deluxeJoin.groups.GroupManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;


public final class DeluxeJoin extends JavaPlugin {


    private final TextComponent prefix = Component.text("[").color(NamedTextColor.GOLD)
            .append(Component.text("DeluxeJoin").color(NamedTextColor.GREEN)
                    .append(Component.text("]").color(NamedTextColor.GOLD))
                    .appendSpace());

    private boolean PaPiHook = false;

    private FileConfiguration config;
    private GroupManager groupManager;

    @Override
    public void onEnable() {

        log(Component.text("Enabling...").color(NamedTextColor.GREEN));

        initPaPiHook();

        registerEvents();

        saveDefaultConfig();

        config = this.getConfig();

        groupManager = new GroupManager(this);

        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void initPaPiHook(){
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            PaPiHook = true;
            log(Component.text("PlaceholderAPI").color(NamedTextColor.WHITE)
                    .append(Component.text(" detected! Enabling PaPi hook.").color(NamedTextColor.GREEN)));
        }
    }

    private void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new Listener(this), this);
    }

    private void registerCommands(){
        this.getCommand("dj").setExecutor(new Commands(this));
    }

    public void log(TextComponent text){
        Bukkit.getConsoleSender().sendMessage(prefix.append(text));
        

    }

    public FileConfiguration getConfiguration(){
        return config;
    }

    public GroupManager getGroupManager() {
        return groupManager;
    }

    public boolean debugMode(){
        return config.getBoolean("config.debug");
    }
}
