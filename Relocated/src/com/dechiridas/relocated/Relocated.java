package com.dechiridas.relocated;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.dechiridas.relocated.commands.RM;
import com.dechiridas.relocated.commands.Rshrine;
import com.dechiridas.relocated.listeners.RBlockListener;
import com.dechiridas.relocated.listeners.RPlayerListener;
import com.dechiridas.relocated.util.Config;
import com.dechiridas.relocated.util.RespawnManager;
import com.dechiridas.relocated.util.Serializer;

public class Relocated extends JavaPlugin {
	// Class instances
	public Config config = new Config(this);
	public Serializer serializer = new Serializer(this);
	
	// Listeners
	public RPlayerListener playerListener = new RPlayerListener(this);
	public RBlockListener blockListener = new RBlockListener(this);
	
	// Managers
	public PluginManager pm;
	public RespawnManager rm = new RespawnManager(this);
	
	@Override
	public void onEnable() {
		// Set managers
		pm = getServer().getPluginManager();
		
		// Load respawns
		rm.load();
		
		// Register events
		pm.registerEvents(playerListener, this);
		
		// Command executors
		getCommand("rm").setExecutor(new RM(this));
		getCommand("resman").setExecutor(new RM(this));
		getCommand("rshrine").setExecutor(new Rshrine(this));
	}
	
	@Override
	public void onDisable() {
		// Save respawns
		rm.save();
	}
}
