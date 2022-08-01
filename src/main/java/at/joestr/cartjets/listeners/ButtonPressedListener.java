//
// Copyright (c) 2020-2022 Joel Strasser <strasser999@gmail.com>
//
// Licensed under the EUPL-1.2 license.
//
// For the full license text consult the 'LICENSE' file from the repository.
//

package at.joestr.cartjets.listeners;

import at.joestr.cartjets.CartJetsPlugin;
import at.joestr.cartjets.configuration.AppConfiguration;
import at.joestr.cartjets.configuration.CurrentEntries;
import at.joestr.cartjets.models.CartJetsModel;
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
 * @author Joel
 */
public class ButtonPressedListener implements Listener {

  private static final Material[] BUTTONS =
      new Material[] {
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
    if (!EquipmentSlot.HAND.equals(ev.getHand())) {
      return;
    }

    Block clickedBlock = ev.getClickedBlock();

    if (clickedBlock == null) {
      return;
    }

    if (CartJetsPlugin.getInstance().getPerUserModels().containsKey(ev.getPlayer().getUniqueId())) {
      return;
    }

    Material clickedBlockMaterial = clickedBlock.getType();
    if (!Arrays.stream(BUTTONS).anyMatch(clickedBlockMaterial::equals)) {
      return;
    }

    List<CartJetsModel> cartJets = null;
    try {
      cartJets = CartJetsPlugin.getInstance().getCartJetsDao().queryForAll();
    } catch (SQLException ex) {
      CartJetsPlugin.getInstance().getLogger().log(Level.SEVERE, null, ex);
    }
    if (cartJets == null) {
      return;
    }

    boolean buttonPresent =
        cartJets.stream().anyMatch(b -> b.getButtonLocation().equals(clickedBlock.getLocation()));
    if (!buttonPresent) {
      return;
    }

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
        (Minecart) ev.getPlayer().getWorld().spawnEntity(spawningLocation, EntityType.MINECART);

    spawnedMinecart.setInvulnerable(true);
    spawnedMinecart.setMaxSpeed(
        AppConfiguration.getInstance().getDouble(CurrentEntries.CONF_MAXSPEED.toString()));

    spawnedMinecart.addPassenger(ev.getPlayer());

    spawnedMinecart.setMetadata(
        "cartjet.is", new FixedMetadataValue(CartJetsPlugin.getInstance(), true));

    spawnedMinecart.setVelocity(ev.getPlayer().getEyeLocation().getDirection());

    CartJetsManager.getInstance().addMinecart(spawnedMinecart.getUniqueId());
  }
}
