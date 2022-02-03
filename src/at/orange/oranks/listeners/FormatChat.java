package at.orange.oranks.listeners;

import at.orange.oranks.ORanks;
import at.orange.oranks.other.Rank;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class FormatChat implements Listener {
    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        if (ORanks.config.getBoolean("formatChat")) {
            String teamName = ORanks.scoreboard.getTeams().stream().filter(x -> x.hasEntry(e.getPlayer().getName())).findFirst().get().getName();

            Rank rank = ORanks.RANKS.stream().filter(x -> String.valueOf(x.orderId).equals(teamName)).findFirst().get();

            ChatColor messageColor = ORanks.config.getString("playerChatColor").equals("AUTO") ? rank.color : ChatColor.valueOf(ORanks.config.getString("playerChatColor"));

            e.setFormat(ORanks.config.getString("chatFormat").replace("%rank%", rank.color + rank.prefix).replace("%player%", e.getPlayer().getName()).replace("%message%", messageColor + e.getMessage()));
        }
    }
}
