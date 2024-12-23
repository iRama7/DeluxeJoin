package me.rama.deluxeJoin.actions;

import me.clip.placeholderapi.PlaceholderAPI;
import me.rama.deluxeJoin.DeluxeJoin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAction extends Action{

    private String command;
    private String executor;

    public CommandAction(String id, boolean broadcast, int delay, String command, String executor, DeluxeJoin plugin) {
        super(id, broadcast, delay, plugin);

        this.command = command;
        this.executor = executor;
    }

    @Override
    public void execute(Player player) {

        String commandCopy = command;

        if(PapiHook){
            commandCopy = PlaceholderAPI.setPlaceholders(player, commandCopy);
        }

        String finalCommand = commandCopy;

        scheduler.scheduleSyncDelayedTask(plugin, () -> {


            CommandSender sender;
            if (executor.equals("PLAYER")) {
                sender = player;
            } else {
                sender = Bukkit.getConsoleSender();
            }

            Bukkit.dispatchCommand(sender, finalCommand);

        }, delay);
    }
}
