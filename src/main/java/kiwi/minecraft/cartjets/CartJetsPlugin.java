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
package kiwi.minecraft.cartjets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.logging.Level;
import kiwi.minecraft.cartjets.configuration.LanguageConfiguration;
import kiwi.minecraft.cartjets.configuration.AppConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Joel
 */
public class CartJetsPlugin extends JavaPlugin {
  
  private static CartJetsPlugin instance;
  
  
  @Override
  public void onLoad() {
    super.onLoad();
    instance = this;
  }
  
  @Override
  public void onEnable() {
    super.onEnable();
    InputStream bundledConfig = this.getClass().getResourceAsStream("config.yml");
    File externalConfig = new File(this.getDataFolder(), "config.yml");
    File bundledLanguages = null;
    try {
      bundledLanguages = new File(
        this.getClass().getResource("languages/").toURI()
      );
    } catch (URISyntaxException ex) {
      instance.getLogger().log(
        Level.SEVERE,
        "Error in URI syntax for bundled languages!",
        ex
      );
      this.getServer().getPluginManager().disablePlugin(this);
    }
    File externalLanguages = new File(this.getDataFolder(), "languages");
    
    try {
      AppConfiguration.getInstance(externalConfig, bundledConfig);
    } catch (IOException ex) {
      instance.getLogger().log(
        Level.SEVERE,
        "Error whilst intialising the plugin configuration!",
        ex
      );
      this.getServer().getPluginManager().disablePlugin(this);
    }
    
    try {
      LanguageConfiguration.getInstance(externalLanguages, bundledLanguages, Locale.ENGLISH);
    } catch (FileNotFoundException ex) {
      instance.getLogger().log(
        Level.SEVERE,
        "Error whilst intialising the language configuration!",
        ex
      );
      this.getServer().getPluginManager().disablePlugin(this);
    }
  }

  @Override
  public void onDisable() {
    super.onDisable();
  }

  public static CartJetsPlugin getInstance() {
    return instance;
  }
}
