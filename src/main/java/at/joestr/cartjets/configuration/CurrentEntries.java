// 
// Copyright (c) 2020-2022 Joel Strasser <strasser999@gmail.com>
// 
// Licensed under the EUPL-1.2 license.
// 
// For the full license text consult the 'LICENSE' file from the repository.
// 
package at.joestr.cartjets.configuration;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Optional;

/**
 *
 * @author Joel
 */
public enum CurrentEntries {
	// Configuration entries
	CONF_VERSION("version", new int[]{1,2}),
	CONF_JDBCURI("jdbcUri", new int[]{1,2}),
	CONF_MAXSPEED("maxSpeed", new int[]{1,2}),
	CONF_VECTORMULTIPLIER("vectorMultiplier", new int[]{1,2}),
	CONF_TASKREPEATINGDELAYINTICKS("taskRepeatingDelayInTicks", new int[]{1,2}),
	CONF_UPDATER_ENABLED("updater.enabled", new int[]{1,2}),
	CONF_UPDATER_DOWNLOADTOPLUGINUPDATEFOLDER("updater.downloadToPluginUpdateFolder", new int[]{1,2}),
	CONF_UPDATER_TARGETURL("updater.targetUrl", new int[]{1,2}),
	CONF_UPDATER_POMPROPERTIESFILE("updater.pomPropertiesFile", new int[]{1,2}),
	CONF_UPDATER_CLASSIFIER("updater.classifier", new int[]{1,2}),
	// Languages
  LANG_VERSION("version", new int[]{2}),
	LANG_PREFIX("prefix", new int[]{1,2}),
	LANG_GEN_NOT_A_PLAYER("generic.not_a_player", new int[]{1,2}),
	LANG_CMD_CARTJETS_X_MSG_SETUPWIZARD("commands.cartjets.message_setupwizard", new int[]{1,2}),
	LANG_CMD_CARTJETS_X_MSG_DELETE("commands.cartjets.message_delete", new int[]{1,2}),
	LANG_CMD_CARTJETS_X_MSG_LIST("commands.cartjets.message_list", new int[]{1,2}),
	LANG_CMD_CARTJETS_X_MSG_UPDATE("commands.cartjets.message_update", new int[]{1,2}),
	LANG_CMD_CARTJETS_SETUPWIZARD_BUTTON_INSTRUCTION("commands.cartjets-setupwizard.button.instruction", new int[]{1,2}),
	LANG_CMD_CARTJETS_SETUPWIZARD_BUTTON_SUCCESS("commands.cartjets-setupwizard.button.success", new int[]{1,2}),
	LANG_CMD_CARTJETS_SETUPWIZARD_BUTTON_OVERLAPPING("commands.cartjets-setupwizard.button.overlapping", new int[]{1,2}),
	LANG_CMD_CARTJETS_SETUPWIZARD_RAIL_INSTRUCTION("commands.cartjets-setupwizard.rail.instruction", new int[]{1,2}),
	LANG_CMD_CARTJETS_SETUPWIZARD_RAIL_SUCCESS("commands.cartjets-setupwizard.rail.success", new int[]{1,2}),
	LANG_CMD_CARTJETS_SETUPWIZARD_NAME_ANVIL_GUI_TEXT("commands.cartjets-setupwizard.name.anvilgui_text", new int[]{1,2}),
	LANG_CMD_CARTJETS_SETUPWIZARD_NAME_PLACEHOLDER("commands.cartjets-setupwizard.name.placeholder", new int[]{1,2}),
	LANG_CMD_CARTJETS_SETUPWIZARD_NAME_DUPLICATE("commands.cartjets-setupwizard.name.duplicate", new int[]{1,2}),
	LANG_CMD_CARTJETS_SETUPWIZARD_NAME_SUCCESS("commands.cartjets-setupwizard.name.success", new int[]{1,2}),
	LANG_CMD_CARTJETS_SETUPWIZARD_CANCEL("commands.cartjets-setupwizard.cancel", new int[]{1,2}),
	LANG_CMD_CARTJETS_DELETE_NON_EXISTING("commands.cartjets-delete.non_existing", new int[]{1,2}),
	LANG_CMD_CARTJETS_DELETE_SUCCESS("commands.cartjets-delete.success", new int[]{1,2}),
	LANG_CMD_CARTJETS_LIST_MESSAGE("commands.cartjets-list.message", new int[]{1,2}),
	LANG_CMD_CARTJETS_UPDATE_OFF("commands.cartjets-update.off", new int[]{1,2}),
	LANG_CMD_CARTJETS_UPDATE_ASYNCSTART("commands.cartjets-update.asyncstart", new int[]{1,2}),
	LANG_CMD_CARTJETS_UPDATE_ERROR("commands.cartjets-update.error", new int[]{1,2}),
	LANG_CMD_CARTJETS_UPDATE_UPTODATE("commands.cartjets-update.uptodate", new int[]{1,2}),
	LANG_CMD_CARTJETS_UPDATE_AVAILABLE("commands.cartjets-update.available", new int[]{1,2}),
	LANG_CMD_CARTJETS_UPDATE_DOWNLOADED("command.cartjets-update.downloaded", new int[]{1,2}),
	// Permissions
	PERM_CMD_CARTJETS("cartjets.commands.cartjets", new int[]{0}),
	PERM_CMD_CARTJETS_SETUPWIZARD("cartjets.commands.cartjets-setupwizard", new int[]{0}),
	PERM_CMD_CARTJETS_LIST("cartjets.commands.cartjets-list", new int[]{0}),
	PERM_CMD_CARTJETS_DELETE("cartjets.commands.cartjets-delete", new int[]{0}),
	PERM_CMD_CARTJETS_UPDATE("cartjets.commands.cartjets-update", new int[]{0});

