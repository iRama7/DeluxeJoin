package me.rama.deluxeJoin.actions;

import me.rama.deluxeJoin.DeluxeJoin;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundAction extends Action{

    protected Sound sound;
    protected float volume;
    protected float pitch;

    public SoundAction(String id, boolean broadcast, int delay, String sound, float volume, float pitch, DeluxeJoin plugin) {
        super(id, broadcast, delay, plugin);
        this.sound = Sound.valueOf(sound);
        this.volume = volume;
        this.pitch = pitch;
    }

    @Override
    public void execute(Player player) {

        scheduler.scheduleSyncDelayedTask(plugin, () -> {

            if(broadcast){
                for(Player p : Bukkit.getOnlinePlayers()){
                    p.playSound(p.getLocation(), sound, volume, pitch);
                }
            }else{
                player.playSound(player.getLocation(), sound, volume, pitch);
            }

        }, delay);

    }
}
