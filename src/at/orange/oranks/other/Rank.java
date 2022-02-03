package at.orange.oranks.other;

import org.bukkit.ChatColor;

public class Rank {
    public int orderId;
    public String id;
    public String prefix;
    public ChatColor color;

    public Rank(int orderId, String id, String prefix, String color) {
        this.orderId = orderId;
        this.id = id;
        this.prefix = prefix;
        this.color = ChatColor.valueOf(color);
    }
}
