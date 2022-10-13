package net.xcyan.citybuild.handle;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardHandle {

    public static ScoreboardHandle handle(Player player) {
        return new ScoreboardHandle(player);
    }

    private final Scoreboard scoreboard;
    private final Objective objective;

    private final Player player;

    public ScoreboardHandle(Player player) {
        this.player = player;

        if(player.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard())) {
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }

        this.scoreboard = player.getScoreboard();

        if(this.scoreboard.getObjective("display") != null) {
            this.scoreboard.getObjective("display").unregister();
        }

        this.objective = this.scoreboard.registerNewObjective("display", "dummy", Component.text("§9     §8□ §9§lxcyan§8§l.§9§lnet §8□     §9"));
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        update();
    }

    public void update() {
        setScore("", 15);
        setScore("\uE102", 14);
        setScore("§7Hauptstadt", 13);
        setScore("", 12);
        setScore("\uE101", 11);
        setScore("§70§8,§700 §f\uE202", 10);
        setScore("", 9);
    }

    public void setScore(String content, int score) {
        Team team = getTeamByScore(score);

        if(team == null)return;

        team.setPrefix(content);

        showScore(score);
    }
    public void removeScore(int score) {
        hideScore(score);
    }
    public void setDisplaySlot(DisplaySlot slot) {
        this.objective.setDisplaySlot(slot);
    }

    private EntryName getEntryNameByScore(int score) {
        for(EntryName name : EntryName.values()) {
            if(score == name.getEntry()) {
                return name;
            }
        }
        return null;
    }

    private Team getTeamByScore(int score) {
        EntryName name = getEntryNameByScore(score);

        if(name == null)return null;

        Team team = scoreboard.getEntryTeam(name.getEntryName());

        if(team != null) return team;

        team = scoreboard.registerNewTeam(name.name());
        team.addEntry(name.getEntryName());

        return team;
    }

    private void showScore(int score) {
        EntryName name = getEntryNameByScore(score);

        if(name == null)return;

        if(objective.getScore(name.getEntryName()).isScoreSet())return;

        objective.getScore(name.getEntryName()).setScore(score);
    }
    private void hideScore(int score) {
        EntryName name = getEntryNameByScore(score);

        if(name == null)return;

        if(!objective.getScore(name.getEntryName()).isScoreSet())return;

        scoreboard.resetScores(name.getEntryName());
    }

    private enum EntryName {
        ENTRY_0(0, ChatColor.BLACK.toString()),
        ENTRY_1(1, ChatColor.DARK_BLUE.toString()),
        ENTRY_2(2, ChatColor.DARK_GREEN.toString()),
        ENTRY_3(3, ChatColor.DARK_RED.toString()),
        ENTRY_4(4, ChatColor.DARK_PURPLE.toString()),
        ENTRY_5(5, ChatColor.GOLD.toString()),
        ENTRY_6(6, ChatColor.GRAY.toString()),
        ENTRY_7(7, ChatColor.DARK_GRAY.toString()),
        ENTRY_8(8, ChatColor.BLUE.toString()),
        ENTRY_9(9, ChatColor.GREEN.toString()),
        ENTRY_10(10, ChatColor.RED.toString()),
        ENTRY_11(11, ChatColor.LIGHT_PURPLE.toString()),
        ENTRY_12(12, ChatColor.YELLOW.toString()),
        ENTRY_13(13, ChatColor.WHITE.toString()),
        ENTRY_14(14, ChatColor.STRIKETHROUGH.toString()),
        ENTRY_15(15, ChatColor.BOLD.toString());

        private final int entry;
        private final String entryName;

        EntryName(int entry, String entryName) {
            this.entry = entry;
            this.entryName = entryName;
        }

        public int getEntry() {
            return entry;
        }

        public String getEntryName() {
            return entryName;
        }
    }
}
