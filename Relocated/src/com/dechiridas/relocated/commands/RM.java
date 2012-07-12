package com.dechiridas.relocated.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.dechiridas.relocated.Relocated;
import com.dechiridas.relocated.util.Respawn;

public class RM implements CommandExecutor {
	private Relocated plugin;
	
	public RM(Relocated instance) {
		this.plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
		boolean r = true;
		if (sender instanceof Player) {
			Player player = (Player)sender;
			if (player.isOp()) {
				if (split.length >= 1 ) {
					String cmd = split[0];
					
					// Add new respawn
					if (cmd.equals("add")) {
						boolean enabled = true;
						if (split.length == 2)
							enabled = (split[1].startsWith("enable") ? true : false);
						if (plugin.rm.addRespawn(player.getLocation(), enabled))
							player.sendMessage("Respawn location successfully added.");
						else
							player.sendMessage("There was an error adding that respawn location. " + 
									"It may already exist.");
					} 
					
					// Remove existing respawn location
					else if (cmd.equals("rem") || cmd.equals("rm") 
							|| cmd.equals("del") || cmd.equals("remove") 
							|| cmd.equals("delete")) {
						if (split.length == 2) {
							try {
								int index = Integer.parseInt(split[1]) - 1;
								Respawn respawn = plugin.rm.getRespawnByIndex(index);
								if (respawn != null) {
									if (plugin.rm.removeRespawnByRef(respawn))
										player.sendMessage("Respawn successfully removed.");
									else
										player.sendMessage("Unable to remove respawn location.");
								}
							} catch (Exception e) {
								player.sendMessage("Index " + split[1] + " not found.");
							}
						} else {
							Location loc = player.getLocation();
							int x = (int)loc.getX(), y = (int)loc.getY(), z = (int)loc.getZ();
							Respawn respawn = plugin.rm.getRespawnByLocationApprox(x, y, z);
							if (respawn != null) {
								if (plugin.rm.removeRespawnByRef(respawn))
									player.sendMessage("Respawn successfully removed.");
								else
									player.sendMessage("Unable to remove respawn location.");
							}
						}
					}
					
					// Enable existing respawn location(s)
					else if (cmd.equals("enable")) {
						if (split.length == 2) {
							try {
								int index = Integer.parseInt(split[1]);
								Respawn respawn = plugin.rm.getRespawnByIndex(index);
								if (respawn != null) {
									respawn.enable();
									player.sendMessage("Respawn enabled.");
								}
							} catch (Exception e) {
								if (split[1].equals("all")) {
									plugin.rm.enableAll();
									player.sendMessage("All respawn locations enabled.");
								}
								else
									player.sendMessage("Index " + split[1] + " not found.");
							}
						} else {
							Location loc = player.getLocation();
							int x = (int)loc.getX(), y = (int)loc.getY(), z = (int)loc.getZ();
							Respawn respawn = plugin.rm.getRespawnByLocationApprox(x, y, z);
							if (respawn != null) {
								respawn.enable();
								player.sendMessage("Respawn enabled.");
							}
						}
					}
					
					// Disable existing respawn location(s)
					else if (cmd.equals("disable")) {
						if (split.length == 2) {
							try {
								int index = Integer.parseInt(split[1]);
								Respawn respawn = plugin.rm.getRespawnByIndex(index);
								if (respawn != null) {
									respawn.disable();
									player.sendMessage("Respawn disabled.");
								}
							} catch (Exception e) {
								if (split[1].equals("all")) {
									plugin.rm.disableAll();
									player.sendMessage("All respawn locations disabled.");
								}
								else
									player.sendMessage("Index " + split[1] + " not found.");
							}
						} else {
							Location loc = player.getLocation();
							int x = (int)loc.getX(), y = (int)loc.getY(), z = (int)loc.getZ();
							Respawn respawn = plugin.rm.getRespawnByLocationApprox(x, y, z);
							if (respawn != null) {
								respawn.disable();
								player.sendMessage("Respawn disabled.");
							}
						}
					}
					
					// Get a count of respawn locations
					else if (cmd.equals("count")) {
						player.sendMessage("" + plugin.rm.getRespawns().size());
					}
					
					// Go to a respawn location
					else if (cmd.equals("goto")) {
						if (split.length == 2) {
							if (split[1].equals("near") || split[1].equals("nearest")) {
								Respawn res = plugin.rm.getClosestRespawn(player);
								player.teleport(plugin.rm.bukkitLocFromResLoc(res.getLocation()));
							} else {
								try {
									int index = Integer.parseInt(split[1]);
									if (index > -1) {
										Respawn res = plugin.rm.getRespawnByIndex(index);
										if (res != null)
											player.teleport(plugin.rm.bukkitLocFromResLoc(res.getLocation()));
										else
											player.sendMessage("Respawn of index " + 
												index + " does not exist.");
									}
								} catch (Exception e) {
									player.sendMessage("Unable to find index " + split[1]);
								}
							}
						}
					}
				} else {
					r = false;
				}
			} else {
				player.sendMessage(ChatColor.YELLOW + 
					"You do not have permission to use that command.");
			}
		} else if (sender instanceof ConsoleCommandSender) {
			System.out.println("This is an in-game only command.");
		}
		return r;
	}
}
