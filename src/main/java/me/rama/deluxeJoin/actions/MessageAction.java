package me.rama.deluxeJoin.actions;

import me.clip.placeholderapi.PlaceholderAPI;
import me.rama.deluxeJoin.DeluxeJoin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageAction extends Action{

    protected String message;

    public MessageAction(String id, boolean broadcast, int delay, String message, DeluxeJoin plugin) {
        super(id, broadcast, delay, plugin);
        this.message = message;
    }

    @Override
    public void execute(Player player) {

        String messageCopy = message;

        if(PapiHook){
            messageCopy = PlaceholderAPI.setPlaceholders(player, messageCopy);
            Bukkit.getLogger().info("Parsing message " + player.getName());
        }

        Component component = LegacyComponentSerializer.legacyAmpersand().deserialize(messageCopy);

        scheduler.scheduleSyncDelayedTask(plugin, () -> {

            if(broadcast){
                for(Player p : Bukkit.getOnlinePlayers()){
                    p.sendMessage(component);
                }
            }else{
                player.sendMessage(component);
            }

        },delay);
    }

}
