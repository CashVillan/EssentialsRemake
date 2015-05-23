package me.cashvillan.essentialsremake.commands;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import me.cashvillan.essentialsremake.FileManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class seen implements CommandExecutor, Listener {
	
	public static HashMap<String, String> lastjoin = new HashMap<String, String>();
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		seen.lastjoin.put(p.getName(), dateFormat.format(date));
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		lastjoin.remove(p.getName());
	}
	
	public void usages(CommandSender p) {
		p.sendMessage("Shows the last logout time of a player.");
		p.sendMessage("/seen <playername>");
	}
	
	@SuppressWarnings("deprecation")
	public boolean checkPlayerOnline(String p) {
		return Bukkit.getOfflinePlayer(p).isOnline();
	}
	
	public String checkPlayerStatus(String p) {
		String status = "";
		
		if (checkPlayerOnline(p) == true) {
			status = "online";
		} else {
			status = "offline";
		}
		return status;
	}
	
	public ChatColor statusColor(String s) {
		if (s == "online") {
			return ChatColor.GREEN;
		}
		if (s == "offline") {
			return ChatColor.RED;
		}
		return null;
	}
	
	public void runPlayer(String target, CommandSender sender) {
		if (FileManager.accountexists(target) == false) {
			sender.sendMessage(ChatColor.RED + "Error: " + ChatColor.DARK_RED + "Player not found");
			return;
		}
		if (checkPlayerOnline(target) == true) {
				sender.sendMessage(ChatColor.GOLD + "Player " + ChatColor.YELLOW + "" + target + ChatColor.GOLD + " has been " + statusColor(checkPlayerStatus(target)) + "" + checkPlayerStatus(target) + ChatColor.GOLD + " since " + ChatColor.RED + "" + onOnlineString(target) + ChatColor.GOLD + ".");
		}
		else {
			sender.sendMessage(ChatColor.RED + "fuck off");
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("seen")) {
			if (args.length == 0) {
				usages(sender);
			}
			if (args.length == 1) {
				runPlayer(args[0], sender);
			}
		}
		return true;
	}
	
	public String onOnlineString(String target) {
		String difference = "";
		
		if (checkPlayerOnline(target) == true) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  

			try {
				Date current = new Date();
				Date since = format.parse(lastjoin.get(target));
				
				long diff = current.getTime() - since.getTime();
				long diffSeconds = diff / 1000 % 60;  
				long diffMinutes = diff / (60 * 1000) % 60;        
				long diffHours = diff / (60 * 60 * 1000); 
				
				if (diffHours != 0) {
					difference = diffHours + " hours";
				}
				if (diffMinutes != 0) {
					if (difference == diffHours + " hours") {
						difference = diffHours + " hours " + diffMinutes + " minutes"; 
					} else {
						difference = diffMinutes + " minutes";
					}
				}
				if (diffSeconds != 0) {
					if (difference == diffHours + " hours " + diffMinutes + " minutes") {
						difference = diffHours + " hours " + diffMinutes + " minutes " + diffSeconds + " seconds"; 
					
					} else if (difference == diffHours + " hours") {
						difference = diffHours + " hours " + diffSeconds + " seconds";
					
					} else if (difference == diffMinutes + " minutes") {
						difference = diffMinutes + " minutes " + diffSeconds + " seconds";
						
					} else {
						difference = diffSeconds + " seconds";
					}
				}
				
			} catch (ParseException e) {
				e.printStackTrace();
			}  
		}
		return difference;
	}
}
