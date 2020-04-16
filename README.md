# RobinhoodJavaClient

This repository contains a client written in java for connecting to the Robinhood API. [See sanko/Robinhood](https://github.com/sanko/Robinhood) for documentation on the API.

## Build Instructions (tested on MacOS and Arch Linux)

 * Ensure you have a Java SDK installed and configured with java/javac in your $PATH.
 * Clone this repository.
 * Execute ./gradlew jarFile
 * Your jar will be built into the build/libs/ directory.


## How to run
 * Application's main class is App.java
 * Make sure that you've file named `config.properties` at path `src/main/resources` (For security reasons file has 
 been excluded)
 * Add following fields in `config.properties`
    *  username=<your_robinhood_password>
    *  password=<your_robinhood_password>
 * Run main class App.java and you would see portfolio details on console such as
    * Past transactions over 1 month, 6 months and 1 year
    * Profit/loss in option positions (Please note, this doesn't consider your active positions)