//
// Copyright (c) 2020-2022 Joel Strasser <strasser999@gmail.com>
//
// Licensed under the EUPL-1.2 license.
//
// For the full license text consult the 'LICENSE' file from the repository.
//

package at.joestr.cartjets.listeners;

import at.joestr.cartjets.CartJetsPlugin;
import at.joestr.cartjets.configuration.CurrentEntries;
import at.joestr.cartjets.models.CartJetsModel;
import at.joestr.cartjets.utils.MessageHelper;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

/**
 * @author Joel
 */
public class SetupwizardButtonPressedListener implements Listener {

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

    if (!CartJetsPlugin.getInstance()
        .getPerUserModels()
        .containsKey(ev.getPlayer().getUniqueId())) {
      return;
    }

    if (CartJetsPlugin.getInstance()
            .getPerUserModels()
            .get(ev.getPlayer().getUniqueId())
            .getButtonLocation()
        != null) {
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
    if (buttonPresent) {
      return;
    }

    Optional<CartJetsModel> cartJet =
        cartJets.stream()
            .filter(b -> b.getButtonLocation().equals(clickedBlock.getLocation()))
            .findFirst();

    Locale l = Locale.forLanguageTag(ev.getPlayer().getLocale());
    final Locale locale = l != null ? l : Locale.ENGLISH;

    if (cartJet.isPresent()) {
      new MessageHelper()
          .prefix(true)
          .path(CurrentEntries.LANG_CMD_CARTJETS_SETUPWIZARD_BUTTON_OVERLAPPING)
          .locale(locale)
          .receiver(ev.getPlayer())
          .modify(s -> s.replace("%line", cartJet.get().getName()))
          .send();
      ev.setCancelled(true);
      return;
    }

    CartJetsPlugin.getInstance()
        .getPerUserModels()
        .get(ev.getPlayer().getUniqueId())
        .setButtonLocation(ev.getClickedBlock().getLocation());
    new MessageHelper()
        .prefix(true)
        .path(CurrentEntries.LANG_CMD_CARTJETS_SETUPWIZARD_BUTTON_SUCCESS)
        .locale(locale)
        .receiver(ev.getPlayer())
        .send();

    new MessageHelper()
        .prefix(true)
        .path(CurrentEntries.LANG_CMD_CARTJETS_SETUPWIZARD_RAIL_INSTRUCTION)
        .locale(locale)
        .receiver(ev.getPlayer())
        .send();

    ev.setCancelled(true);
  }
}
