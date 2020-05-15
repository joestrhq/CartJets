//
// MIT License
// 
// Copyright (c) 2020 minecraft.kiwi
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
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
	
	@DatabaseField
	private UUID minecartDirectionLocationWorldUuid;
	@DatabaseField
	private double minecartDirectionLocationX;
	@DatabaseField
	private double minecartDirectionLocationY;
	@DatabaseField
	private double minecartDirectionLocationZ;
	@DatabaseField
	private float minecartDirectionLocationPitch;
	@DatabaseField
	private float minecartDirectionLocationYaw;

	public CartJetsModel() {
	}

	public CartJetsModel(String name, UUID buttonLocationWorldUuid, double buttonLocationX, double buttonLocationY, double buttonLocationZ, float buttonLocationPitch, float buttonLocationYaw, UUID minecartSpawningLocationWorldUuid, double minecartSpawningLocationX, double minecartSpawningLocationY, double minecartSpawningLocationZ, float minecartSpawningLocationPitch, float minecartSpawningLocationYaw, UUID minecartDirectionLocationWorldUuid, double minecartDirectionLocationX, double minecartDirectionLocationY, double minecartDirectionLocationZ, float minecartDirectionLocationPitch, float minecartDirectionLocationYaw) {
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
		this.minecartDirectionLocationWorldUuid = minecartDirectionLocationWorldUuid;
		this.minecartDirectionLocationX = minecartDirectionLocationX;
		this.minecartDirectionLocationY = minecartDirectionLocationY;
		this.minecartDirectionLocationZ = minecartDirectionLocationZ;
		this.minecartDirectionLocationPitch = minecartDirectionLocationPitch;
		this.minecartDirectionLocationYaw = minecartDirectionLocationYaw;
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

	public UUID getMinecartDirectionLocationWorldUuid() {
		return minecartDirectionLocationWorldUuid;
	}

	public void setMinecartDirectionLocationWorldUuid(UUID minecartDirectionLocationWorldUuid) {
		this.minecartDirectionLocationWorldUuid = minecartDirectionLocationWorldUuid;
	}

	public double getMinecartDirectionLocationX() {
		return minecartDirectionLocationX;
	}

	public void setMinecartDirectionLocationX(double minecartDirectionLocationX) {
		this.minecartDirectionLocationX = minecartDirectionLocationX;
	}

	public double getMinecartDirectionLocationY() {
		return minecartDirectionLocationY;
	}

	public void setMinecartDirectionLocationY(double minecartDirectionLocationY) {
		this.minecartDirectionLocationY = minecartDirectionLocationY;
	}

	public double getMinecartDirectionLocationZ() {
		return minecartDirectionLocationZ;
	}

	public void setMinecartDirectionLocationZ(double minecartDirectionLocationZ) {
		this.minecartDirectionLocationZ = minecartDirectionLocationZ;
	}

	public float getMinecartDirectionLocationPitch() {
		return minecartDirectionLocationPitch;
	}

	public void setMinecartDirectionLocationPitch(float minecartDirectionLocationPitch) {
		this.minecartDirectionLocationPitch = minecartDirectionLocationPitch;
	}

	public float getMinecartDirectionLocationYaw() {
		return minecartDirectionLocationYaw;
	}

	public void setMinecartDirectionLocationYaw(float minecartDirectionLocationYaw) {
		this.minecartDirectionLocationYaw = minecartDirectionLocationYaw;
	}

	public Object getButtonLocation() {
		if (buttonLocationWorldUuid == null) return null;
		
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
		if (minecartSpawningLocationWorldUuid == null) return null;
		
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
	
	public Location getMinecarDirectionLocation() {
		if (minecartDirectionLocationWorldUuid == null) return null;
		
		return new Location(
			Bukkit.getServer().getWorld(minecartSpawningLocationWorldUuid),
			minecartDirectionLocationX,
			minecartDirectionLocationY,
			minecartDirectionLocationZ,
			minecartDirectionLocationYaw,
			minecartDirectionLocationPitch
		);
	}

	public void setMinecartDirectionLocation(Location location) {
		this.minecartDirectionLocationWorldUuid = location.getWorld().getUID();
		this.minecartDirectionLocationX = location.getX();
		this.minecartDirectionLocationY = location.getY();
		this.minecartDirectionLocationZ = location.getZ();
		this.minecartDirectionLocationYaw = location.getYaw();
		this.minecartDirectionLocationPitch = location.getPitch();
	}
}
