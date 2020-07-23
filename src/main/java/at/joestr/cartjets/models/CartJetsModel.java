// 
// Copyright (c) 2020 Joel Strasser <strasser999@gmail.com>
// 
// Licensed under the EUPL-1.2
// 
package at.joestr.cartjets.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;

/**
 *
 * @author Joel
 */
@DatabaseTable(tableName = "cartjets")
public class CartJetsModel {

	@DatabaseField(id = true)
	private String name;

	@DatabaseField
	private UUID buttonLocationWorldUuid;
	@DatabaseField
	private double buttonLocationX;
	@DatabaseField
	private double buttonLocationY;
	@DatabaseField
	private double buttonLocationZ;
	@DatabaseField
	private float buttonLocationPitch;
	@DatabaseField
	private float buttonLocationYaw;

	@DatabaseField
	private UUID minecartSpawningLocationWorldUuid;
	@DatabaseField
	private double minecartSpawningLocationX;
	@DatabaseField
	private double minecartSpawningLocationY;
	@DatabaseField
	private double minecartSpawningLocationZ;
	@DatabaseField
	private float minecartSpawningLocationPitch;
	@DatabaseField
	private float minecartSpawningLocationYaw;

	public CartJetsModel() {
	}

	public CartJetsModel(String name, UUID buttonLocationWorldUuid, double buttonLocationX, double buttonLocationY, double buttonLocationZ, float buttonLocationPitch, float buttonLocationYaw, UUID minecartSpawningLocationWorldUuid, double minecartSpawningLocationX, double minecartSpawningLocationY, double minecartSpawningLocationZ, float minecartSpawningLocationPitch, float minecartSpawningLocationYaw) {
		this.name = name;
		this.buttonLocationWorldUuid = buttonLocationWorldUuid;
		this.buttonLocationX = buttonLocationX;
		this.buttonLocationY = buttonLocationY;
		this.buttonLocationZ = buttonLocationZ;
		this.buttonLocationPitch = buttonLocationPitch;
		this.buttonLocationYaw = buttonLocationYaw;
		this.minecartSpawningLocationWorldUuid = minecartSpawningLocationWorldUuid;
		this.minecartSpawningLocationX = minecartSpawningLocationX;
		this.minecartSpawningLocationY = minecartSpawningLocationY;
		this.minecartSpawningLocationZ = minecartSpawningLocationZ;
		this.minecartSpawningLocationPitch = minecartSpawningLocationPitch;
		this.minecartSpawningLocationYaw = minecartSpawningLocationYaw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getButtonLocationWorldUuid() {
		return buttonLocationWorldUuid;
	}

	public void setButtonLocationWorldUuid(UUID buttonLocationWorldUuid) {
		this.buttonLocationWorldUuid = buttonLocationWorldUuid;
	}

	public double getButtonLocationX() {
		return buttonLocationX;
	}

	public void setButtonLocationX(double buttonLocationX) {
		this.buttonLocationX = buttonLocationX;
	}

	public double getButtonLocationY() {
		return buttonLocationY;
	}

	public void setButtonLocationY(double buttonLocationY) {
		this.buttonLocationY = buttonLocationY;
	}

	public double getButtonLocationZ() {
		return buttonLocationZ;
	}

	public void setButtonLocationZ(double buttonLocationZ) {
		this.buttonLocationZ = buttonLocationZ;
	}

	public float getButtonLocationPitch() {
		return buttonLocationPitch;
	}

	public void setButtonLocationPitch(float buttonLocationPitch) {
		this.buttonLocationPitch = buttonLocationPitch;
	}

	public float getButtonLocationYaw() {
		return buttonLocationYaw;
	}

	public void setButtonLocationYaw(float buttonLocationYaw) {
		this.buttonLocationYaw = buttonLocationYaw;
	}

	public UUID getMinecartSpawningLocationWorldUuid() {
		return minecartSpawningLocationWorldUuid;
	}

	public void setMinecartSpawningLocationWorldUuid(UUID minecartSpawningLocationWorldUuid) {
		this.minecartSpawningLocationWorldUuid = minecartSpawningLocationWorldUuid;
	}

	public double getMinecartSpawningLocationX() {
		return minecartSpawningLocationX;
	}

	public void setMinecartSpawningLocationX(double minecartSpawningLocationX) {
		this.minecartSpawningLocationX = minecartSpawningLocationX;
	}

	public double getMinecartSpawningLocationY() {
		return minecartSpawningLocationY;
	}

	public void setMinecartSpawningLocationY(double minecartSpawningLocationY) {
		this.minecartSpawningLocationY = minecartSpawningLocationY;
	}

	public double getMinecartSpawningLocationZ() {
		return minecartSpawningLocationZ;
	}

	public void setMinecartSpawningLocationZ(double minecartSpawningLocationZ) {
		this.minecartSpawningLocationZ = minecartSpawningLocationZ;
	}

	public float getMinecartSpawningLocationPitch() {
		return minecartSpawningLocationPitch;
	}

	public void setMinecartSpawningLocationPitch(float minecartSpawningLocationPitch) {
		this.minecartSpawningLocationPitch = minecartSpawningLocationPitch;
	}

	public float getMinecartSpawningLocationYaw() {
		return minecartSpawningLocationYaw;
	}

	public void setMinecartSpawningLocationYaw(float minecartSpawningLocationYaw) {
		this.minecartSpawningLocationYaw = minecartSpawningLocationYaw;
	}

	public Object getButtonLocation() {
		if (buttonLocationWorldUuid == null) {
			return null;
		}

		return new Location(
			Bukkit.getServer().getWorld(buttonLocationWorldUuid),
			buttonLocationX,
			buttonLocationY,
			buttonLocationZ,
			buttonLocationYaw,
			buttonLocationPitch
		);
	}

	public void setButtonLocation(Location location) {
		this.buttonLocationWorldUuid = location.getWorld().getUID();
		this.buttonLocationX = location.getX();
		this.buttonLocationY = location.getY();
		this.buttonLocationZ = location.getZ();
		this.buttonLocationYaw = location.getYaw();
		this.buttonLocationPitch = location.getPitch();
	}

	public Location getMinecartSpawningLocation() {
		if (minecartSpawningLocationWorldUuid == null) {
			return null;
		}

		return new Location(
			Bukkit.getServer().getWorld(minecartSpawningLocationWorldUuid),
			minecartSpawningLocationX,
			minecartSpawningLocationY,
			minecartSpawningLocationZ,
			minecartSpawningLocationYaw,
			minecartSpawningLocationPitch
		);
	}

	public void setMinecartSpawningLocation(Location location) {
		this.minecartSpawningLocationWorldUuid = location.getWorld().getUID();
		this.minecartSpawningLocationX = location.getX();
		this.minecartSpawningLocationY = location.getY();
		this.minecartSpawningLocationZ = location.getZ();
		this.minecartSpawningLocationYaw = location.getYaw();
		this.minecartSpawningLocationPitch = location.getPitch();
	}
}
