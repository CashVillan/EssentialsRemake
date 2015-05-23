package me.cashvillan.essentialsremake;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.cashvillan.essentialsremake.commands.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	private final seen seen = new seen();
	private final fly fly = new fly();
	private final gm gm = new gm();
	private final playerListener playerListener = new playerListener();
	
	static Main plugin;
	
	public void onEnable() {
		plugin = this;
		registerEvents();
		registerCommands();
		FileManager.loadplayersConfig();
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			
			me.cashvillan.essentialsremake.commands.seen.lastjoin.put(p.getName(), dateFormat.format(date));
			me.cashvillan.essentialsremake.playerListener.load(p);
		}
		
	}
	
	public void onDisable() {
		
	}
	
	public void registerEvents() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(this.playerListener, this);
		pm.registerEvents(this.seen, this);
		pm.registerEvents(this.fly, this);
		pm.registerEvents(this.gm, this);
	}
	
	public void registerCommands() {
		getCommand("seen").setExecutor(new me.cashvillan.essentialsremake.commands.seen());
		getCommand("fly").setExecutor(new me.cashvillan.essentialsremake.commands.fly());
		getCommand("gm").setExecutor(new me.cashvillan.essentialsremake.commands.gm());
	}
	
	public static Plugin getPlugin() {
		return Bukkit.getServer().getPluginManager().getPlugin("EssentialsRemake");
	}
	
	
	public static Main getInstance() {
		return plugin;
	}
}
