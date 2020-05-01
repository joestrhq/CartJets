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
package at.joestr.cartjets.commands;

import at.joestr.cartjets.CartJetsPlugin;
import at.joestr.cartjets.utils.CurrentEntries;
import at.joestr.cartjets.utils.MessageHelper;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Locale;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

/**
 *
 * @author Joel
 */
public class CommandCartjets implements TabExecutor {

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    return ImmutableList.of();
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
    if (args.length != 0) {
      return false;
    }
    
    Locale l =
      sender instanceof Player ? Locale.forLanguageTag(((Player) sender).getLocale()) : Locale.ENGLISH;
    final Locale locale = l != null ? l : Locale.ENGLISH;
    
    if (!(sender instanceof Player)) {
      new MessageHelper()
        .path(CurrentEntries.LANG_GEN_NOT_A_PLAYER)
        .locale(locale)
        .receiver(sender)
        .send();
      return true;
    }
    
    CartJetsPlugin.getInstance().getDescription().getCommands().forEach((cmd, map) -> {
      if (Bukkit.getServer().getPluginCommand(cmd).testPermissionSilent(sender)) {
        new MessageHelper()
          .path(CurrentEntries.find((String) map.get("permission")))
          .locale(locale)
          .receiver(sender)
          .send();
      }
    });
    return true;
  }
}
