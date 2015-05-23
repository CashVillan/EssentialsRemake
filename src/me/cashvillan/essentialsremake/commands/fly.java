package me.cashvillan.essentialsremake.commands;

import me.cashvillan.essentialsremake.FileManager;
import me.cashvillan.essentialsremake.playerListener;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class fly implements CommandExecutor, Listener {
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("fly")) {
			if (FileManager.getValue(p.getName(), "fly") == "disabled") {
				FileManager.setValue(p.getName(), "fly", "enabled");
				playerListener.load(p);
				p.sendMessage(ChatColor.YELLOW + "Flight has been enabled!");
			} else {
				if (FileManager.getValue(p.getName(), "fly") == "enabled") {
					FileManager.setValue(p.getName(), "fly", "disabled");
					playerListener.load(p);
					p.sendMessage(ChatColor.YELLOW + "Flight has been disabled!");
					return true;
				}
			}
		}
		return true;
	}
}
