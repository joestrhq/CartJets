/*
Private License

Copyright (c) 2019 Joel Strasser

Only the owner is allowed to use this software.
 */
package at.joestr.cartjets.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joel
 */
public class LanguageConfiguration {

	private static LanguageConfiguration INSTANCE;
	private static final Logger LOG = Logger.getLogger(LanguageConfiguration.class.getName());
	
	private Set<Locale> languagesNotFound;

	public Map<Locale, YamlFileConfiguration> externalLanguageConfigurations;
	public Map<Locale, YamlStreamConfiguration> bundledLanguageConfigurations;
	public Locale fallback;

	private LanguageConfiguration(File externalLanguagesFolder, Map<String, InputStream> bundledLanguages, Locale fallback) throws FileNotFoundException, IOException {
		this.externalLanguageConfigurations = new HashMap<>();
		this.bundledLanguageConfigurations = new HashMap<>();
		this.languagesNotFound = new HashSet<>();
		this.fallback = fallback;

		if (!externalLanguagesFolder.exists()) {
			externalLanguagesFolder.mkdirs();
		}

		for (String languageFileName : bundledLanguages.keySet()) {
			String fileName = languageFileName;
			Locale l =
				Locale.forLanguageTag(
					fileName.contains(".") ? fileName.split("\\.")[0] : fileName
				);
			bundledLanguageConfigurations.put(
				l,
				new YamlStreamConfiguration(
					bundledLanguages.get(languageFileName)
				)
			);
			File externalFile =
				new File(externalLanguagesFolder, fileName);
			if (!externalFile.exists()) {
				bundledLanguageConfigurations.get(l).saveConfigAsFile(
					externalFile
				);
			}
		}

		for (File languageFile : externalLanguagesFolder.listFiles()) {
			String fileName = languageFile.getName();
			externalLanguageConfigurations.put(
				Locale.forLanguageTag(
					fileName.contains(".") ? fileName.split("\\.")[0] : fileName
				),
				new YamlFileConfiguration(languageFile)
			);
		}

		this.fallback = fallback;
	}

	public static LanguageConfiguration getInstance(File externalLanguagesFolder, Map<String, InputStream> bundledLanguagesStream, Locale fallback) throws FileNotFoundException, IOException {
		if (INSTANCE != null) {
			throw new RuntimeException("This class has already been instantiated!");
		}

		INSTANCE = new LanguageConfiguration(externalLanguagesFolder, bundledLanguagesStream, fallback);

		return INSTANCE;
	}

	public static LanguageConfiguration getInstance() {
		if (INSTANCE == null) {
			throw new RuntimeException("This class has not been instantiated yet!");
		}

		return INSTANCE;
	}

	private void externalLanguageNotFound(Locale locale) {
		if (!this.languagesNotFound.add(locale)) return;
		
		LOG.log(
			Level.WARNING,
			"The external language file {0}.yml ({1}) was not found!",
			new Object[] {locale.toLanguageTag(), locale.getDisplayName()}
		);
	}

	private void pathNotInExternalLanguage(String path, Locale locale) {
		LOG.log(
			Level.WARNING,
			"The path {0} was not found in {1}!",
			new Object[] {path, this.externalLanguageConfigurations.get(locale)}
		);
	}

	public String getString(String path, Locale locale) {
		YamlFileConfiguration configFile
			= this.externalLanguageConfigurations.get(locale);
		if (configFile != null) {
			String result
				= configFile.getString(path);
			if (result != null) {
				return result;
			} else {
				this.pathNotInExternalLanguage(path, locale);
			}
		} else {
			this.externalLanguageNotFound(locale);
		}

		YamlStreamConfiguration configStream
			= this.bundledLanguageConfigurations.get(locale);
		if (configStream != null) {
			String result
				= configStream.getString(path);
			if (result != null) {
				return result;
			}
		}

		configStream
			= this.bundledLanguageConfigurations.get(fallback);
		if (configStream != null) {
			String result
				= configStream.getString(path);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
}
