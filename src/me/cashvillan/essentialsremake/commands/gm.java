package me.cashvillan.essentialsremake.commands;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class gm implements Listener, CommandExecutor {
	
	public static HashMap<String, String> gm = new HashMap<String, String>();
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		gm.put(p.getName(), "survival");
		
		if (gm.get(p.getName()) == "survival") {
			makeSurvival(p);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		gm.put(p.getName(), "survival");
	}

	public void usages(Player p) {
		
		String status = gm.get(p.getName());
		
		p.sendMessage(ChatColor.GRAY + "Status: " + ChatColor.GREEN + status);
		p.sendMessage(ChatColor.RED + "Usage:");
		p.sendMessage("[/gm <player> <gamemode>] Set another players gamemode.");
		p.sendMessage("[/gm <gamemode>] Set your gamemode.");
	}
	
	public void makeCreative(Player p) {
		gm.put(p.getName(), "creative");
		p.setGameMode(GameMode.CREATIVE);
		p.sendMessage(ChatColor.YELLOW + "Your gamemode has been updated to 'creative'.");
	}
	
	public void makeSurvival(Player p) {
		gm.put(p.getName(), "");
		p.setGameMode(GameMode.SURVIVAL);
		p.sendMessage(ChatColor.YELLOW + "Your gamemode has been updated to 'survival'.");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("gm")) {
			if (args.length == 0) {
				usages(p);
				return true;
			}
			
			if (args.length == 1) {
				if (args[0].contains("creative")) {
					if (gm.get(p.getName()) == "creative") {
						makeSurvival(p);
					} else {
						if (gm.get(p.getName()) == "survival") {
							makeCreative(p);
							return true;
						}
					}
				}
				else {
					usages(p);
				}
			}
		}
		return true;
	}
}
