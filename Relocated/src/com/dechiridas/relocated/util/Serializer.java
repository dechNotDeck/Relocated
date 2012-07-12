package com.dechiridas.relocated.util;

import java.io.*;
import java.util.List;

import com.dechiridas.relocated.Relocated;

public class Serializer {
	private Relocated plugin;
	
	public Serializer(Relocated instance) {
		this.plugin = instance;
	}
	
	public void saveRespawns(Object data, String filename) {
		saveRespawns(data, filename, true);
	}
	
	public void saveRespawns(Object data, String filename, boolean silent) {
		try {
			filename = (filename.endsWith(".dat") ? filename : filename + ".dat");
			if (!filename.startsWith(plugin.config.directory))
				filename = plugin.config.directory + File.separator + filename;
			OutputStream file = new FileOutputStream(filename);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			try {
				output.writeObject(data);
				if (silent == false)
					System.out.println("Saved data successfully to: " + filename);
			} finally {
				output.close();
			}
		} catch (IOException e) {
			System.out.println("Unable to save data.");
			e.printStackTrace();
		}
	}
	
	public void loadRespawns(String filename) {
		try {
			filename = (filename.endsWith(".dat") ? filename : filename + ".dat");
			if (!filename.startsWith(plugin.config.directory))
				filename = plugin.config.directory + File.separator + filename;
			File file = new File(filename);
			if (!file.exists())
				file.createNewFile();
			InputStream filestream = new FileInputStream(filename);
			InputStream buffer = new BufferedInputStream(filestream);
			ObjectInput input = new ObjectInputStream(buffer);
			try {
				plugin.rm.setRespawns((List<Respawn>)input.readObject());
				System.out.println("Loaded respawns successfully.");
			}
			finally {
				input.close();
			}
		} catch (ClassNotFoundException ex) {
			
		} catch (IOException e) {
			System.out.println("Unable to load respawns.");
			e.printStackTrace();
		}
	}
}
