/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.joestr.cartjets.configuration;

import com.sun.xml.internal.bind.api.impl.NameConverter;
import com.vdurmont.semver4j.Semver;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Joel
 */
public class MavenUpdater {
	
	public enum Mode {
		OFF,
		RELEASES,
		SNAPSHOTS_AND_RELEASES
	}
	
	private final Mode mode;
	private final String currentVersion;
	private final String releaseRepo;
	private final String snapshotRepo;
	private final String projectPath;
	private final String suffix;
	private Update lastUpdate;

	public MavenUpdater(Mode mode, String currentVersion, String releaseRepo, String snapshotRepo, String projectPath, String suffix) {
		this.mode = mode;
		this.currentVersion = currentVersion;
		this.releaseRepo = releaseRepo;
		this.snapshotRepo = snapshotRepo;
		this.projectPath = projectPath;
		this.suffix = suffix;
	}
	
	public CompletableFuture<Optional<Update>> checkForUpdate() {
		return CompletableFuture.supplyAsync(() -> {
			Update result = null;
			
			if (lastUpdate.getExpiry().isAfter(LocalDateTime.now())) {
				return Optional.of(lastUpdate);
			} 
			
			if (mode.equals(Mode.OFF)) return Optional.empty();
			
			String newVersion = checkForUpdate(
				releaseRepo + projectPath + "maven-metadata.xml"
			);

			if (newVersion != null) {
				result = new Update(
					currentVersion,
					newVersion,
					releaseRepo + projectPath + "-" + newVersion + (suffix.equalsIgnoreCase("") ? "-" + suffix : "") + ".jar",
					LocalDateTime.now().plusHours(12)
			);
			}
			
			if (mode.equals(Mode.RELEASES) && newVersion != null) {
				lastUpdate = result;
				return Optional.of(result);
			}
			
			newVersion = checkForUpdate(
				snapshotRepo + projectPath + "maven-metadata.xml"
			);

			if (newVersion != null) {
				result = new Update(
					currentVersion,
					newVersion,
					snapshotRepo + projectPath + "-" + newVersion + (suffix.equalsIgnoreCase("") ? "-" + suffix : "") + ".jar",
					LocalDateTime.now().plusHours(12)
				);
			}
			
			if (mode.equals(Mode.SNAPSHOTS_AND_RELEASES) && newVersion != null) {
				lastUpdate = result;
				return Optional.of(result);
			}
			
			return Optional.empty();
		});
	}
	
	private String checkForUpdate(String mavenMetadataUrl) {
		Document document = null;
		
		try {
			InputStream iS = new URL(mavenMetadataUrl).openStream();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			document = db.parse(iS);
		} catch (IOException | ParserConfigurationException | SAXException ex) {
			return null;
		}
		
		String nVal = document.getElementsByTagName("latest").item(0).getNodeValue();
		
		Semver currenVersion = new Semver(this.currentVersion);
		Semver newVersion = new Semver(nVal, Semver.SemverType.IVY);
		
		if (currenVersion.isLowerThan(newVersion)) return nVal;
		
		return null;
	}
	
	private CompletableFuture<Boolean> downloadUpdate(File folder) {
		return CompletableFuture.supplyAsync(() -> {
			if (!folder.exists()) return false;
			if (!folder.canWrite()) return false;
			
			if (lastUpdate == null) {
				try {
					lastUpdate = checkForUpdate().get().get();
				} catch (InterruptedException | ExecutionException ex) {
					Logger.getLogger(MavenUpdater.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			
			if (lastUpdate == null) return false;
			
			URL downloadUrl = null;
			try {
				 downloadUrl = new URL(lastUpdate.getDownloadUrl());
			} catch (MalformedURLException ex) {
				Logger.getLogger(MavenUpdater.class.getName()).log(Level.SEVERE, null, ex);
			}
			if (downloadUrl == null) return false;
			
			;
			
			try {
				Files.copy(downloadUrl.openStream(), folder.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException ex) {
				Logger.getLogger(MavenUpdater.class.getName()).log(Level.SEVERE, null, ex);
			}
			
			return false;
		});
	}
}
