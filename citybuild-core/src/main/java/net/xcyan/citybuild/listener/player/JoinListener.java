package net.xcyan.citybuild.listener.player;

import net.xcyan.citybuild.addon.Addon;
import net.xcyan.citybuild.handle.ScannerHandle;
import net.xcyan.citybuild.handle.ScoreboardHandle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public record JoinListener(Addon addon) implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        ScoreboardHandle.handle(player);
        ScannerHandle.handle(player);
    }
}
