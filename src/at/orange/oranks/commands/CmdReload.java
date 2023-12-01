package at.orange.oranks.commands;

import at.orange.oranks.ORanks;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdReload implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.hasPermission("oranks.reload")) {
            if (strings.length > 0 && strings[0].equalsIgnoreCase("reload")) {
                ORanks.plugin.reloadConfig();
                ORanks.config = ORanks.plugin.getConfig();
                ORanks.reloadRanks();

                commandSender.sendMessage("§aThe config has been successfully reloaded!");
            } else {
                commandSender.sendMessage("§aType §c/oranks reload §ato reload the config!");
            }
        }
        return false;
    }
}
