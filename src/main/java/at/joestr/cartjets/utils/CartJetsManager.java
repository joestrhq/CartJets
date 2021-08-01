// 
// Copyright (c) 2020 Joel Strasser <strasser999@gmail.com>
// 
// Licensed under the EUPL-1.2
// 
package at.joestr.cartjets.utils;

import at.joestr.cartjets.CartJetsPlugin;
import at.joestr.cartjets.configuration.AppConfiguration;
import at.joestr.cartjets.configuration.CurrentEntries;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

	private final ArrayList<UUID> minecarts;
  private final double multiplier;

	private CartJetsManager() {
		this.minecarts = new ArrayList<>();
    multiplier
      = AppConfiguration.getInstance()
        .getDouble(CurrentEntries.CONF_VECTORMULTIPLIER.toString());

		Bukkit.getScheduler().runTaskTimer(
			CartJetsPlugin.getInstance(),
			() -> {
				CartJetsManager
					.getInstance()
					.getCurrentMinecarts()
					.forEach(e -> {
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(CartJetsPlugin.getInstance(), () -> {
              Entity target = Bukkit.getServer().getEntity(e);
              
              if (target == null) {
                CartJetsManager.getInstance().removeMinecart(e);
                return;
              }
              
              target.setVelocity(
                target.getVelocity()
                  .normalize()
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

	public static CartJetsManager getInstance() {
		if (instance == null) {
			instance = new CartJetsManager();
		}
		return instance;
	}

	public synchronized void addMinecart(UUID e) {
		Entity target = Bukkit.getServer().getEntity(e);
		if (target == null) {
			return;
		}
		if (target.getType() != EntityType.MINECART) {
			return;
		}
		this.minecarts.add(e);
	}

	public synchronized void removeMinecart(UUID e) {
		Entity target = Bukkit.getServer().getEntity(e);
		if (target == null) {
			return;
		}
		if (target.getType() != EntityType.MINECART) {
			return;
		}
		this.minecarts.remove(e);
	}

	public List<UUID> getCurrentMinecarts() {
		return Collections.unmodifiableList(minecarts); // Just to be on the safe side.
	}
}
