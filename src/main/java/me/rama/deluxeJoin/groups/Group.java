package me.rama.deluxeJoin.groups;

import me.rama.deluxeJoin.actions.Action;

import java.util.List;

public class Group {

    private final String id;
    private final int weight;
    private final String permission;

    private final List<Action> join_actions;
    private final List<Action> quit_actions;

    public Group(String id, String permission, int weight, List<Action> join_actions, List<Action> quit_actions){
        this.id = id;
        this.permission = permission;
        this.weight = weight;
        this.join_actions = join_actions;
        this.quit_actions = quit_actions;
    }

    public String getPermission() {
        return permission;
    }

    public int getWeight() {
        return weight;
    }

    public List<Action> getJoin_actions() {
        return join_actions;
    }

    public List<Action> getQuit_actions() {
        return quit_actions;
    }

    public String getId() {
        return id;
    }
}
