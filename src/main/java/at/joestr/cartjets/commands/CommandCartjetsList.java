//
// Copyright (c) 2020-2022 Joel Strasser <strasser999@gmail.com>
//
// Licensed under the EUPL-1.2 license.
//
// For the full license text consult the 'LICENSE' file from the repository.
//

package at.joestr.cartjets.commands;

import at.joestr.cartjets.CartJetsPlugin;
import at.joestr.cartjets.configuration.CurrentEntries;
import at.joestr.cartjets.utils.LocaleHelper;
import at.joestr.cartjets.utils.MessageHelper;
import com.google.common.collect.ImmutableList;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

/**
 * @author Joel
 */
public class CommandCartjetsList implements TabExecutor {

  @Override
  public List<String> onTabComplete(
      CommandSender sender, Command command, String alias, String[] args) {
    return ImmutableList.of();
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (args.length != 0) {
      return false;
    }

    final Locale locale =
        sender instanceof Player
            ? LocaleHelper.resolve(((Player) sender).getLocale())
            : Locale.ENGLISH;

    if (!(sender instanceof Player)) {
      new MessageHelper()
          .path(CurrentEntries.LANG_GEN_NOT_A_PLAYER)
          .locale(locale)
          .receiver(sender)
          .send();
      return true;
    }

    StringBuilder lineListAsString = new StringBuilder();
    try {
      lineListAsString.append(
          CartJetsPlugin.getInstance().getCartJetsDao().queryForAll().stream()
              .map(b -> b.getName())
              .collect(Collectors.joining(", ", "", "")));
    } catch (SQLException ex) {
      CartJetsPlugin.getInstance().getLogger().log(Level.SEVERE, null, ex);
      throw new RuntimeException(null, ex);
    }

    new MessageHelper()
        .prefix(true)
        .path(CurrentEntries.LANG_CMD_CARTJETS_LIST_MESSAGE)
        .locale(locale)
        .receiver(sender)
        .modify(s -> s.replace("%lines", lineListAsString.toString()))
        .send();
    return true;
  }
}
