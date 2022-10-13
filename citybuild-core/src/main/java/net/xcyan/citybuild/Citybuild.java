package net.xcyan.citybuild;

import net.kyori.adventure.text.format.TextColor;
import net.xcyan.citybuild.addon.Addon;
import net.xcyan.citybuild.command.FlyCommand;
import net.xcyan.citybuild.command.GlowCommand;
import net.xcyan.citybuild.command.ScannerCommand;
import net.xcyan.citybuild.listener.player.JoinListener;

public class Citybuild extends Addon {

    public Citybuild() {
        super("Citybuild", TextColor.color(0x5555FF));
    }

    @Override
    public void load() {

    }

    @Override
    public void enable() {
        registerListener(new JoinListener(this));

        registerCommand(new ScannerCommand(this));
        registerCommand(new FlyCommand(this));
        registerCommand(new GlowCommand(this));
    }

    @Override
    public void disable() {

    }
}
