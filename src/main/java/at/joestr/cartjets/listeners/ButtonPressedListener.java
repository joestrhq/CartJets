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
package at.joestr.cartjets.listeners;

import at.joestr.cartjets.CartJetsPlugin;
import at.joestr.cartjets.configuration.AppConfiguration;
import at.joestr.cartjets.models.CartJetsModel;
import at.joestr.cartjets.configuration.CurrentEntries;
import at.joestr.cartjets.utils.CartJetsManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.metadata.FixedMetadataValue;

/**
 *
 * @author Joel
 */
public class ButtonPressedListener implements Listener {
  
  private static final Material[] BUTTONS = new Material[] {
    Material.OAK_BUTTON,
    Material.STONE_BUTTON,
    Material.DARK_OAK_BUTTON,
    Material.ACACIA_BUTTON,
    Material.SPRUCE_BUTTON,
    Material.BIRCH_BUTTON,
    Material.JUNGLE_BUTTON
  };
  
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onButtonClicked(PlayerInteractEvent ev) {
		if (!EquipmentSlot.HAND.equals(ev.getHand()))
			return;
		
    Block clickedBlock = ev.getClickedBlock();
		
    if (clickedBlock == null) return;
    
    if (CartJetsPlugin.getInstance().getPerUserModels().containsKey(ev.getPlayer().getUniqueId()))
      return;
    
    Material clickedBlockMaterial = clickedBlock.getType();
    if (!Arrays.stream(BUTTONS).anyMatch(clickedBlockMaterial::equals)) return;
    
    List<CartJetsModel> cartJets = null;
    try {
      cartJets =
        CartJetsPlugin.getInstance().getCartJetsDao().queryForAll();
    } catch (SQLException ex) {
      CartJetsPlugin.getInstance().getLogger().log(Level.SEVERE, null, ex);
    }
    if (cartJets == null) return;
    
    boolean buttonPresent =
      cartJets.stream()
        .anyMatch(b -> b.getButtonLocation().equals(clickedBlock.getLocation()));
    if (!buttonPresent) return;
    
    Optional<CartJetsModel> cartJet =
      cartJets.stream()
        .filter(b -> b.getButtonLocation().equals(clickedBlock.getLocation()))
        .findFirst();
    
		if (!cartJet.isPresent()) {
			return;
		}
		
		ev.setCancelled(true);
		
		Location spawningLocation = cartJet.get().getMinecartSpawningLocation();
		
    Minecart spawnedMinecart = 
      (Minecart) ev.getPlayer().getWorld().spawnEntity(
				spawningLocation,
        EntityType.MINECART
      );
    
		spawnedMinecart.setInvulnerable(true);
		spawnedMinecart.setMaxSpeed(
			AppConfiguration.getInstance()
				.getDouble(CurrentEntries.CONF_MAXSPEED.toString())
		);
		
    spawnedMinecart.addPassenger(ev.getPlayer());
    
    spawnedMinecart.setMetadata(
      "cartjet.is",
      new FixedMetadataValue(CartJetsPlugin.getInstance(), true)
    );
		
		CartJetsManager.getInstrance().addMinecart(spawnedMinecart.getUniqueId());
  }
}
