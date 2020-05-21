/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.joestr.cartjets.utils;

import at.joestr.cartjets.CartJetsPlugin;
import at.joestr.cartjets.configuration.AppConfiguration;
import at.joestr.cartjets.configuration.CurrentEntries;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 *
 * @author Joel
 */
public class CartJetsManager {
	private static CartJetsManager instance = null;
	
	private ArrayList<UUID> minecarts;
	
	private CartJetsManager() {
		this.minecarts = new ArrayList<>();
		
		Bukkit.getScheduler().runTaskTimerAsynchronously(
			CartJetsPlugin.getInstance(),
			() -> {
				CartJetsManager
					.getInstrance()
					.getCurrentMinecarts()
					.forEach((e) -> {
						Entity target = Bukkit.getServer().getEntity(e);
						if (target == null) {
							CartJetsManager.getInstrance().removeMinecart(e);
							return;
						}
						
						double multiplier =
							AppConfiguration.getInstance()
								.getDouble(CurrentEntries.CONF_VECTORMULTIPLIER.toString());
						
						// Execute this synchronously
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(
							CartJetsPlugin.getInstance(), () -> {
								target.setVelocity(
									target.getVelocity()
										.multiply(multiplier)
								);
						});
					});
			},
			0, // No init delay
			AppConfiguration.getInstance()
				.getInt(CurrentEntries.CONF_TASKREPEATINGDELAYINTICKS.toString())
		);
	}
	
	public static CartJetsManager getInstrance() {
		if (instance == null) instance = new CartJetsManager();
		return instance;
	}
	
	public synchronized void addMinecart(UUID e) {
		Entity target = Bukkit.getServer().getEntity(e);
		if (target == null) return;
		if (target.getType() != EntityType.MINECART) return;
		this.minecarts.add(e);
	}
	
	public synchronized void removeMinecart(UUID e) {
		Entity target = Bukkit.getServer().getEntity(e);
		if (target == null) return;
		if (target.getType() != EntityType.MINECART) return;
		this.minecarts.remove(e);
	}
	
	public List<UUID> getCurrentMinecarts() {
		return Collections.unmodifiableList(minecarts); // Just to be on the safe side.
	}
}
