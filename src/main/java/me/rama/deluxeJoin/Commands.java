package me.rama.deluxeJoin;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Commands implements CommandExecutor {

    private DeluxeJoin plugin;

    public Commands(DeluxeJoin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(args.length == 0){
            commandSender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&7 - &e/dj reload"));
        }

        if(args.length == 1 && args[0].equalsIgnoreCase("reload")){
            commandSender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&6[&aDeluxeJoin&6] &eReloading config..."));
            plugin.reloadConfig();
        }

        return false;
    }
}
