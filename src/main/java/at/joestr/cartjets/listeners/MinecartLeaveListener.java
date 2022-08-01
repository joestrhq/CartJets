//
// Copyright (c) 2020-2022 Joel Strasser <strasser999@gmail.com>
//
// Licensed under the EUPL-1.2 license.
//
// For the full license text consult the 'LICENSE' file from the repository.
//

package at.joestr.cartjets.listeners;

import at.joestr.cartjets.CartJetsPlugin;
import at.joestr.cartjets.utils.CartJetsManager;
import java.util.List;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.metadata.MetadataValue;
import org.spigotmc.event.entity.EntityDismountEvent;

/**
 * @author Joel
 */
public class MinecartLeaveListener implements Listener {

  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onMinecartLeave(EntityDismountEvent ev) {
    Entity dismounted = ev.getDismounted();

    if (dismounted.getType() != EntityType.MINECART) {
      return;
    }

    List<MetadataValue> metadataValues = dismounted.getMetadata("cartjet.is");

    if (metadataValues.isEmpty()) {
      return;
    }

    MetadataValue metadataValue = metadataValues.get(0);

    if (metadataValue.getOwningPlugin() != CartJetsPlugin.getInstance()) {
      return;
    }

    if (!metadataValue.asBoolean()) {
      return;
    }

    CartJetsManager.getInstance().removeMinecart(dismounted.getUniqueId());

    dismounted.remove();
  }
}
