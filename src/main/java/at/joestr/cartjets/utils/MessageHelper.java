// 
// Copyright (c) 2020 Joel Strasser <strasser999@gmail.com>
// 
// Licensed under the EUPL-1.2
// 
package at.joestr.cartjets.utils;

import at.joestr.cartjets.configuration.CurrentEntries;
import at.joestr.cartjets.configuration.LanguageConfiguration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Joel
 */
public class MessageHelper {

	private CurrentEntries path;
	private Function<String, String> modify;
	private Locale locale;
	private CommandSender receiver;
	private boolean showPrefix;
	private List<Function<String, String>> modifiers;

	public MessageHelper() {
		this.modifiers = new ArrayList<>();
	}

	public MessageHelper path(CurrentEntries path) {
		this.path = path;
		return this;
	}

	public MessageHelper locale(Locale locale) {
		this.locale = locale;
		return this;
	}

	public MessageHelper modify(Function<String, String> modify) {
		this.modify = modify;
		return this;
	}

	public MessageHelper addModifier(Function<String, String> modifier) {
		this.modifiers.add(modifier);
		return this;
	}

	public MessageHelper receiver(CommandSender receiver) {
		this.receiver = receiver;
		return this;
	}

	public MessageHelper prefix(boolean show) {
		this.showPrefix = show;
		return this;
	}

	public void send() {
		String message
			= LanguageConfiguration.getInstance()
				.getString(
					this.path.toString(),
					this.locale
				);

		if (showPrefix) {
			message = message.replace("%prefix",
				new MessageHelper()
					.locale(this.locale)
					.path(CurrentEntries.LANG_PREFIX)
					.string()
			);
		} else {
			message = message.replace("%prefix", "");
		}

		if (this.modify != null) {
			message = this.modify.apply(message);
		}

		for (Function<String, String> modifier : this.modifiers) {
			modifier.apply(message);
		}

		receiver.spigot().sendMessage(
			ComponentSerializer.parse(message)
		);
	}

	public String string() {
		return LanguageConfiguration.getInstance()
			.getString(
				this.path.toString(),
				this.locale
			);
	}
}
