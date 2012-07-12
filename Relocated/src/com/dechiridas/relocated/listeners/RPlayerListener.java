package com.dechiridas.relocated.listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.dechiridas.relocated.Relocated;
import com.dechiridas.relocated.util.Respawn;

public class RPlayerListener implements Listener {
	public Relocated plugin;
	
	public RPlayerListener(Relocated instance) {
		this.plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Location currentLoc = event.getPlayer().getLocation();
		Respawn respawn = plugin.rm.getClosestRespawn(currentLoc);
		Location resLoc = plugin.rm.bukkitLocFromResLoc(respawn.getLocation());
		event.setRespawnLocation(resLoc);
	}
}
