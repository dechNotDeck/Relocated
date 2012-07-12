package com.dechiridas.relocated.util;

import java.io.Serializable;

public class ResLocation implements Serializable {
	private static final long serialVersionUID = -2746073405501166119L;
	
	private double x, y, z;
	private float pitch, yaw;
	private String world;
	
	public ResLocation(double x, double y, double z, float pitch, float yaw, String world) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
		this.world = world;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public String getWorldName() {
		return world;
	}
}
