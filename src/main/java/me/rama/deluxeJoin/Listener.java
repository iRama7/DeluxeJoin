package me.rama.deluxeJoin;

import me.rama.deluxeJoin.actions.Action;
import me.rama.deluxeJoin.groups.Group;
import me.rama.deluxeJoin.groups.GroupManager;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listener implements org.bukkit.event.Listener {


    private DeluxeJoin plugin;
    private GroupManager groupManager;

    private final boolean disableDefaultMessages;

    public Listener(DeluxeJoin plugin){
        this.plugin = plugin;

        disableDefaultMessages = plugin.getConfig().getBoolean("config.disable-minecraft-messages");

    }

    @EventHandler
    public void joinEvent(PlayerJoinEvent event){

        if(disableDefaultMessages){
            event.joinMessage(null);
        }

        Player p = event.getPlayer();
        groupManager = plugin.getGroupManager();

        Group group = groupManager.getMostWeightGroup(p);


        if(group != null) {

            for (Action a : group.getJoin_actions()) {
                a.execute(p);

                if (plugin.debugMode()) {
                    plugin.log(LegacyComponentSerializer.legacyAmpersand().deserialize("&eExecuting join action &f" + a.getId() + " &efor player &f" + p.getName() + " &ein group &f" + group.getId()));
                }

            }
        }else{
            if(plugin.debugMode()){
                plugin.log(LegacyComponentSerializer.legacyAmpersand().deserialize("&eNo group could be found for the player &f" + p.getName()));
            }
        }

    }

    @EventHandler
    public void quitEvent(PlayerQuitEvent event){

        if (disableDefaultMessages) {
            event.quitMessage(null);
        }

        Player p = event.getPlayer();
        groupManager = plugin.getGroupManager();

        Group group = groupManager.getMostWeightGroup(p);

        if (group != null) {

            for (Action a : group.getQuit_actions()) {
                a.execute(p);

                if (plugin.debugMode()) {
                    plugin.log(LegacyComponentSerializer.legacyAmpersand().deserialize("&eExecuting quit action &f" + a.getId() + " &efor player &f" + p.getName() + " &ein group &f" + group.getId()));
                }

            }
        }else{
            if(plugin.debugMode()){
                plugin.log(LegacyComponentSerializer.legacyAmpersand().deserialize("&eNo group could be found for the player &f" + p.getName()));
            }
        }

    }

}
