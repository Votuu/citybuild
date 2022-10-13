package net.xcyan.citybuild.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.xcyan.citybuild.addon.Addon;
import net.xcyan.citybuild.api.CommandAPI;
import net.xcyan.citybuild.handle.ScannerHandle;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ScannerCommand extends CommandAPI {

    public ScannerCommand(Addon addon) {
        super(addon, "scanner", "Manage our scanner handle");

        functionMap.put("cancel", new CancelFunction());
    }

    @Override
    public void single(CommandSender sender, String[] args) {

    }

    private static class CancelFunction implements Function {

        @Override
        public void execute(CommandSender sender, String[] args) {
            if(!(sender instanceof Player player))return;

            ScannerHandle handle = ScannerHandle.handle(player);

            if(handle.citybuildScanner() == null) {
                player.sendMessage(handle.prefix().append(Component.text("Es läuft aktuell keine Chateingabe.", NamedTextColor.RED)));
                return;
            }

            handle.cancel();
        }

        @Override
        public String description() {
            return "cancel the current input action";
        }

        @Override
        public String suggest() {
            return "cancel";
        }
    }
}
