//
// MIT License
// 
// Copyright (c) 2020 minecraft.kiwi
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
// 
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
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
	CONF_VERSION("version"),
	CONF_JDBCURI("jdbcUri"),
	CONF_MAXSPEED("maxSpeed"),
	CONF_VECTORMULTIPLIER("vectorMultiplier"),
	CONF_TASKREPEATINGDELAYINTICKS("taskRepeatingDelayInTicks"),
	CONF_JENKINSUPDATER_MODE("jenkinsUpdater.mode"),
	CONF_JENKINSUPDATER_AUTOUPDATE("jenkinsUpdater.autoUpdate"),
	CONF_JENKINSUPDATER_ROOTURL("jenkinsUpdater.rootUrl"),
	CONF_JENKINSUPDATER_POMPROPERTIES("jenkinsUpdater.pomProperties"),
	CONF_JENKINSUPDATER_CLASSIFIER("jenkinsUpdater.classifier"),
	// Languages
	LANG_PREFIX("prefix"),
	LANG_GEN_NOT_A_PLAYER("generic.not_a_player"),
	LANG_CMD_CARTJETS_X_MSG_SETUPWIZARD("commands.cartjets.message_setupwizard"),
	LANG_CMD_CARTJETS_X_MSG_DELETE("commands.cartjets.message_delete"),
	LANG_CMD_CARTJETS_X_MSG_LIST("commands.cartjets.message_list"),
	LANG_CMD_CARTJETS_X_MSG_UPDATE("commands.cartjets.message_update"),
	LANG_CMD_CARTJETS_SETUPWIZARD_BUTTON_INSTRUCTION("commands.cartjets-setupwizard.button.instruction"),
	LANG_CMD_CARTJETS_SETUPWIZARD_BUTTON_SUCCESS("commands.cartjets-setupwizard.button.success"),
	LANG_CMD_CARTJETS_SETUPWIZARD_BUTTON_OVERLAPPING("commands.cartjets-setupwizard.button.overlapping"),
	LANG_CMD_CARTJETS_SETUPWIZARD_RAIL_INSTRUCTION("commands.cartjets-setupwizard.rail.instruction"),
	LANG_CMD_CARTJETS_SETUPWIZARD_RAIL_SUCCESS("commands.cartjets-setupwizard.rail.success"),
	LANG_CMD_CARTJETS_SETUPWIZARD_NAME_ANVIL_GUI_TEXT("commands.cartjets-setupwizard.name.anvilgui_text"),
	LANG_CMD_CARTJETS_SETUPWIZARD_NAME_PLACEHOLDER("commands.cartjets-setupwizard.name.placeholder"),
	LANG_CMD_CARTJETS_SETUPWIZARD_NAME_DUPLICATE("commands.cartjets-setupwizard.name.duplicate"),
	LANG_CMD_CARTJETS_SETUPWIZARD_NAME_SUCCESS("commands.cartjets-setupwizard.name.success"),
	LANG_CMD_CARTJETS_SETUPWIZARD_CANCEL("commands.cartjets-setupwizard.cancel"),
	LANG_CMD_CARTJETS_DELETE_NON_EXISTING("commands.cartjets-delete.non_existing"),
	LANG_CMD_CARTJETS_DELETE_SUCCESS("commands.cartjets-delete.success"),
	LANG_CMD_CARTJETS_LIST_MESSAGE("commands.cartjets-list.message"),
	LANG_CMD_CARTJETS_UPDATE_OFF("commands.cartjets-update.off"),
	LANG_CMD_CARTJETS_UPDATE_ASYNCSTART("commands.cartjets-update.asyncstart"),
	LANG_CMD_CARTJETS_UPDATE_ERROR("commands.cartjets-update.error"),
	LANG_CMD_CARTJETS_UPDATE_UPTODATE("commands.cartjets-update.uptodate"),
	LANG_CMD_CARTJETS_UPDATE_AVAILABLE("commands.cartjets-update.available"),
	LANG_CMD_CARTJETS_UPDATE_DOWNLOADED("command.cartjets-update.downloaded"),
	// Permissions
	PERM_CMD_CARTJETS("cartjets.commands.cartjets"),
	PERM_CMD_CARTJETS_SETUPWIZARD("cartjets.commands.cartjets-setupwizard"),
	PERM_CMD_CARTJETS_LIST("cartjets.commands.cartjets-list"),
	PERM_CMD_CARTJETS_DELETE("cartjets.commands.cartjets-delete"),
	PERM_CMD_CARTJETS_UPDATE("cartjets.commands.cartjets-update");

	private final String text;

	private CurrentEntries(String text) {
		this.text = text;
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

	public static CurrentEntries[] getGlobalEntries() {
		return new CurrentEntries[] {
			CONF_VERSION
		};
	}

	public static CurrentEntries[] getConfigurationEntries() {
		return new CurrentEntries[] {
			CONF_VERSION,
			CONF_JDBCURI,
			CONF_MAXSPEED,
			CONF_VECTORMULTIPLIER,
			CONF_TASKREPEATINGDELAYINTICKS,
			CONF_JENKINSUPDATER_MODE,
			CONF_JENKINSUPDATER_AUTOUPDATE,
			CONF_JENKINSUPDATER_ROOTURL,
			CONF_JENKINSUPDATER_POMPROPERTIES,
			CONF_JENKINSUPDATER_CLASSIFIER
		};
	}

	public static CurrentEntries[] getLanguageEntries() {
		return new CurrentEntries[] {
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
