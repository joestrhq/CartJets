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

import java.util.List;
import at.joestr.cartjets.CartJetsPlugin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.metadata.MetadataValue;
import org.spigotmc.event.entity.EntityDismountEvent;

/**
 *
 * @author Joel
 */
public class MinecartLeaveListener {
  
  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onMinecartLeave(EntityDismountEvent ev) {
    Entity dismounted = ev.getDismounted();
    
    if (dismounted.getType() != EntityType.MINECART) return;
    
    List<MetadataValue> metadataValues = dismounted.getMetadata("cartjets.is");
    
    if (metadataValues.isEmpty()) return;
    
    MetadataValue metadataValue = metadataValues.get(0);
    
    if (metadataValue.getOwningPlugin() != CartJetsPlugin.getInstance()) return;
    
    if (metadataValue.asBoolean() != true) return;
    
    dismounted.remove();
  }
}