	private final String text;
  private final int[] inVersions;

	private CurrentEntries(String text, int[] inVersions) {
		this.text = text;
    this.inVersions = inVersions;
	}

	@Override
	public String toString() {
		return this.text;
  }

	public static CurrentEntries find(String text) {
		Optional<CurrentEntries> result = Arrays.asList(values())
			.stream()
			.filter(cE -> cE.toString().equalsIgnoreCase(text))
			.findFirst();
		if (result.isPresent()) {
			return result.get();
		}
		throw new NullPointerException(MessageFormat.format("The text {0} is not in this Enum!", text));
	}

	public static CurrentEntries[] getConfigurationEntries() {
		return new CurrentEntries[] {
			CONF_VERSION,
			CONF_JDBCURI,
			CONF_MAXSPEED,
			CONF_VECTORMULTIPLIER,
			CONF_TASKREPEATINGDELAYINTICKS,
			CONF_UPDATER_ENABLED,
			CONF_UPDATER_DOWNLOADTOPLUGINUPDATEFOLDER,
			CONF_UPDATER_TARGETURL,
			CONF_UPDATER_POMPROPERTIESFILE,
			CONF_UPDATER_CLASSIFIER
		};
	}

	public static CurrentEntries[] getLanguageEntries() {
		return new CurrentEntries[] {
      LANG_VERSION,
			LANG_PREFIX,
			LANG_GEN_NOT_A_PLAYER,
			LANG_CMD_CARTJETS_X_MSG_SETUPWIZARD,
			LANG_CMD_CARTJETS_X_MSG_DELETE,
			LANG_CMD_CARTJETS_X_MSG_LIST,
			LANG_CMD_CARTJETS_X_MSG_UPDATE,
			LANG_CMD_CARTJETS_SETUPWIZARD_BUTTON_INSTRUCTION,
			LANG_CMD_CARTJETS_SETUPWIZARD_BUTTON_SUCCESS,
			LANG_CMD_CARTJETS_SETUPWIZARD_BUTTON_OVERLAPPING,
			LANG_CMD_CARTJETS_SETUPWIZARD_RAIL_INSTRUCTION,
			LANG_CMD_CARTJETS_SETUPWIZARD_RAIL_SUCCESS,
			LANG_CMD_CARTJETS_SETUPWIZARD_NAME_ANVIL_GUI_TEXT,
			LANG_CMD_CARTJETS_SETUPWIZARD_NAME_PLACEHOLDER,
			LANG_CMD_CARTJETS_SETUPWIZARD_NAME_DUPLICATE,
			LANG_CMD_CARTJETS_SETUPWIZARD_NAME_SUCCESS,
			LANG_CMD_CARTJETS_SETUPWIZARD_CANCEL,
			LANG_CMD_CARTJETS_DELETE_NON_EXISTING,
			LANG_CMD_CARTJETS_DELETE_SUCCESS,
			LANG_CMD_CARTJETS_LIST_MESSAGE,
			LANG_CMD_CARTJETS_UPDATE_OFF,
			LANG_CMD_CARTJETS_UPDATE_ASYNCSTART,
			LANG_CMD_CARTJETS_UPDATE_ERROR,
			LANG_CMD_CARTJETS_UPDATE_UPTODATE,
			LANG_CMD_CARTJETS_UPDATE_AVAILABLE,
			LANG_CMD_CARTJETS_UPDATE_DOWNLOADED
		};
	}

	public static CurrentEntries[] getPermissionEntries() {
		return new CurrentEntries[] {
			PERM_CMD_CARTJETS,
			PERM_CMD_CARTJETS_SETUPWIZARD,
			PERM_CMD_CARTJETS_LIST,
			PERM_CMD_CARTJETS_DELETE,
			PERM_CMD_CARTJETS_UPDATE
		};
	}
}
