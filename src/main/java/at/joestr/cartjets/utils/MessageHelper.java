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
package at.joestr.cartjets.utils;

import at.joestr.cartjets.configuration.CurrentEntries;
import at.joestr.cartjets.configuration.LanguageConfiguration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Joel
 */
public class MessageHelper {
  
  private CurrentEntries path;
  private Function<String, String> modify;
  private Locale locale;
  private CommandSender receiver;
  private boolean showPrefix;
	private List<Function<String, String>> modifiers;
	
	public MessageHelper() {
		this.modifiers = new ArrayList<>();
	}
  
  public MessageHelper path(CurrentEntries path) {
    this.path = path;
    return this;
  }
  
  public MessageHelper locale(Locale locale) {
    this.locale = locale;
    return this;
  }
  
  public MessageHelper modify(Function<String, String> modify) {
    this.modify = modify;
    return this;
  }
	
	public MessageHelper addModifier(Function<String, String> modifier) {
		this.modifiers.add(modifier);
		return this;
	}
  
  public MessageHelper receiver(CommandSender receiver) {
    this.receiver = receiver;
    return this;
  }
  
  public MessageHelper prefix(boolean show) {
    this.showPrefix = show;
    return this;
  }
  
  public void send() {
    String message =
      LanguageConfiguration.getInstance()
        .getString(
          this.path.toString(),
          this.locale
        );
    
    if (showPrefix) {
      message = message.replace("%prefix",
        new MessageHelper()
          .locale(this.locale)
          .path(CurrentEntries.LANG_PREFIX)
          .string()
      );
    } else {
      message = message.replace("%prefix", "");
    }
    
    if (this.modify != null) message = this.modify.apply(message);
		
		for (Function<String, String> modifier: this.modifiers) {
			modifier.apply(message);
		}
    
    receiver.spigot().sendMessage(
      ComponentSerializer.parse(message)
    );
  }
  
  public String string() {
    return LanguageConfiguration.getInstance()
        .getString(
          this.path.toString(),
          this.locale
        );
  }
}
