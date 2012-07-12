package com.dechiridas.relocated.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.dechiridas.relocated.Relocated;

public class PlayerManager {
	private Relocated plugin;
	private List<PlayerRespawn> playerRespawns;
	
	public PlayerManager(Relocated instance) {
		this.plugin = instance;
		this.playerRespawns = new ArrayList<PlayerRespawn>();
	}
	
	public void addPlayerRespawn(PlayerRespawn pr) {
		this.playerRespawns.add(pr);
	}
	
	public void addPlayerRespawn(Respawn respawn, Player player) {
		PlayerRespawn pr = new PlayerRespawn(respawn, player);
		this.playerRespawns.add(pr);
	}
	
	public void removePlayerRespawnByRef(PlayerRespawn pr) {
		
	}
	
	public void removePlayerRespawnByIndex(int index) {
		
	}
	
	public void removePlayerRespawnByPlayerName(String playerName) {
		
	}
	
	public void removePlayerRespawnByPlayer(Player player) {
		
	}
	
	public PlayerRespawn getPlayerRespawnByIndex(int index) {
		PlayerRespawn pr = null;
		
		return pr;
	}
	
	public PlayerRespawn getPlayerRespawnByPlayer(Player player) {
		PlayerRespawn pr = null;
		
		return pr;
	}
	
	public PlayerRespawn getPlayerRespawnByPlayerName(String playerName) {
		PlayerRespawn pr = null;
		
		return pr;
	}
	
	public Player[] getAllPlayersForRespawn(Respawn respawn) {
		List<Player> players = new ArrayList<Player>();
		synchronized(playerRespawns) {
			for (int i = playerRespawns.size() - 1; i >= 0; i--) {
				if (playerRespawns.get(i).getRespawn() == respawn) {
					Player player = plugin.getServer().getPlayerExact(playerRespawns.get(i).getPlayerName());
					players.add(player);
				}
			}
		}
		return (Player[])players.toArray();
	}
}
