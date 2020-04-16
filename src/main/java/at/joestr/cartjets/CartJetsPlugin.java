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
package at.joestr.cartjets;

import at.joestr.cartjets.configuration.AppConfiguration;
import at.joestr.cartjets.configuration.LanguageConfiguration;
import at.joestr.cartjets.models.CartJetsModel;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;
import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Joel
 */
public class CartJetsPlugin extends JavaPlugin {
  
  private static CartJetsPlugin instance;
  
  private Dao<CartJetsModel, String> cartJetsDao;
  private HashMap<UUID, CartJetsModel> perUserModels;
  
  public static CartJetsPlugin getInstance() {
    return instance;
  }
  
  @Override
  public void onLoad() {
    super.onLoad();
    instance = this;
  }
  
  @Override
  public void onEnable() {
    super.onEnable();
    
    this.loadAppConfiguration();
    this.loadLanguageConfiguration();
    this.loadDatabase();
    this.perUserModels = new HashMap<>();
  }

  @Override
  public void onDisable() {
    super.onDisable();
  }

  public Dao<CartJetsModel, String> getCartJetsDao() {
    return cartJetsDao;
  }
  
  public HashMap<UUID, CartJetsModel> getPerUserModels() {
    return perUserModels;
  }
  
  private void loadAppConfiguration() {
    InputStream bundledConfig = this.getClass().getResourceAsStream("config.yml");
    File externalConfig = new File(this.getDataFolder(), "config.yml");
    
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
  }
  
  private void loadLanguageConfiguration() {
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
      LanguageConfiguration.getInstance(externalLanguages, bundledLanguages, Locale.ENGLISH);
    } catch (IOException ex) {
      instance.getLogger().log(
        Level.SEVERE,
        "Error whilst intialising the language configuration!",
        ex
      );
      this.getServer().getPluginManager().disablePlugin(this);
    }
  }
  
  private void loadDatabase() {
    ConnectionSource connectionSource = null;
    try {
      connectionSource = new JdbcConnectionSource(
        AppConfiguration.getInstance().getString("jdbcUri")
      );
    } catch (SQLException ex) {
      instance.getLogger().log(
        Level.SEVERE,
        "Error whilst connecting to database!",
        ex
      );
      this.getServer().getPluginManager().disablePlugin(this);
    }
    
    try {
      cartJetsDao = DaoManager.createDao(connectionSource, CartJetsModel.class);
    } catch (SQLException ex) {
      instance.getLogger().log(
        Level.SEVERE,
        "Error whilst creating Dao!",
        ex
      );
      this.getServer().getPluginManager().disablePlugin(this);
    }
  }
}
