// 
// Copyright (c) 2020 Joel Strasser <strasser999@gmail.com>
// 
// Licensed under the EUPL-1.2
// 
package at.joestr.cartjets.commands;

import at.joestr.cartjets.configuration.CurrentEntries;
import at.joestr.cartjets.utils.LocaleHelper;
import at.joestr.cartjets.utils.MessageHelper;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Locale;
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

		final Locale locale
			= sender instanceof Player
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

		if (sender.hasPermission(CurrentEntries.PERM_CMD_CARTJETS_SETUPWIZARD.toString())) {
			new MessageHelper()
				.path(CurrentEntries.LANG_CMD_CARTJETS_X_MSG_SETUPWIZARD)
				.locale(locale)
				.receiver(sender)
				.send();
		}

		if (sender.hasPermission(CurrentEntries.PERM_CMD_CARTJETS_LIST.toString())) {
			new MessageHelper()
				.path(CurrentEntries.LANG_CMD_CARTJETS_X_MSG_LIST)
				.locale(locale)
				.receiver(sender)
				.send();
		}

		if (sender.hasPermission(CurrentEntries.PERM_CMD_CARTJETS_DELETE.toString())) {
			new MessageHelper()
				.path(CurrentEntries.LANG_CMD_CARTJETS_X_MSG_DELETE)
				.locale(locale)
				.receiver(sender)
				.send();
		}

		if (sender.hasPermission(CurrentEntries.PERM_CMD_CARTJETS_UPDATE.toString())) {
			new MessageHelper()
				.path(CurrentEntries.LANG_CMD_CARTJETS_X_MSG_UPDATE)
				.locale(locale)
				.receiver(sender)
				.send();
		}

		return true;
	}
}
