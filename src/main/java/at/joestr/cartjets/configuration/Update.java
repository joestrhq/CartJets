/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.joestr.cartjets.configuration;

import java.net.URL;

/**
 *
 * @author Joel
 */
public class Update {
	private String currentVersion;
	private String newVersion;
	private String downloadUrl;

	public Update(String currentVersion, String newVersion, String downloadUrl) {
		this.currentVersion = currentVersion;
		this.newVersion = newVersion;
		this.downloadUrl = downloadUrl;
	}

	public String getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}

	public String getNewVersion() {
		return newVersion;
	}

	public void setNewVersion(String newVersion) {
		this.newVersion = newVersion;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
}
