# CartJets
![Build Status](https://shields.io/endpoint?url=https://files.joestr.at/ci-build-status/cctray.php?project_name=PIP.github.joestrhq.CartJets_main)
[![Maintainability](https://api.codeclimate.com/v1/badges/e50fc6d42cf44bf736ba/maintainability)](https://codeclimate.com/github/joestrhq/CartJets/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/e50fc6d42cf44bf736ba/test_coverage)](https://codeclimate.com/github/joestrhq/CartJets/test_coverage)
[![License](https://img.shields.io/static/v1?label=License&message=EUPL-1.2&color=blue)](https://github.com/joestrhq/CartJets/blob/master/LICENSE)
[![Matrix](https://img.shields.io/matrix/joestrhq.general:matrix.org?color=0dbd8b&logo=matrix)](https://matrix.to/#/#joestrhq.general:matrix.org)

## Installtion

Head to over the [latest release](https://github.com/joestrhq/CartJets/releases/tag/v2.1.1).

Download the `jar` file (currently `cartjets-2.1.1-shaded.jar`).

Drop the downloaded `jar` file into your Spigot `plugins` folder.

Stop your Spigot server and start it again.

## About
With CartJets you can define entry points where custom minecarts spawn which can travel faster than normal ones to establish a custom rail network on your server.

## Build
To build the project you need at least a Java Development Kit (JDK) in version 11 and Maven 3 installed.  

At first get a copy of the source code. Preferrably via `git clone https://github.com/joestrhq/CartJets.git`.  

Initiate a build with `mvn -Djarsigner.skip=true clean package`.  

The compiled plugin (`cartjets-X.X.X-shaded.jar`) will be available in the `target` folder.
