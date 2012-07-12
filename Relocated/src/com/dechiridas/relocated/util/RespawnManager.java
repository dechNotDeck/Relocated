package com.dechiridas.relocated.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.dechiridas.relocated.Relocated;

public class RespawnManager {
	private Relocated plugin;
	private List<Respawn> respawns;
	
	public RespawnManager(Relocated instance) {
		this.plugin = instance;
		this.respawns = new ArrayList<Respawn>();
	}
	
	public void save() {
		plugin.serializer.saveRespawns(respawns, "respawns.dat");
	}
	
	public void load() {
		plugin.serializer.loadRespawns("respawns.dat");
	}
	
	public void disableAll() {
		synchronized(respawns) {
			for (int i = respawns.size() - 1; i >= 0; i--)
				respawns.get(i).disable();
		}
	}
	
	public void enableAll() {
		synchronized(respawns) {
			for (int i = respawns.size() - 1; i >= 0; i--)
				respawns.get(i).enable();
		}
	}
	
	public void setRespawns(List<Respawn> respawns) {
		this.respawns = respawns;
	}
	
	public List<Respawn> getRespawns() {
		return respawns;
	}
	
	public boolean addRespawn(Location loc) {
		return addRespawn(loc, true);
	}
	
	public boolean addRespawn(Location loc, boolean enabled) {
		Respawn respawn = new Respawn(loc, enabled);
		return addRespawn(respawn);
	}
	
	public boolean addRespawn(Respawn respawn) {
		boolean r = true;
		synchronized(respawns) {
			Location loc = bukkitLocFromResLoc(respawn.getLocation());
			for (Respawn res : respawns) {
				if (bukkitLocFromResLoc(res.getLocation()) == loc)
					r = false;
			}
			if (r == true) {
				respawns.add(respawn);
				System.out.println("New respawn location added.");
			}
		}
		return r;
	}
	
	public boolean removeRespawnByIndex(int index) {
		boolean r = false;
		synchronized(respawns) {
			for (int i = respawns.size() - 1; i >= 0; i--) {
				if (i == index) {
					respawns.remove(i);
					r = true;
				}
			}
			if (r == true)
				System.out.println("Respawn location removed.");
		}
		return r;
	}
	
	public boolean removeRespawnByLocation(Location loc) {
		boolean r = false;
		synchronized(respawns) {
			for (int i = respawns.size() - 1; i >= 0; i--) {
				if (bukkitLocFromResLoc(respawns.get(i).getLocation()) == loc) {
					respawns.remove(i);
					r = true;
				}
			}
			if (r == true)
				System.out.println("Respawn location removed.");
		}
		return r;
	}
	
	public boolean removeRespawnByRef(Respawn respawn) {
		boolean r = false;
		synchronized(respawns) {
			for (int i = respawns.size() - 1; i >= 0; i--) {
				if (respawns.get(i) == respawn) {
					respawns.remove(i);
					r = true;
				}
			}
			if (r == true)
				System.out.println("Respawn location removed.");
		}
		return r;
	}
	
	public Respawn getRespawnByLocation(Location loc) {
		Respawn res = null;
		synchronized(respawns) {
			for (int i = respawns.size() - 1; i >= 0; i--) {
				if (bukkitLocFromResLoc(respawns.get(i).getLocation()) == loc) {
					res = respawns.get(i);
				}
			}
		}
		return res;
	}
	
	public Respawn getRespawnByLocationApprox(int x, int y, int z) {
		Respawn res = null;
		synchronized (respawns) {
			for (int i = respawns.size() - 1; i >= 0; i--) {
				Location loc = bukkitLocFromResLoc(respawns.get(i).getLocation());
				int rx = (int)loc.getX(), ry = (int)loc.getY(), rz = (int)loc.getZ();
				if (rx == x && ry == y && rz == z)
					res = respawns.get(i);
			}
		}
		return res;
	}
	
	public Respawn getRespawnByIndex(int index) {
		Respawn res = null;
		synchronized(respawns) {
			for (int i = respawns.size() - 1; i >= 0; i--) {
				if (i == index)
					res = respawns.get(i);
			}
		}
		return res;
	}
	
	public Respawn getClosestRespawn(Entity entity) {
		Location loc = entity.getLocation();
		return getClosestRespawn(loc);
	}
	
	public Respawn getClosestRespawn(Player player) {
		Location loc = player.getLocation();
		return getClosestRespawn(loc);
	}
	
	public Respawn getClosestRespawn(Location loc) {
		Respawn res = null;
		int closestindex = -1;
		double closestdistance = -1;
		synchronized(respawns) {
			for (int i = respawns.size() - 1; i >= 0; i--) {
				Location rloc = bukkitLocFromResLoc(respawns.get(i).getLocation());
				double dx = Math.pow((loc.getX() - rloc.getX()), 2);
				double dy = Math.pow((loc.getY() - rloc.getY()), 2);
				double dz = Math.pow((loc.getZ() - rloc.getZ()), 2);
				double distance = Math.sqrt(dx + dy + dz);
				if (closestdistance == -1) {
					closestdistance = distance;
					closestindex = i;
				} else if (closestdistance != -1 && distance < closestdistance) {
					closestdistance = distance;
					closestindex = i;
				}
			}
			if (closestindex != -1 && closestdistance != -1)
				res = respawns.get(closestindex);
		}
		return res;
	}
	
	public int getRespawnIndexByLocation(Location loc) {
		int index = -1;
		synchronized(respawns) {
			for (int i = respawns.size() - 1; i >= 0; i--) {
				if (bukkitLocFromResLoc(respawns.get(i).getLocation()) == loc)
					index = i;
			}
		}
		return index;
	}
	
	public int getRespawnIndexByRef(Respawn respawn) {
		int index = -1;
		synchronized(respawns) {
			for (int i = respawns.size() - 1; i >= 0; i--) {
				if (respawns.get(i) == respawn)
					index = i;
			}
		}
		return index;
	}
	
	public Location bukkitLocFromResLoc(ResLocation rloc) {
		Location loc = new Location(plugin.getServer().getWorld(rloc.getWorldName()), 
				rloc.getX(), rloc.getY(), rloc.getZ(), rloc.getYaw(), rloc.getPitch());
		return loc;
	}
}
