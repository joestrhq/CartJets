/*
Private License

Copyright (c) 2019 Joel Strasser

Only the owner is allowed to use this software.
 */
package kiwi.minecraft.cartjets.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Joel
 */
public class YamlFileConfiguration extends YamlConfiguration {
  
  private File configFile;
  
  public YamlFileConfiguration(File configFile) throws FileNotFoundException {
    super(new Yaml().load(new FileInputStream(configFile)));
    this.configFile = configFile;
  }

  public File getConfigFile() {
    return configFile;
  }

  public void setConfigFile(File configFile) {
    this.configFile = configFile;
  }
  
  public void loadConfigFile() throws FileNotFoundException {
    this.config = new Yaml().load(new FileInputStream(this.configFile));
  }
  
  public void saveConfigFile() throws FileNotFoundException, IOException {
    FileOutputStream fOS = new FileOutputStream(this.configFile);
    fOS.write(
      new Yaml().dumpAsMap(this.config).getBytes(StandardCharsets.UTF_8)
    );
  }
}
