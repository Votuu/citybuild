package net.xcyan.citybuild.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.xcyan.citybuild.addon.Addon;
import net.xcyan.citybuild.api.CommandAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GlowCommand extends CommandAPI {

    public GlowCommand(Addon addon) {
        super(addon, "glow", "enable/disable our glow skill");
    }

    @Override
    public void single(CommandSender sender, String[] args) {
        if(!(sender instanceof Player player))return;

        if(!(player.hasPermission("citybuild.glow"))) {
            return;
        }

        if(player.isGlowing()) {
            player.setGlowing(false);

            player.sendMessage(addon.prefix().append(Component.text("Du hast Leuchten ", NamedTextColor.GRAY).append(Component.text("deaktiviert", addon.color())).append(Component.text(".", NamedTextColor.GRAY))));
            return;
        }
        player.setGlowing(true);

        player.sendMessage(addon.prefix().append(Component.text("Du hast Leuchten ", NamedTextColor.GRAY).append(Component.text("aktiviert", addon.color())).append(Component.text(".", NamedTextColor.GRAY))));
    }
}
