package com.dechiridas.relocated.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.dechiridas.relocated.Relocated;

public class Rshrine implements CommandExecutor {
	private Relocated plugin;
	
	public Rshrine(Relocated instance) {
		this.plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
		boolean r = true;
		if (sender instanceof Player) {
			Player player = (Player)sender;
			if (player.isOp()) {
				if (split.length == 0) { // Enable shrine player mode
					player.sendMessage("Enting shrine player mode.");
					plugin.blockListener.addShrinePlayer(player);
				} else if (split.length == 1) {
					if (split[0].equals("halt")) { // Exist shrine player mode
						plugin.blockListener.removeShrinePlayer(player);
					}
				}
			}
		} else if (sender instanceof ConsoleCommandSender) {
			System.out.println("This is an in-game only command.");
		}
		return r;
	}

}
