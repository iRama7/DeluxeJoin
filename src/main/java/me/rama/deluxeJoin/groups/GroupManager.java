package me.rama.deluxeJoin.groups;


import me.rama.deluxeJoin.DeluxeJoin;
import me.rama.deluxeJoin.actions.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GroupManager {

    private List<Group> groups;
    private DeluxeJoin plugin;
    private FileConfiguration config;

    public GroupManager(DeluxeJoin plugin){
        groups = new ArrayList<>();
        this.plugin = plugin;
        config = plugin.getConfig();

        initGroups();
    }

    private void initGroups(){

        ConfigurationSection groups_section = config.getConfigurationSection("groups");

        if(groups_section == null){
            plugin.log(Component.text("ERROR ").color(NamedTextColor.DARK_RED).decorate(TextDecoration.BOLD).append(Component.text("Could not find any groups defined in config. Disabling...").color(NamedTextColor.RED)));
            Bukkit.getPluginManager().disablePlugin(plugin);
            return;
        }

        for(String group_name : groups_section.getKeys(false)){
            String id = group_name;
            int weight = groups_section.getInt(group_name + ".weight");
            String permssion = groups_section.getString(group_name + ".permission");



            //Build join actions
            List<Action> join_actions = new ArrayList<>();
            ConfigurationSection join_actions_section = config.getConfigurationSection("groups." + group_name + ".join-actions");
            if(join_actions_section != null) {
                for (String action_name : join_actions_section.getKeys(false)) {
                    join_actions.add(buildAction(join_actions_section, action_name));
                }
            }

            //Build quit actions
            List<Action> quit_actions = new ArrayList<>();
            ConfigurationSection quit_actions_section = config.getConfigurationSection("groups." + group_name + ".quit-actions");
            if(quit_actions_section != null){
                for(String action_name : quit_actions_section.getKeys(false)){
                    quit_actions.add(buildAction(quit_actions_section, action_name));
                }
            }

            Group group = new Group(id, permssion, weight, join_actions, quit_actions);
            groups.add(group);
        }

        plugin.log(Component.text("Successfully loaded ").color(NamedTextColor.GREEN)
                .append(Component.text(groups.size()).color(NamedTextColor.RED))
                .append(Component.text(" groups!").color(NamedTextColor.GREEN)));

    }

    private Action buildAction(ConfigurationSection section, String action_name){

        Action action = null;

        ActionType type = null;

        try {
            type = ActionType.valueOf(section.getString(action_name + ".type"));
        } catch (IllegalArgumentException e) {
            plugin.log(Component.text("ERROR ").color(NamedTextColor.DARK_RED).decorate(TextDecoration.BOLD).append(Component.text(e.getCause().getMessage()).color(NamedTextColor.RED)));
        }

        if(type == null){
            return null;
        }
        boolean broadcast = section.getBoolean(action_name + ".broadcast");
        int delay = section.getInt(action_name + ".delay");
        String id = action_name;

        switch (type){
            case MESSAGE -> {
                String message = section.getString(action_name + ".message");
                action = new MessageAction(id, broadcast, delay, message, plugin);
            }

            case TITLE -> {

                String title = section.getString(action_name + ".title");
                String subtitle = section.getString(action_name + ".subtitle");
                int fadeIn = section.getInt(action_name + ".times.fadeIn");
                int stay = section.getInt(action_name + ".times.stay");
                int fadeOut = section.getInt(action_name + ".times.fadeOut");

                action = new TitleAction(id, broadcast, delay, title, subtitle, fadeIn, stay, fadeOut, plugin);
            }

            case SOUND -> {

                String sound = section.getString(action_name + ".sound");
                float volume = (float) section.getDouble(action_name + ".volume");
                float pitch = (float) section.getDouble(action_name + ".pitch");

                action = new SoundAction(id, broadcast, delay, sound, volume, pitch, plugin);
            }

            case COMMAND -> {
                String executor = section.getString(action_name + ".executor");
                String command = section.getString(action_name + ".command");
                action = new CommandAction(id, broadcast, delay, command, executor, plugin);
            }

            case ACTIONBAR -> {
                String message = section.getString(action_name + ".message");
                action = new ActionbarAction(id, broadcast, delay, message, plugin);
            }

        }

        return action;

    }

    public Group getMostWeightGroup(Player player){

        Group mostWeightGroup = null;

        for(Group group : groups){
            if(player.hasPermission(group.getPermission())){
                if(mostWeightGroup == null){
                    mostWeightGroup = group;
                }else{
                    if(mostWeightGroup.getWeight() < group.getWeight()){
                        mostWeightGroup = group;
                    }
                }
            }
        }

        return mostWeightGroup;

    }

}
