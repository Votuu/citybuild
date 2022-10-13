package net.xcyan.citybuild.api;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.xcyan.citybuild.addon.Addon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CommandAPI implements CommandExecutor, TabCompleter {

    protected final Addon addon;
    protected final Map<String, Function> functionMap;

    protected String label;
    protected String description;

    public CommandAPI(Addon addon, String label, String description) {
        this.addon = addon;
        this.functionMap = new HashMap<>();

        this.label = label;
        this.description = description;
    }

    protected interface Function {
        void execute(CommandSender sender, String[] args);
        String description();

        default String suggest() {
            return "$none";
        }
        default boolean hasPermission(CommandSender sender) {
            return true;
        }
    }

    public abstract void single(CommandSender sender, String[] args);

    protected void useUsage() {
        functionMap.put("usage", new Function() {
            @Override
            public void execute(CommandSender sender, String[] args) {
                usage(sender);
            }

            @Override
            public String description() {
                return "Shows all usages of command";
            }

            @Override
            public String suggest() {
                return "usage";
            }
        });
    }

    private void usage(CommandSender sender) {
        sender.sendMessage(addon.prefix().append(Component.text("Hilfe für ", NamedTextColor.GRAY))
                .append(Component.text("/" + label.toLowerCase(), addon.color()))
                .append(Component.text(":", NamedTextColor.DARK_GRAY)));
        sender.sendMessage(Component.text(" "));

        sender.sendMessage(addon.prefix().append(Component.text("/" + label, addon.color()))
                .append(Component.text(" - ", NamedTextColor.DARK_GRAY))
                .append(Component.text(description, NamedTextColor.GRAY)));

        for(String s : functionMap.keySet()) {
            sender.sendMessage(addon.prefix().append(Component.text("/" + label + " " + s, addon.color()))
                    .append(Component.text(" - ", NamedTextColor.DARK_GRAY))
                    .append(Component.text(functionMap.get(s).description(), NamedTextColor.GRAY)));
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0) {
            single(sender, args);
            return false;
        }

        String search = args[0];
        if(functionMap.containsKey(search.toLowerCase())) {
            functionMap.get(search).execute(sender, args);
            return false;
        }

        sender.sendMessage(addon.prefix().append(Component.text("Es wurde keine Funktion für ").append(Component.text(search.toLowerCase(), addon.color())).append(Component.text(" gefunden.", NamedTextColor.GRAY))));
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if(args.length == 0)return list;

        if(args.length == 1) {
            for(String optional : functionMap.keySet()) {
                if(!functionMap.get(optional).description().equalsIgnoreCase("$none")) {
                    list.add(optional);
                }
            }
        }

        List<String> completer = new ArrayList<>();
        String current = args[args.length-1].toLowerCase();
        for(String s : list) {
            if(s.startsWith(current)) {
                completer.add(s);
            }
        }
        return completer;
    }

    public String label() {
        return label;
    }
    public String description() {
        return description;
    }
}
