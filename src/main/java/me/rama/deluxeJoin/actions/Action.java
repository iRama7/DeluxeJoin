package me.rama.deluxeJoin.actions;

import me.rama.deluxeJoin.DeluxeJoin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class Action {

    protected final boolean broadcast;
    protected boolean PapiHook;
    protected int delay;
    protected BukkitScheduler scheduler;
    protected DeluxeJoin plugin;
    protected String id;

    public Action(String id, boolean broadcast, int delay, DeluxeJoin plugin){
        this.broadcast = broadcast;
        this.delay = delay;
        this.plugin = plugin;
        this.id = id;

        scheduler = Bukkit.getScheduler();
        PapiHook = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;

    }

    public abstract void execute(Player player);

    public String getId() {
        return id;
    }
}
