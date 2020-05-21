/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.joestr.cartjets.listeners;

import at.joestr.cartjets.CartJetsPlugin;
import at.joestr.cartjets.configuration.CurrentEntries;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author Joel
 */
public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent ev) {
		if (!ev.getPlayer().hasPermission(CurrentEntries.PERM_CMD_CARTJETS_UPDATE.toString()))
			return;
		
		CartJetsPlugin.getInstance().getUpdater().checkForUpdate().whenComplete((result, ex) -> {
			if (ex != null) {
				// error
				return;
			}
			
			if (!result.isPresent()) {
				// error?
				return;
			}
			
			// update is present
		});
	}
}
