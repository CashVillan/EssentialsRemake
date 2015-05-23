package me.cashvillan.essentialsremake;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {
	
	//fileloading
    public static FileConfiguration playersConfig;
    public static File playersFile;

    public static void loadplayersConfig() {
		playersFile = new File(Main.getInstance().getDataFolder(), "players.yml");
	    if (!playersFile.exists()) {
	        try {
	        	playersFile.createNewFile();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
		    
		playersConfig = YamlConfiguration.loadConfiguration(playersFile);
    }
    
    public static FileConfiguration getPlayersConfig() {
    	return playersConfig;
    }
    
    public static void saveplayersConfig() {
    	try {
			playersConfig.save(playersFile);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    //filemanagement
	public static void setValue(String player, String key, Object value) {
		FileManager.getPlayersConfig().set("players." + player + "." + key, value);
		FileManager.saveplayersConfig();
	}
	
	public static Object getValue(String player, String key) {
		return FileManager.getPlayersConfig().get("players." + player) + "." + key;
	}
	
	public static boolean hasValue(String player, String key) {
		return FileManager.getPlayersConfig().contains("players." + player + "." + key);
	}
	
	public static boolean accountexists(String player) {
		return FileManager.getPlayersConfig().contains("players." + player);
	}
	public static void createAccount(String player) {
		setValue(player, "gamemode", "survival");
		setValue(player, "fly", "disabled");
	}
}
