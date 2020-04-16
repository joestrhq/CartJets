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
import at.joestr.cartjets.models.CartJetsModel;
import at.joestr.cartjets.utils.CurrentEntries;
import at.joestr.cartjets.utils.MessageHelper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

/**
 *
 * @author Joel
 */
public class CommandCartjetsDelete implements TabExecutor {

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    List<String> buttonNames = new ArrayList<>();
    
    if (args.length > 1) {
      return buttonNames;
    }
    
    try {
      buttonNames =
        CartJetsPlugin.getInstance().getCartJetsDao()
          .queryForAll()
          .stream().map(CartJetsModel::getName)
          .collect(Collectors.toList());
    } catch (SQLException ex) {
      CartJetsPlugin.getInstance().getLogger().log(Level.SEVERE, null, ex);
      throw new RuntimeException(null, ex);
    }
    
    if (args.length == 1) {
      return buttonNames.stream()
        .filter((s) -> s.startsWith(args[0]))
        .collect(Collectors.toList());
    }
    
    return buttonNames;
  }
  
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {    
    if (args.length != 1) {
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
    
    int result;
    try {
      result = CartJetsPlugin.getInstance().getCartJetsDao().deleteById(args[0]);
    } catch (SQLException ex) {
      CartJetsPlugin.getInstance().getLogger().log(Level.SEVERE, null, ex);
      throw new RuntimeException(null, ex);
    }
    
    if (result == -1) {
      new MessageHelper()
        .path(CurrentEntries.LANG_CMD_CARTJETS_DELETE_NON_EXISTING)
        .locale(locale)
        .receiver(sender)
        .send();
    }
    
    new MessageHelper()
      .path(CurrentEntries.LANG_CMD_CARTJETS_DELETE_SUCCESS)
      .locale(locale)
      .receiver(sender)
      .send();
    return true;
  } 
}
