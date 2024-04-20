package at.orange.oranks.other;

import org.bukkit.ChatColor;

public class Rank {
    public int orderId;
    public String id;
    public String prefix;
    public String messageFormatting;

    public Rank(int orderId, String id, String prefix, String messageFormatting) {
        this.orderId = orderId;
        this.id = id;
        this.prefix = prefix;
        this.messageFormatting = messageFormatting;
    }
}
