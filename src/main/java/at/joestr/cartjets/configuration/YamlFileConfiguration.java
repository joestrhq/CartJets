//
// Copyright (c) 2020-2022 Joel Strasser <strasser999@gmail.com>
//
// Licensed under the EUPL-1.2 license.
//
// For the full license text consult the 'LICENSE' file from the repository.
//

package at.joestr.cartjets.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.yaml.snakeyaml.Yaml;

/**
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
    fOS.write(new Yaml().dumpAsMap(this.config).getBytes(StandardCharsets.UTF_8));
  }
}
