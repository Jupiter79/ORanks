package at.orange.oranks.listeners;

import at.orange.oranks.ORanks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class GiveRankOnJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        ORanks.autoAssignRank(e.getPlayer());
    }
}
