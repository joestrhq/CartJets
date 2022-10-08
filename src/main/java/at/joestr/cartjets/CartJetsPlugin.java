//
// Copyright (c) 2020-2022 Joel Strasser <strasser999@gmail.com>
//
// Licensed under the EUPL-1.2 license.
//
// For the full license text consult the 'LICENSE' file from the repository.
//

package at.joestr.cartjets;

import at.joestr.cartjets.commands.CommandCartjets;
import at.joestr.cartjets.commands.CommandCartjetsDelete;
import at.joestr.cartjets.commands.CommandCartjetsList;
import at.joestr.cartjets.commands.CommandCartjetsSetupwizard;
import at.joestr.cartjets.commands.CommandCartjetsUpdate;
import at.joestr.cartjets.configuration.AppConfiguration;
import at.joestr.cartjets.configuration.CurrentEntries;
import at.joestr.cartjets.configuration.LanguageConfiguration;
import at.joestr.cartjets.configuration.Updater;
import at.joestr.cartjets.listeners.ButtonPressedListener;
import at.joestr.cartjets.listeners.MinecartLeaveListener;
import at.joestr.cartjets.listeners.SetupwizardButtonPressedListener;
import at.joestr.cartjets.listeners.SetupwizardRailClickListener;
import at.joestr.cartjets.models.CartJetsModel;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The CartJets plugin instance
 *
 * @author Joel Strasser (joestr)
 */
public class CartJetsPlugin extends JavaPlugin {

  private static CartJetsPlugin instance;

  private Dao<CartJetsModel, String> cartJetsDao;
  private HashMap<UUID, CartJetsModel> perUserModels;
  private HashMap<String, TabExecutor> commandMap;
  private Updater updater;

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

    Metrics metrics = new Metrics(this, 16603);

    this.perUserModels = new HashMap<>();
    this.commandMap = new HashMap<>();

    this.loadAppConfiguration();
    this.loadLanguageConfiguration();

    try {
      this.loadDatabase();
    } catch (SQLException ex) {
      this.getLogger().log(Level.SEVERE, "Error whilst loading database!", ex);
      this.getServer().getPluginManager().disablePlugin(this);
    }

    this.updater =
        new Updater(
            AppConfiguration.getInstance().getBool(CurrentEntries.CONF_UPDATER_ENABLED.toString()),
            AppConfiguration.getInstance()
                .getBool(CurrentEntries.CONF_UPDATER_DOWNLOADTOPLUGINUPDATEFOLDER.toString()),
            this.getDescription().getVersion(),
            AppConfiguration.getInstance()
                .getString(CurrentEntries.CONF_UPDATER_TARGETURL.toString()),
            AppConfiguration.getInstance()
                .getString(CurrentEntries.CONF_UPDATER_POMPROPERTIESFILE.toString()),
            AppConfiguration.getInstance()
                .getString(CurrentEntries.CONF_UPDATER_CLASSIFIER.toString()),
            Bukkit.getUpdateFolderFile());

    this.commandMap.put("cartjets", new CommandCartjets());
    this.commandMap.put("cartjets-delete", new CommandCartjetsDelete());
    this.commandMap.put("cartjets-list", new CommandCartjetsList());
    this.commandMap.put("cartjets-setupwizard", new CommandCartjetsSetupwizard());
    this.commandMap.put("cartjets-update", new CommandCartjetsUpdate());

    this.registerCommands();
    this.registerListeners();
  }

  @Override
  public void onDisable() {
    super.onDisable();

    this.cartJetsDao.getConnectionSource().closeQuietly();
    this.getServer().getScheduler().cancelTasks(this);
  }

  public Dao<CartJetsModel, String> getCartJetsDao() {
    return cartJetsDao;
  }

  public Map<UUID, CartJetsModel> getPerUserModels() {
    return perUserModels;
  }

  /**
   * Gets the {@link Updater updater} for this plugin.
   *
   * @return The {@link Updater updater}
   */
  public Updater getUpdater() {
    return updater;
  }

  /** Registers the commands for this plugin. */
  private void registerCommands() {
    this.commandMap.forEach(
        (s, e) -> {
          PluginCommand pluginCommand = getCommand(s);
          if (pluginCommand == null) {
            return;
          }
          pluginCommand.setExecutor(e);
          pluginCommand.setTabCompleter(e);
        });
  }

  /** Registers the listeners for this plugin. */
  private void registerListeners() {
    this.getServer().getPluginManager().registerEvents(new ButtonPressedListener(), this);
    this.getServer().getPluginManager().registerEvents(new MinecartLeaveListener(), this);
    this.getServer().getPluginManager().registerEvents(new SetupwizardRailClickListener(), this);
    this.getServer()
        .getPluginManager()
        .registerEvents(new SetupwizardButtonPressedListener(), this);
  }

  /** Loads the bundled and external plugin configuration. */
  private void loadAppConfiguration() {
    InputStream bundledConfig = this.getClass().getResourceAsStream("config.yml");
    File externalConfig = new File(this.getDataFolder(), "config.yml");

    try {
      AppConfiguration.getInstance(externalConfig, bundledConfig);
    } catch (IOException ex) {
      this.getLogger().log(Level.SEVERE, "Error whilst intialising the plugin configuration!", ex);
      this.getServer().getPluginManager().disablePlugin(this);
    }
  }

  /** Loads the bundled and external provided languages. */
  private void loadLanguageConfiguration() {
    Map<String, InputStream> bundledLanguages = new HashMap<>();
    bundledLanguages.put("en.yml", this.getClass().getResourceAsStream("languages/en.yml"));
    bundledLanguages.put("de.yml", this.getClass().getResourceAsStream("languages/de.yml"));
    File externalLanguagesFolder = new File(this.getDataFolder(), "languages");

    try {
      LanguageConfiguration.getInstance(
          externalLanguagesFolder, bundledLanguages, new Locale("en"));
    } catch (IOException ex) {
      this.getLogger()
          .log(Level.SEVERE, "Error whilst intialising the language configuration!", ex);
      this.getServer().getPluginManager().disablePlugin(this);
    }
  }

  /**
   * Loads the database.
   *
   * @throws SQLException If an SQL error occurs.
   */
  private void loadDatabase() throws SQLException {
    ConnectionSource connectionSource =
        new JdbcConnectionSource(AppConfiguration.getInstance().getString("jdbcUri"));

    this.cartJetsDao = DaoManager.createDao(connectionSource, CartJetsModel.class);

    TableUtils.createTableIfNotExists(connectionSource, CartJetsModel.class);
  }
}
