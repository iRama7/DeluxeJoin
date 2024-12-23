package me.rama.deluxeJoin.actions;

import me.clip.placeholderapi.PlaceholderAPI;
import me.rama.deluxeJoin.DeluxeJoin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Duration;

public class TitleAction extends Action{

    protected String title;
    protected String subtitle;
    protected int fadeIn;
    protected int stay;
    protected int fadeOut;

    public TitleAction(String id, boolean broadcast, int delay, String title, String subtitle, int fadeIn, int stay, int fadeOut, DeluxeJoin plugin) {
        super(id, broadcast, delay, plugin);
        this.title = title;
        this.subtitle = subtitle;

        this.fadeIn = fadeIn / 20;
        this.stay = stay / 20;
        this.fadeOut = fadeOut / 20;

    }

    @Override
    public void execute(Player player) {

        String titleCopy = title;
        String subtitleCopy = subtitle;

        if(PapiHook){
            titleCopy = PlaceholderAPI.setPlaceholders(player, titleCopy);
            subtitleCopy = PlaceholderAPI.setPlaceholders(player, subtitleCopy);
        }

        Component titleComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(titleCopy);
        Component subtitleComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(subtitleCopy);

        Title ComponentTitle = Title.title(
                titleComponent,
                subtitleComponent,
                Title.Times.times(Duration.ofSeconds(fadeIn), Duration.ofSeconds(stay), Duration.ofSeconds(fadeOut))
        );

        scheduler.scheduleSyncDelayedTask(plugin, () -> {

            if(broadcast){
                for(Player p : Bukkit.getOnlinePlayers()){
                    p.showTitle(ComponentTitle);
                }
            }else{
                player.showTitle(ComponentTitle);
            }

        }, delay);
    }

}
