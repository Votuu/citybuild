package net.xcyan.citybuild.handle;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.xcyan.citybuild.Citybuild;
import net.xcyan.citybuild.addon.Addon;
import net.xcyan.citybuild.api.scanner.CitybuildScanner;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ScannerHandle implements Listener {

    private static Map<Player, ScannerHandle> handleMap = new HashMap<>();

    public static ScannerHandle handle(Player player) {
        if(handleMap.containsKey(player)) {
            return handleMap.get(player);
        }

        return new ScannerHandle(player);
    }
    private Player player;
    private CitybuildScanner citybuildScanner;

    public ScannerHandle(Player player) {
        this.player = player;

        handleMap.put(player, this);
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(Citybuild.class));
    }

    public void handler(CitybuildScanner citybuildScanner) {
        if(this.citybuildScanner != null) {
            player.sendMessage("debug");
            return;
        }
        this.citybuildScanner = citybuildScanner;

        player.sendMessage(prefix().append(Component.text(citybuildScanner.message(), NamedTextColor.GRAY))
                .append(Component.text(" [", NamedTextColor.DARK_GRAY))
                .append(Component.text("Abbrechen", NamedTextColor.RED).clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/scanner cancel")))
                .append(Component.text("]", NamedTextColor.DARK_GRAY)));
    }

    public void cancel() {
        if(citybuildScanner == null) {
            return;
        }

        citybuildScanner.cancel();
        player.sendMessage(prefix()
                .append(Component.text("Die Chateingabe wurde ", NamedTextColor.GRAY))
                .append(Component.text("abgebrochen", NamedTextColor.RED))
                .append(Component.text(".", NamedTextColor.GRAY)));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if(event.getPlayer() != player) {
            return;
        }

        if(citybuildScanner != null) {
            cancel();
        }
        citybuildScanner = null;
        handleMap.remove(player);
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if(event.getPlayer() != player) {
            return;
        }

        if(citybuildScanner == null) {
            return;
        }

        citybuildScanner.result(LegacyComponentSerializer.legacyAmpersand().serialize(event.message()));
        citybuildScanner.ready();

        citybuildScanner = null;
    }

    @Nullable
    public CitybuildScanner citybuildScanner() {
        return citybuildScanner;
    }

    public Component prefix() {
        return Component.text(" \u25cf ", NamedTextColor.DARK_GRAY)
                .append(Component.text("Scanner", TextColor.color(0x22ebbd)))
                .append(Component.text(" | ", NamedTextColor.DARK_GRAY));
    }
}
