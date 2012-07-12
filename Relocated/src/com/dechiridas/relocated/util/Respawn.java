package com.dechiridas.relocated.util;

import java.io.Serializable;

import org.bukkit.Location;

public class Respawn implements Serializable {
	private static final long serialVersionUID = -3860342446952565667L;
	
	private ResLocation location;
	private boolean enabled;
	private ResType type;
	
	public Respawn(Location loc, boolean enable, ResType type) {
		this.location = resLocFromBukkitLoc(loc);
		this.enabled = enable;
		this.type = type;
	}
	
	private ResLocation resLocFromBukkitLoc(Location loc) {
		ResLocation rloc = new ResLocation(loc.getX(), loc.getY(), loc.getZ(), 
				loc.getPitch(), loc.getYaw(), 
				loc.getWorld().getName());
		return rloc;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public ResLocation getLocation() {
		return location;
	}
	
	public void enable() {
		this.enabled = true;
	}
	
	public void disable() {
		this.enabled = false;
	}
	
	public void setLocation(Location loc) {
		this.location = resLocFromBukkitLoc(loc);
	}
	
	public ResType getType() {
		return type;
	}
}
