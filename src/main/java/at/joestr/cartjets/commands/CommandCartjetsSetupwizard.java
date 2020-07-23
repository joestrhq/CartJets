// 
// Copyright (c) 2020 Joel Strasser <strasser999@gmail.com>
// 
// Licensed under the EUPL-1.2
// 
package at.joestr.cartjets.commands;

import at.joestr.cartjets.CartJetsPlugin;
import at.joestr.cartjets.configuration.CurrentEntries;
import at.joestr.cartjets.models.CartJetsModel;
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
public class CommandCartjetsSetupwizard implements TabExecutor {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return ImmutableList.of();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
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

		Player player = (Player) sender;

		if (CartJetsPlugin.getInstance().getPerUserModels().containsKey(player.getUniqueId())) {
			CartJetsPlugin.getInstance().getPerUserModels().remove(player.getUniqueId());
			new MessageHelper()
				.path(CurrentEntries.LANG_CMD_CARTJETS_SETUPWIZARD_CANCEL)
				.locale(locale)
				.receiver(sender)
				.send();
		} else {
			CartJetsPlugin.getInstance().getPerUserModels().put(player.getUniqueId(), new CartJetsModel());
			new MessageHelper()
				.path(CurrentEntries.LANG_CMD_CARTJETS_SETUPWIZARD_BUTTON_INSTRUCTION)
				.locale(locale)
				.receiver(sender)
				.send();
		}

		return true;
	}
}
