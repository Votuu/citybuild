package net.xcyan.citybuild.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.xcyan.citybuild.addon.Addon;
import net.xcyan.citybuild.api.CommandAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand extends CommandAPI {

    public FlyCommand(Addon addon) {
        super(addon, "fly", "enable/disable our fly skill");
    }

    @Override
    public void single(CommandSender sender, String[] args) {
        if(!(sender instanceof Player player))return;

        if(!(player.hasPermission("citybuild.fly"))) {
            return;
        }

        if(player.getAllowFlight()) {
            player.setFlying(false);
            player.setAllowFlight(false);

            player.sendMessage(addon.prefix().append(Component.text("Du hast Fliegen ", NamedTextColor.GRAY).append(Component.text("deaktiviert", addon.color())).append(Component.text(".", NamedTextColor.GRAY))));
            return;
        }
        player.setAllowFlight(true);
        player.setFlying(true);

        player.sendMessage(addon.prefix().append(Component.text("Du hast Fliegen ", NamedTextColor.GRAY).append(Component.text("aktiviert", addon.color())).append(Component.text(".", NamedTextColor.GRAY))));
    }
}
