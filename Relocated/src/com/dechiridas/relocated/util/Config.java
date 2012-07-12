package com.dechiridas.relocated.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import com.dechiridas.relocated.Relocated;

public class Config {
	private Relocated instance;
	public String directory = "plugins" + File.separator + Relocated.class.getSimpleName();
	File file = new File(directory + File.separator + "config.yml");
	
	public Config(Relocated instance) {
		this.instance = instance;
		checkConfig();
	}
	
	public void checkConfig() {
		new File(directory).mkdir();
		if (!file.exists()) {
			try {
				file.createNewFile();
				addDefaults();
			} catch (Exception e) {
				System.out.println("Unable to create config file.");
			}
		} else {
			loadKeys();
		}
	}
	
	public YamlConfiguration load() {
		try {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			return config;
		} catch (Exception e) {
			System.out.println("Unable to load config file.");
		}
		return null;
	}
	
	private void addDefaults() {
		System.out.println("Generating Config file...");
		//write("Use_Whitelist", false);
	}
	
	private void loadKeys() {
		System.out.println("Loading Config File...");
	}
	
	public void write(String root, Object x) {
		YamlConfiguration config = load();
		config.set(root, x);
		try {
			config.save(file);
		} catch (IOException e) {
			System.out.println("There was an error saving configuration to file " + file.getName());
		}
	}
	
	public Boolean readBoolean(String root) {
		YamlConfiguration config = load();
		return config.getBoolean(root, true);
	}
	
	public Double readDouble(String root) {
		YamlConfiguration config = load();
		return config.getDouble(root, 0);
	}
	
	public String readString(String root) {
		YamlConfiguration config = load();
		return config.getString(root);
	}
}