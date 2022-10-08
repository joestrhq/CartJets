# Configuration

## Plugin

The configuration file is located in `plugins/CartJets/config.yml`.  
  
The default configuration file looks like this:  
  
```
version: 2
jdbcUri: 'jdbc:sqlite:./plugins/CartJets/cartjets.db'
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
  
### Settings

`version`: Version of the plugin config file.  
`jdbcUri`: The URI for the database connection.  
`maxSpeed`: The maximum speed a minecart should reach.  
`vectorMultiplier`: The factor which the current velocity get multiplied by.  
`taskRepeatingDelayInTicks`: In which delay a speed multiplication should happen.  
`updater`: The section for the updater.  
  
### Updater

`enabled`: If the updater is active.  
`downloadToPluginUpdateFolder`: Downloads the update to the plugin update folder.  
`targetUrl`: The URL for fetching updates.  
`pomPropertiesFile`: Where the meta-file `pom.properties` is located (on the `targetUrl`).  
`classifier`: A classifier added to the jar.  
  
With the default settings a download URL can look like `https://github.com/joestrhq/CartJets/latest/download/cartjets-1.1.2-shaded.jar`.

## Language

The default language configurations are located in folder `plugins/PostBox/languages/`.  
  
The default language configuration file for English looks like this:  

```
version: 2
prefix: ',{"text":"[","color":"dark_gray"},{"text":"CJ","color":"dark_green"},{"text":"]","color":"dark_gray"},{"text":" "}'
generic:
  not_a_player: '{"text":"This command can only be used by players!","color":"red"}'
commands:
  cartjets:
    message_setupwizard: '[""%prefix,{"text":"» ","color":"gray"},{"text":"Start/cancel
      the setup wizard","color":"gray","clickEvent":{"action":"run_command","value":"/cartjets-setupwizard"},"hoverEvent":{"action":"show_text","value":{"text":"/cartjets-setupwizard","color":"white"}}}]'
    message_list: '[""%prefix,{"text":"» ","color":"gray"},{"text":"List all lines","color":"gray","clickEvent":{"action":"run_command","value":"/cartjets-list"},"hoverEvent":{"action":"show_text","value":{"text":"/cartjets-list","color":"white"}}}]'
    message_delete: '[""%prefix,{"text":"» ","color":"gray"},{"text":"Delete a line","color":"gray","clickEvent":{"action":"suggest_command","value":"/cartjets-delete
      "},"hoverEvent":{"action":"show_text","value":{"text":"/cartjets-delete <Name>","color":"white"}}}]'
    message_update: '[""%prefix,{"text":"» ","color":"gray"},{"text":"Check for an
      update","color":"gray","clickEvent":{"action":"run_command","value":"/cartjets-update"},"hoverEvent":{"action":"show_text","value":{"text":"/cartjets-update","color":"white"}}}]'
  cartjets-setupwizard:
    button:
      instruction: '[""%prefix,{"text":"Right-click the button that should spawn a
        minecart.","color":"aqua"}]'
      success: '[""%prefix,{"text":"The button has been selected!","color":"green"}]'
      overlapping: '[""%prefix,{"text":"This button overlaps with line ","color":"red"},{"text":"%line","color":"white"},{"text":"!","color":"red"}]'
    rail:
      instruction: '[""%prefix,{"text":"Right-click the rail where the minecart should
        spawn.","color":"aqua"}]'
      success: '[""%prefix,{"text":"The rail has been selected!","color":"green"}]'
    rail2:
      instruction: '[""%prefix,{"text":"Right-click the rail to set the direction
        for the minecart.","color":"aqua"}]'
      success: '[""%prefix,{"text":"The rail has been selected!","color":"green"}]'
    name:
      anvilgui_text: Line name
      placeholder: Name of the line ...
      duplicate: '%line already exists'
      success: '[""%prefix,{"text":"The line ","color":"green"},{"text":"%line","color":"white"},{"text":"
        has been created!","color":"green"}]'
    cancel: '[""%prefix,{"text":"Creation canceled!","color":"red"}]'
  cartjets-list:
    message: '[""%prefix,{"text":"Following lines are available: ","color":"aqua"},{"text":"%lines","color":"white"}]'
  cartjets-delete:
    non_existing: '[""%prefix,{"text":"The line ","color":"red"},{"text":"%line","color":"white"},{"text":"
      does not exist.","color":"red"}]'
    success: '[""%prefix,{"text":"The line ","color":"green"},{"text":"%line","color":"white"},{"text":"
      has been removed.","color":"green"}]'
  cartjets-update:
    false: '[""%prefix,{"text":"Updates are turned off!","color":"green"}]'
    asyncstart: '[""%prefix,{"text":"Checking for updates ...","color":"aqua"}]'
    error: '[""%prefix,{"text":"An error occoured during download!","color":"red"}]'
    uptodate: '[""%prefix,{"text":"You are already using the latest version.","color":"green"}]'
    available: '[""%prefix,{"text":"A new version is available ","color":"green"},{"text":"here","underlined":true,"color":"gray","clickEvent":{"action":"open_url","value":"%update$downloadUrl"},"hoverEvent":{"action":"show_text","value":{"text":"%update$downloadUrl","color":"white"}}},{"text":".","color":"green"}]'
    downloaded: '[""%prefix,{"text":"The new version is in the update-folder!","color":"green"}]'
```