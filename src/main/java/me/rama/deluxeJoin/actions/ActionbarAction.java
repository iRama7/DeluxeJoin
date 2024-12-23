package me.rama.deluxeJoin.actions;

import me.clip.placeholderapi.PlaceholderAPI;
import me.rama.deluxeJoin.DeluxeJoin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ActionbarAction extends Action{

    private String message;

    public ActionbarAction(String id, boolean broadcast, int delay, String message, DeluxeJoin plugin) {
        super(id, broadcast, delay, plugin);
        this.message = message;
    }

    @Override
    public void execute(Player player) {

        String messageCopy = message;

        if(PapiHook){
            messageCopy = PlaceholderAPI.setPlaceholders(player, messageCopy);
        }

        Component messageComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(messageCopy);

        scheduler.scheduleSyncDelayedTask(plugin, () -> {

            if(broadcast){
                for(Player p : Bukkit.getOnlinePlayers()){
                    p.sendActionBar(messageComponent);
                }
            }else{
                player.sendActionBar(messageComponent);
            }

        }, delay);

    }

}
