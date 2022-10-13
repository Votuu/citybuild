package net.xcyan.citybuild.addon;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.xcyan.citybuild.api.CommandAPI;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.UUID;

public abstract class Addon extends JavaPlugin {

    private final UUID addonId;
    private String name;
    private TextColor color;

    public Addon(String name, TextColor color) {
        this.addonId = UUID.randomUUID();
        this.name = name;
        this.color = color;
    }

    @Override
    public void onLoad() {
        load();
        super.onLoad();
    }

    @Override
    public void onEnable() {
        enable();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        disable();
        super.onDisable();
    }

    public UUID addonId() {
        return addonId;
    }

    public String name() {
        return name;
    }
    public void name(String name) {
        this.name = name;
    }

    public TextColor color() {
        return color;
    }
    public void color(TextColor color) {
        this.color = color;
    }

    public abstract void load();
    public abstract void enable();
    public abstract void disable();

    public Component prefix() {
        return Component.text(" \u25cf ", NamedTextColor.DARK_GRAY)
                .append(Component.text(name(), color()))
                .append(Component.text(" | ", NamedTextColor.DARK_GRAY))
                .append(Component.text("", NamedTextColor.GRAY));
    }

    public void registerCommand(CommandAPI commandAPI) {
        Objects.requireNonNull(getCommand(commandAPI.label().toLowerCase())).setExecutor(commandAPI);
    }
    public void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }
}
