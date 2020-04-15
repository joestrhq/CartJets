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

import at.joestr.cartjets.utils.MessageHelper;
import at.joestr.cartjets.CartJetsPlugin;
import at.joestr.cartjets.models.CartJetsModel;
import at.joestr.cartjets.utils.CurrentEntries;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Joel
 */
public class RailClickListener implements Listener {
  
  private static final Material[] RAILS = new Material[] {
    Material.RAIL,
    Material.POWERED_RAIL,
    Material.ACTIVATOR_RAIL,
    Material.DETECTOR_RAIL
  };
  
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onButtonClicked(PlayerInteractEvent ev) {
    Block clickedBlock = ev.getClickedBlock();
    if (clickedBlock == null) return;
    
    if (!CartJetsPlugin.getInstance().getPlayerModels().containsKey(ev.getPlayer().getUniqueId()))
      return;
    
    if (CartJetsPlugin.getInstance().getPlayerModels().get(ev.getPlayer().getUniqueId()).getMinecartSpawningLocation() != null)
      return;
    
    Material clickedBlockMaterial = clickedBlock.getType();
    if (!Arrays.stream(RAILS).anyMatch(clickedBlockMaterial::equals)) return;
    
    Locale l = Locale.forLanguageTag(ev.getPlayer().getLocale());
    final Locale locale = l != null ? l : Locale.ENGLISH;
    
    new MessageHelper()
      .path(CurrentEntries.LANG_CMD_CARTJETS_SETUPWIZARD_RAIL_SUCCESS)
      .locale(locale)
      .receiver(ev.getPlayer())
      .send();
    
    new AnvilGUI.Builder()
    .onClose(player -> {
        new MessageHelper()
          .receiver(player)
          .locale(locale)
          .path(CurrentEntries.LANG_CMD_CARTJETS_SETUPWIZARD_CANCEL)
          .send();
    })
    .onComplete((player, text) -> {
      List<CartJetsModel> cartJetsButtons = null;
      try {
        cartJetsButtons =
          CartJetsPlugin.getInstance().getCartJetsButtonsDao().queryForAll();
      } catch (SQLException ex) {
        CartJetsPlugin.getInstance().getLogger().log(Level.SEVERE, null, ex);
      }
      if (cartJetsButtons == null) return AnvilGUI.Response.close();;

      Optional<CartJetsModel> cartJets =
        cartJetsButtons.stream()
          .filter((b) -> {
            return b.getName().equalsIgnoreCase(text);
          })
          .findFirst();
      
      if(cartJets.isPresent()) {
        try {
          CartJetsPlugin.getInstance().getCartJetsButtonsDao().create(cartJets.get());
        } catch (SQLException ex) {
          CartJetsPlugin.getInstance().getLogger().log(Level.SEVERE, null, ex);
        }
        new MessageHelper()
          .receiver(player)
          .locale(locale)
          .path(CurrentEntries.LANG_CMD_CARTJETS_SETUPWIZARD_NAME_SUCCESS)
          .send();
        return AnvilGUI.Response.close();
      }
      
      return AnvilGUI.Response.text(
        new MessageHelper().locale(locale)
        .path(CurrentEntries.LANG_CMD_CARTJETS_SETUPWIZARD_NAME_DUPLICATE)
        .string()
      );
    })
    //.preventClose()
    .text(
      new MessageHelper().locale(locale)
        .path(CurrentEntries.LANG_CMD_CARTJETS_SETUPWIZARD_NAME_PLACEHOLDER)
        .string()
    )
    .item(new ItemStack(Material.MINECART))
    .title(
      new MessageHelper().locale(locale)
        .path(CurrentEntries.LANG_CMD_CARTJETS_SETUPWIZARD_NAME_ANVIL_GUI_TEXT)
        .string()
    )
    .plugin(CartJetsPlugin.getInstance())
    .open(ev.getPlayer());
    
    ev.setCancelled(true);
  }
}
