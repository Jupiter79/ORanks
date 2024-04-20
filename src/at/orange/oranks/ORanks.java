package at.orange.oranks;

import at.orange.oranks.commands.CmdReload;
import at.orange.oranks.listeners.FormatChat;
import at.orange.oranks.listeners.GiveRankOnJoin;
import at.orange.oranks.other.Rank;
import at.orange.oranks.stats.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;
import java.util.stream.Collectors;

public class ORanks extends JavaPlugin {
    public static Plugin plugin;
    public static FileConfiguration config;

    public static Scoreboard scoreboard;
    public static List<Rank> RANKS = new ArrayList<>();

    @Override
    public void onEnable() {
        plugin = this;

        getServer().getConsoleSender().sendMessage("§aORanks v" + getDescription().getVersion() + " has been successfully enabled!");

        getCommand("oranks").setExecutor(new CmdReload());

        getServer().getPluginManager().registerEvents(new GiveRankOnJoin(), this);
        getServer().getPluginManager().registerEvents(new FormatChat(), this);

        config = getConfig();
        saveDefaultConfig();

        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        scoreboard.getTeams().forEach(Team::unregister);

        new BukkitRunnable() {
            @Override
            public void run() {
                // Metrics
                new Metrics((JavaPlugin) ORanks.plugin, 12778);

                reloadRanks();
            }
        }.runTask(plugin);

        new BukkitRunnable() {
            @Override
            public void run() {
                ORanks.assignRanks();
            }
        }.runTaskTimer(plugin, 0, 20 * 5);
    }

    public static void reloadRanks() {
        scoreboard.getTeams().forEach(Team::unregister);
        RANKS = new ArrayList<>();

        loadRanks();
    }

    static List<LinkedHashMap<String, Map<String, String>>> loadedRanks;
    static void loadRanks() {
        loadedRanks = (List<LinkedHashMap<String, Map<String, String>>>) config.get("ranks");

        final int[] current = {0};
        loadedRanks.forEach(x -> {
            Map.Entry<String, Map<String, String>> rank = x.entrySet().iterator().next();

            String id = rank.getKey();

            RANKS.add(new Rank(current[0], id, rank.getValue().get("prefix"), rank.getValue().get("messageFormatting")));

            current[0]++;
        });

        RANKS.forEach(rank -> {
            Team team = scoreboard.registerNewTeam(String.valueOf(rank.orderId));

            team.setPrefix(config.getString("tagFormat").replace("%rank%", rank.prefix));
            team.setColor(ChatColor.valueOf(config.getString("playerColor")));
        });

        assignRanks();
    }

    static void assignRanks() {
        Bukkit.getOnlinePlayers().forEach(ORanks::autoAssignRank);
    }

    private static Rank getRankByPlayer(Player p) {
        if (!(RANKS.stream().filter(x -> p.hasPermission("oranks." + x.id)).count() == 0)) {
            return RANKS.stream().filter(x -> p.hasPermission("oranks." + x.id)).findFirst().get();
        } else {
            return RANKS.stream().filter(x -> x.id.equals("defaultRank")).findFirst().get();
        }
    }

    public static void autoAssignRank(Player p) {
        Rank rank = getRankByPlayer(p);

        Team team = scoreboard.getTeam(String.valueOf(rank.orderId));
        team.addEntry(p.getName());

        p.setScoreboard(scoreboard);
    }
}
