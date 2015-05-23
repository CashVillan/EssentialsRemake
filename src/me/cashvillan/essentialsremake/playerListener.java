package me.cashvillan.essentialsremake;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class playerListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		load(p);
	}
	
	
	
	public static void load(Player p) {
		if (FileManager.accountexists(p.getName()) == false) {
			FileManager.createAccount(p.getName());
			load(p);
		}
		setGamemode(p);
		setFly(p);
		p.sendMessage(ChatColor.GREEN + "Profile loaded!");
		
	}
	
	public static void setGamemode(Player p) {
		if (FileManager.getValue(p.getName(), "gamemode") == "survival") {
			p.setGameMode(GameMode.SURVIVAL);
		}
		if (FileManager.getValue(p.getName(), "gamemode") == "creative") {
			p.setGameMode(GameMode.CREATIVE);
		}
		if (FileManager.getValue(p.getName(), "gamemode") == "adventure") {
			p.setGameMode(GameMode.ADVENTURE);
		}
		if (FileManager.getValue(p.getName(), "gamemode") == "spectator")
			p.setGameMode(GameMode.SPECTATOR);
	}
	
	public static void setFly(Player p) {
		if (FileManager.getValue(p.getName(), "fly") == "enabled") {
			p.setAllowFlight(true);
		} else {
			p.setAllowFlight(false);
		}
	}
}
