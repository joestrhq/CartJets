/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.joestr.cartjets.configuration;

import com.vdurmont.semver4j.Semver;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

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

	public MavenUpdater(Mode mode, String currentVersion, String releaseRepo, String snapshotRepo, String projectPath, String suffix) {
		this.mode = mode;
		this.currentVersion = currentVersion;
		this.releaseRepo = releaseRepo;
		this.snapshotRepo = snapshotRepo;
		this.projectPath = projectPath;
		this.suffix = suffix;
	}
	
	public CompletableFuture<Update> checkForUpdate() {
		return CompletableFuture.runAsync(() -> {
			Update result = null;
			
			if (mode.equals(Mode.OFF)) return result;
			
			String newVersion = checkForUpdate(
				releaseRepo + projectPath + "maven-metadata.xml"
			);

			if (newVersion != null) {
				result = new Update(
					currentVersion,
					newVersion,
					releaseRepo + projectPath + "-" + newVersion + "-" + suffix + ".jar"
				);
			}
			
			if (mode.equals(Mode.RELEASES) && newVersion != null) {
				return result;
			}
			
			newVersion = checkForUpdate(
				snapshotRepo + projectPath + "maven-metadata.xml"
			);

			if (newVersion != null) {
				result = new Update(
					currentVersion,
					newVersion,
					snapshotRepo + projectPath + "-" + newVersion + "-" + suffix + ".jar"
				);
			}
			
			if (mode.equals(Mode.SNAPSHOTS_AND_RELEASES) && newVersion != null) {
				return result;
			}
			
			return result;
		});
	}
	
	private String checkForUpdate(String mavenMetadataUrl) {
		Document document = null;
		
		try {
			InputStream iS = new URL(mavenMetadataUrl).openStream();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			document = db.parse(iS);
		} catch (Exception ex) {
			return null;
		}
		
		String nVal = document.getElementsByTagName("latest").item(0).getNodeValue();
		
		Semver currenVersion = new Semver(this.currentVersion);
		Semver newVersion = new Semver(nVal, Semver.SemverType.IVY);
		
		if (currenVersion.isLowerThan(newVersion)) return nVal;
		
		return null;
	}
}
