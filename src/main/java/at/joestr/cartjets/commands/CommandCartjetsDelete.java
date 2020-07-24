// 
// Copyright (c) 2020 Joel Strasser <strasser999@gmail.com>
// 
// Licensed under the EUPL-1.2
// 
package at.joestr.cartjets.commands;

import at.joestr.cartjets.CartJetsPlugin;
import at.joestr.cartjets.models.CartJetsModel;
import at.joestr.cartjets.configuration.CurrentEntries;
import at.joestr.cartjets.utils.LocaleHelper;
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
			buttonNames
				= CartJetsPlugin.getInstance().getCartJetsDao()
					.queryForAll()
					.stream().map(CartJetsModel::getName)
					.collect(Collectors.toList());
		} catch (SQLException ex) {
			CartJetsPlugin.getInstance().getLogger().log(Level.SEVERE, null, ex);
			throw new RuntimeException("An SQL error occured", ex);
		}

		if (args.length == 1) {
			return buttonNames.stream()
				.filter(s -> s.startsWith(args[0]))
				.collect(Collectors.toList());
		}

		return buttonNames;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length != 1) {
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

		int result;
		try {
			result = CartJetsPlugin.getInstance().getCartJetsDao().deleteById(args[0]);
		} catch (SQLException ex) {
			CartJetsPlugin.getInstance().getLogger().log(Level.SEVERE, null, ex);
			throw new RuntimeException("An SQL error occured", ex);
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
