package rama.dj;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerJoinEventClass implements Listener {

    public DeluxeJoin plugin;

    public PlayerJoinEventClass(DeluxeJoin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        boolean disableJoinMessage = plugin.getConfig().getBoolean("config.disable-minecraft-join-message");
        if(disableJoinMessage){
            e.setJoinMessage(null);
        }
        Player p = e.getPlayer();
        String action = getPlayerAction(p);
        executeAction(action, p);
    }

    public String getPlayerAction(Player p){
        FileConfiguration config = plugin.getConfig();
        Set<String> joinActions = config.getConfigurationSection("join-actions").getKeys(false);
        List<String> playerActions = new ArrayList<>();
        for(String action : joinActions){
            String permission = config.getString("join-actions." + action + ".permission");
            if(p.hasPermission(permission)){
                playerActions.add(action);
            }
        }
        String mostPriorityAction = null;
        int previousPriority = 0;
        int currentPriority;
        for(String action : playerActions){
            currentPriority = config.getInt("join-actions." + action + ".priority");
            if(previousPriority == 0){
                previousPriority = config.getInt("join-actions." + action + ".priority");
            }else{
                if(currentPriority > previousPriority){
                    previousPriority = currentPriority;
                    mostPriorityAction = action;
                }
            }
        }
        return mostPriorityAction;
    }

    public void executeAction(String action, Player p){
        FileConfiguration config = plugin.getConfig();
        if(action == null){
            if(config.getBoolean("config.debug-mode")){
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&eDeluxeJoin&6] &eNo action found for &e") + p.getName());
            }
        }else{
            List<String> actions = config.getStringList("join-actions." + action + ".actions");
            for(String a : actions){
                //regex to get upperCase string inside brackets
                String regex = "\\[(.*?)\\]";
                Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
                Matcher matcher = pattern.matcher(a);
                String actionName = null;
                while(matcher.find()){
                    actionName = matcher.group(1);
                }
                //switch statement to check for actionName values

                switch(actionName){
                    case "MESSAGE":
                        //get the string without "[MESSAGE]"
                        String message = a.replace("[MESSAGE] ", "").replaceAll("%player%", p.getName());
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                        break;
                    case "COMMAND":
                        //get the string without "[COMMAND]"
                        String command = a.replace("[COMMAND] ", "").replaceAll("%player%", p.getName());
                        p.performCommand(command);
                        break;
                    case "CONSOLE":
                        //get the string without "[CONSOLE]"
                        String console = a.replace("[CONSOLE] ", "").replaceAll("%player%", p.getName());
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), console);
                        break;
                    case "BROADCAST":
                        //get the string without "[BROADCAST]"
                        String broadcast = a.replace("[BROADCAST] ", "").replaceAll("%player%", p.getName());
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', broadcast));
                        break;
                    case "TITLEMSG":
                        //Separate the string between /subtitle
                        String[] titleMsg = a.split("/subtitle");
                        //get the string without "[TITLEMSG]"
                        String title = titleMsg[0].replace("[TITLEMSG] ", "").replaceAll("%player%", p.getName());
                        String subtitle = titleMsg[1].replace("[TITLEMSG] ", "").replaceAll("%player%", p.getName());
                        p.sendTitle(colorized(title), colorized(subtitle), 20, 60, 20);
                        break;
                    case "TITLEBROADCAST":
                        //get the string without "[TITLEBROADCAST]"
                        String[] titleBroadcastString = a.split("/subtitle");
                        String titleBroadcast = titleBroadcastString[0].replace("[TITLEBROADCAST]", "").replaceAll("%player%", p.getName());
                        //get the string after /subtitle
                        String subtitleBroadcast = titleBroadcastString[1].replace("[TITLEBROADCAST]", "").replaceAll("%player%", p.getName());
                        //for each player online send title
                        for(Player player : Bukkit.getOnlinePlayers()) {
                            player.sendTitle(colorized(titleBroadcast), colorized(subtitleBroadcast), 20, 60, 20);
                        }
                        break;
                    case "SOUNDMSG":
                        //get the string without "[SOUNDMSG]"
                        String sound = a.replace("[SOUNDMSG] ", "");
                        p.playSound(p.getLocation(), Sound.valueOf(sound), 100, 1);
                        break;
                    case "SOUNDBROADCAST":
                        //get the string without "[SOUNDBROADCAST]"
                        String soundBroadcast = a.replace("[SOUNDBROADCAST] ", "");
                        //for each player online play sound
                        for(Player player : Bukkit.getOnlinePlayers()) {
                            player.playSound(player.getLocation(), Sound.valueOf(soundBroadcast), 1, 1);
                        }
                        break;
                    default:
                        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[&eDeluxeJoin&6] &eNo action type found for &e") + a);
                        break;
                }
            }
        }

    }

    public String colorized(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }


}
