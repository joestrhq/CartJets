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
  LANG_CMD_CARTJETS_MSG_SETUPWIZARD("commands.cartjets.message_setupwizard"),
  LANG_CMD_CARTJETS_MSG_DELETE("commands.cartjets.message_delete"),
  LANG_CMD_CARTJETS_MSG_LIST("commands.cartjets.message_delete"),
  LANG_CMD_CARTJETS_SETUPWIZARD_BUTTON_INSTRUCTION("commands.cartjets-setupwizard.button.instruction"),
  LANG_CMD_CARTJETS_SETUPWIZARD_BUTTON_SUCCESS("commands.cartjets-setupwizard.button.success"),
  LANG_CMD_CARTJETS_SETUPWIZARD_BUTTON_OVERLAPPING("commands.cartjets-setupwizard.button.overlapping"),
  LANG_CMD_CARTJETS_SETUPWIZARD_RAIL_INSTRUCTION("commands.cartjets-setupwizard.rail.instruction"),
  LANG_CMD_CARTJETS_SETUPWIZARD_RAIL_SUCCESS("commands.cartjets-setupwizard.success"),
  LANG_CMD_CARTJETS_SETUPWIZARD_NAME_PLACEHOLDER("commands.cartjets-setupwizard.name.placeholder"),
  LANG_CMD_CARTJETS_SETUPWIZARD_NAME_SUCCESS("commands.cartjets-setupwizard.name.success"),
  LANG_CMD_CARTJETS_DELETE_NON_EXISTING("commands.cartjets-delete.non_existing"),
  LANG_CMD_CARTJETS_DELETE_SUCCESS("commands.cartjets-delete.success"),
  LANG_CMD_CARTJETS_LIST_MESSAGE("commands.cartjets-list.message"),
  PERM_CMD_CARTJETS("cartjets.commands.cartjets"),
  PERM_CMD_CARTJETS_SETUPWIZARD("cartjets.commands.cartjets-setupwizard"),
  PERM_CMD_CARTJETS_LIST("cartjets.commands.cartjets");
  
  private final String text;

  private CurrentEntries(String text) {
    this.text = text;
  }
  
  @Override  
  public String toString() {
    return this.text;
  }
}
