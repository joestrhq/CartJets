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
package kiwi.minecraft.cartjets.utils;

/**
 *
 * @author Joel
 */
public enum CurrentEntries {
  CONFIG_JDBCURI("jdbcUri"),
  LANG_PREFIX("prefix"),
  LANG_NO_PERM("generic.no_permission"),
  LANG_WRONG_SYNTAX("generic.wrong_syntax"),
  LANG_CMD_CARTJETS_SETUPWIZARD_LINE("commands.cartjets.setup-wizard_line"),
  LANG_CMD_CARTJETS_DELETE_LINE("commands.cartjets.delete_line"),
  LANG_CMD_CARTJETS_LIST_LINE("commands.cartjets.list_line"),
  LANG_CMD_CARTJETS_SETUPWIZARD_MARK_BUTTON("commands.cartjets_setup-wizard.mark_button_message"),
  LANG_CMD_CARTJETS_SETUPWIZARD_MARK_BUTTON_SUCCESS("commands.cartjets_setup-wizard.mark_button_success"),
  LANG_CMD_CARTJETS_SETUPWIZARD_MARK_BUTTON_OVERLAPPING("commands.cartjets_setup-wizard.mark_button_overlapping"),
  LANG_CMD_CARTJETS_SETUPWIZARD_MARK_RAIL("commands.cartjets_setup-wizard.mark_rail"),
  LANG_CMD_CARTJETS_SETUPWIZARD_MARK_RAIL_SUCCESS("commands.cartjets_setup-wizard.mark_rail_success"),
  LANG_CMD_CARTJETS_SETUPWIZARD_NAME_ANVIL_PLACEHOLDER("commands.cartjets_setup-wizard.name_anvil_placeholder"),
  LANG_CMD_CARTJETS_SETUPWIZARD_NAME_SUCCESS("commands.cartjets_setup-wizard.name_success"),
  LANG_CMD_CARTJETS_DELETE_NON_EXISTING("commands.cartjets_delete.non_existing"),
  LANG_CMD_CARTJETS_DELETE_SUCCESS("commands.cartjets_delete.success"),
  LANG_CMD_CARTJETS_LIST_LIST("commands.cartjets_list.list");
  
  private final String text;

  private CurrentEntries(String text) {
    this.text = text;
  }
  
  @Override  
  public String toString() {
    return this.text;
  }
}
