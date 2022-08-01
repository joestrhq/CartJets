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
import at.joestr.cartjets.configuration.Updater.State;
import at.joestr.cartjets.utils.LocaleHelper;
import at.joestr.cartjets.utils.MessageHelper;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

/**
 * @author Joel
 */
public class CommandCartjetsUpdate implements TabExecutor {

  @Override
  public List<String> onTabComplete(
      CommandSender sender, Command command, String alias, String[] args) {
    return Collections.emptyList();
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
          .prefix(true)
          .path(CurrentEntries.LANG_GEN_NOT_A_PLAYER)
          .locale(locale)
          .receiver(sender)
          .send();
      return true;
    }

    new MessageHelper()
        .prefix(true)
        .path(CurrentEntries.LANG_CMD_CARTJETS_UPDATE_ASYNCSTART)
        .locale(locale)
        .receiver(sender)
        .send();

    CartJetsPlugin.getInstance()
        .getUpdater()
        .checkForUpdate()
        .whenCompleteAsync(
            (optionalUpdate, error) -> {
              if (error != null) {
                new MessageHelper()
                    .prefix(true)
                    .path(CurrentEntries.LANG_CMD_CARTJETS_UPDATE_ERROR)
                    .locale(locale)
                    .receiver(sender)
                    .send();
                return;
              }

              if (optionalUpdate.equals(State.SUCCESS_UPTODATE)) {
                new MessageHelper()
                    .prefix(true)
                    .path(CurrentEntries.LANG_CMD_CARTJETS_UPDATE_UPTODATE)
                    .locale(locale)
                    .receiver(sender)
                    .send();
                return;
              }

              if (optionalUpdate.equals(State.SUCCESS_AVAILABLE)) {
                new MessageHelper()
                    .prefix(true)
                    .path(CurrentEntries.LANG_CMD_CARTJETS_UPDATE_AVAILABLE)
                    .locale(locale)
                    .receiver(sender)
                    .modify(msg -> msg.replace("%update$downloadUrl", "...."))
                    .send();
                return;
              }

              if (optionalUpdate.equals(State.SUCCES_DOWNLOADED)) {
                new MessageHelper()
                    .prefix(true)
                    .path(CurrentEntries.LANG_CMD_CARTJETS_UPDATE_DOWNLOADED)
                    .locale(locale)
                    .receiver(sender)
                    .send();
              }
            });

    return true;
  }
}
