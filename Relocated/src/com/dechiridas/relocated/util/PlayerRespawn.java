package com.dechiridas.relocated.util;

import java.io.Serializable;

import org.bukkit.entity.Player;

public class PlayerRespawn implements Serializable {
	private static final long serialVersionUID = -2721127279926599563L;
	
	private Respawn respawn;
	private String playerName;
	
	public PlayerRespawn(Respawn respawn, Player player) {
		this.respawn = respawn;
		this.playerName = player.getName();
	}
	
	public Respawn getRespawn() {
		return respawn;
	}
	
	public void setRespawn(Respawn respawn) {
		this.respawn = respawn;
	}
	
	public String getPlayerName() {
		return playerName;
	}
}
