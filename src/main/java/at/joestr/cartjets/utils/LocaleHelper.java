// 
// Copyright (c) 2020 Joel Strasser <strasser999@gmail.com>
// 
// Licensed under the EUPL-1.2
// 
package at.joestr.cartjets.utils;

import java.util.Locale;

/**
 *
 * @author Joel
 */
public class LocaleHelper {

	private LocaleHelper() {
		throw new IllegalMonitorStateException("Utility class");
	}

	public static Locale resolve(final String locale) {
		// Minecraft's format: e.g. de_de, en_gb
		String[] localeTagParts = locale.split("_");

		Locale l
			= localeTagParts.length > 1
				? new Locale(localeTagParts[0]) // Use the language part only.
				: Locale.ENGLISH;
		return l != null ? l : Locale.ENGLISH;
	}
}
