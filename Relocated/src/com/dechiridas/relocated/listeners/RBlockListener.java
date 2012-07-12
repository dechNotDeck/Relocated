package com.dechiridas.relocated.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

import com.dechiridas.relocated.Relocated;

public class RBlockListener implements Listener {
	private Relocated plugin;
	private List<ShrinePlayer> shrinePlayers = new ArrayList<ShrinePlayer>();
	
	public RBlockListener(Relocated instance) {
		this.plugin = instance;
	}
	
	public List<ShrinePlayer> getShrinePlayers() {
		return shrinePlayers;
	}
	
	public boolean isShrinePlayer(Player player) {
		boolean r = false;
		synchronized(shrinePlayers) {
			for (int i = shrinePlayers.size() - 1; i >= 0; i--) {
				if (shrinePlayers.get(i).getPlayer() == player)
					r = true;
			}
		}
		return r;
	}
	
	public void removeShrinePlayer(Player player) {
		synchronized(shrinePlayers) {
			for (int i = shrinePlayers.size() - 1; i >= 0; i--) {
				if (shrinePlayers.get(i).getPlayer() == player)
					shrinePlayers.remove(i);
			}
		}
	}
	
	public void addShrinePlayer(Player player) {
		ShrinePlayer sp = new ShrinePlayer(player);
		shrinePlayers.add(sp);
	}
	
	@EventHandler
	public void onBlockDamage(BlockDamageEvent event) {
		Player player = (Player)event.getPlayer();
		player.sendMessage("You damaged a block!");
		if (isShrinePlayer(player)) {
			player.sendMessage("Building shrine.");
			buildShrine(event.getBlock().getLocation());
			removeShrinePlayer(player);
		}
	}
	
	public void buildShrine(Location loc) {
		double x = loc.getX(), y = loc.getY(), z = loc.getZ();
		Location cloc = new Location(loc.getWorld(), x - 2, y, z -1);
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					Location tloc = new Location(cloc.getWorld(), cloc.getX() + j, cloc.getY() + k, cloc.getZ() + i);
					if (i == 2 && j == 1 && k == 1)
						tloc.getBlock().setType(Material.NETHERRACK);
					if (i == 1 && j == 1 && k == 1)
						tloc.getBlock().setType(Material.FIRE);
					else
						tloc.getBlock().setType(Material.OBSIDIAN);
				}
			}
		}
	}
	
	private class ShrinePlayer {
		private Player player;
		
		public ShrinePlayer(Player player) {
			this.player = player;
		}
		
		public Player getPlayer() {
			return player;
		}
	}
}
