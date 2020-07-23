// 
// Private License
// 
// Copyright (c) 2019-2020 Joel Strasser <strasser999@gmail.com>
// 
// Only the copyright holder is allowed to use this software.
// 
package at.joestr.cartjets.configuration;

import com.vdurmont.semver4j.Semver;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joel
 */
public class JenkinsUpdater {
	
	public enum Mode {
		OFF,
		RELEASES,
		SNAPSHOTS_AND_RELEASES
	}
	
	public enum State {
		ERROR_OFF,
		SUCCESS_UPTODATE,
		SUCCESS_AVAILABLE,
		SUCCES_DOWNLOADED
	}
	
	public class Update {
		private Semver currentVersion;
		private Semver newVersion;
		private String downloadUrl;
		private LocalDateTime expiry;

		public Update(Semver currentVersion, Semver newVersion, String downloadUrl, LocalDateTime expiry) {
			this.currentVersion = currentVersion;
			this.newVersion = newVersion;
			this.downloadUrl = downloadUrl;
			this.expiry = expiry;
		}

		public Semver getCurrentVersion() {
			return currentVersion;
		}

		public void setCurrentVersion(Semver currentVersion) {
			this.currentVersion = currentVersion;
		}

		public Semver getNewVersion() {
			return newVersion;
		}

		public void setNewVersion(Semver newVersion) {
			this.newVersion = newVersion;
		}

		public String getDownloadUrl() {
			return downloadUrl;
		}

		public void setDownloadUrl(String downloadUrl) {
			this.downloadUrl = downloadUrl;
		}

		public LocalDateTime getExpiry() {
			return expiry;
		}

		public void setExpiry(LocalDateTime expiry) {
			this.expiry = expiry;
		}
	}
	
	private final Mode mode;
	private final boolean autoUpdate;
	private final Semver currentVersion;
	private final String rootUrl;
	private final String pomPropsUrl;
	private final Properties pomProps;
	private final String classifier;
	private final File targetFolder;
	private Update lastUpdate;

	public JenkinsUpdater(Mode mode, boolean autoUpdate, String currentVersion, String rootUrl, String pomProperties, String classifier, File targetFolder) {
		this.mode = mode;
		this.autoUpdate = autoUpdate;
		this.currentVersion = new Semver(currentVersion, Semver.SemverType.IVY);
		this.rootUrl = rootUrl;
		this.pomPropsUrl = new StringBuilder(rootUrl).append("/").append(pomProperties).toString();
		this.pomProps = new Properties();
		this.classifier = classifier;
		this.targetFolder = targetFolder;
		this.lastUpdate = null;
	}
	
	public CompletableFuture<State> checkForUpdate() {
		return CompletableFuture.supplyAsync(() -> {
			if (mode.equals(Mode.OFF)) return State.ERROR_OFF;
			
			if ((lastUpdate == null) || (lastUpdate.getExpiry().isBefore(LocalDateTime.now()))) {
				downloadPomProperties(pomPropsUrl);
			
				Semver newVersion =
					new Semver(pomProps.getProperty("version", "0.1.0-SNAPSHOT"), Semver.SemverType.IVY);

				if (!newVersion.isGreaterThan(currentVersion)) return State.SUCCESS_UPTODATE;

				lastUpdate = new Update(
						currentVersion,
						newVersion,
						new StringBuilder(rootUrl)
							.append("/")
							.append(pomProps.getProperty("artifactId"))
							.append("-")
							.append(newVersion.getOriginalValue())
							.append(classifier.equalsIgnoreCase("") ? "" : "-" + classifier)
							.append(".jar")
							.toString(),
						LocalDateTime.now().plusHours(12)
					);
			}
			
			if (mode.equals(Mode.RELEASES) && lastUpdate.getNewVersion().isStable() && !autoUpdate) {
				return State.SUCCESS_AVAILABLE;
			}
			
			if (mode.equals(Mode.SNAPSHOTS_AND_RELEASES) && !autoUpdate) {
				return State.SUCCESS_AVAILABLE;
			}
			
			if (mode.equals(Mode.RELEASES) && lastUpdate.getNewVersion().isStable() && autoUpdate) {
				this.downloadUpdateTo(targetFolder);
				return State.SUCCES_DOWNLOADED;
			}
			
			if (mode.equals(Mode.SNAPSHOTS_AND_RELEASES) && autoUpdate) {
				this.downloadUpdateTo(targetFolder);
				return State.SUCCES_DOWNLOADED;
			}
			
			return State.ERROR_OFF;
		});
	}
	
	private void downloadPomProperties(String pomPropertiesUrl) {
		try (InputStream inputStream = new URL(pomPropertiesUrl).openStream()) {
			this.pomProps.load(inputStream);
		} catch (IOException ex) {
			Logger.getGlobal().log(Level.SEVERE, "", ex);
		}
	}
	
	private boolean downloadUpdateTo(File folder) {
		boolean result = false;
		
		if (!folder.exists() || !folder.canWrite())
			return result;

		URL downloadUrl = null;
		try {
			 downloadUrl = new URL(lastUpdate.getDownloadUrl());
		} catch (MalformedURLException ex) {
			Logger.getLogger(JenkinsUpdater.class.getName()).log(Level.SEVERE, null, ex);
		}
		if (downloadUrl == null) return result;

		try (InputStream newFile = downloadUrl.openStream()) {
			Files.copy(
				newFile,
				new File(folder, pomProps.getProperty("artifactId") + ".jar").toPath(),
				StandardCopyOption.REPLACE_EXISTING
			);
		} catch (IOException ex) {
			Logger.getLogger(JenkinsUpdater.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		result = true;

		return result;
	}

	public Update getLastUpdate() {
		return lastUpdate;
	}
}
