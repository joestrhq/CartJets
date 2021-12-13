# Configuration
The configuration file is located in `plugins/CartJEts/config.yml`.  
  
The default configuration file looks like this:  
  
```
version: '1.1.1'
jdbcUri: 'jdbc:h2:./plugins/CartJets/cartjets'
maxSpeed: 1.0
vectorMultiplier: 0.75
taskRepeatingDelayInTicks: 20
# Updater config
updater:
  enabled: true
  downloadToPluginUpdateFolder: true
  targetUrl: 'https://github.com/joestrhq/CartJets/releases/latest/download/'
  pomPropertiesFile: 'pom.properties'
  classifier: 'shaded'
```
  
## Settings
`version`: Version of the plugin.  
`jdbcUri`: The URI for the database connection.  
`maxSpeed`: The maximum speed a minecart should reach.  
`vectorMultiplier`: The factor which the current velocity get multiplied by.  
`taskRepeatingDelayInTicks`: In which delay a speed multiplication should happen.  
`updater`: The section for the updater.  
  
## Updater
`enabled`: If the updater is active.  
`downloadToPluginUpdateFolder`: Downloads the update to the plugin update folder.  
`targetUrl`: The URL for fetching updates.  
`pomPropertiesFile`: Where the meta-file `pom.properties` is located (on the `targetUrl`).  
`classifier`: A classifier added to the jar.  
  
With the default settings a download URL can look like `https://github.com/joestrhq/CartJets/latest/download/cartjets-1.1.1-shaded.jar`.