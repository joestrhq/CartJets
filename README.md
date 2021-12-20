# CartJets
![Build Status](https://shields.io/endpoint?url=https://files.joestr.at/ci-build-status/cctray.php?project_name=PIP.github.joestrhq.CartJets_main.pr)
[![Maintainability](https://api.codeclimate.com/v1/badges/e50fc6d42cf44bf736ba/maintainability)](https://codeclimate.com/github/joestrhq/CartJets/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/e50fc6d42cf44bf736ba/test_coverage)](https://codeclimate.com/github/joestrhq/CartJets/test_coverage)
[![License](https://img.shields.io/static/v1?label=License&message=EUPL-1.2&color=blue)](https://github.com/joestrhq/CartJets/blob/master/LICENSE)

## About
With CartJets you can define entry points where custom minecarts spawn which can travel faster than normal ones to establish a custom rail network on your server.

## Build
To build the project you need at least Java Development Kit (JDK) in version 11 and Maven 3 installed.  

At first get a copy of the source code. Preferrably via `git clone https://github.com/joestrhq/CartJets.git`.  

Initiate a build with `mvn -Djarsigner.skip=true clean package`.  

The compiled plugin (`cartjets-X.X.X-shaded.jar`) will be available in the `target` folder.
