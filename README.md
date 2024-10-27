## Game Overview

Angry Birds Dead Hour is an exciting twist on the classic Angry Birds saga. In this version, the Angry Birds have come back as ghosts to seek revenge on the pigs who wronged them. With a haunting new atmosphere, players must control these ghostly birds to overcome obstacles, defeat enemies, and bring peace back to their realm. The pigs are in for the fright of their lives as they attempt to defend themselves from these otherworldly attackers!
## Features

Haunting Gameplay: Control the ghostly Angry Birds in thrilling physics-based levels.

Battle Against Pigs: Use strategic aim and ghostly powers to topple the pigs’ defenses.

Win/Loss Simulation: For testing purposes, you can manually simulate a win or loss by selecting the thumbs up or thumbs down buttons on the Level 1 screen.

## How to Run the Game

To get started with Angry Birds Dead Hour, follow these steps:

Ensure you have LWJGL (Lightweight Java Game Library) 3 installed, as it’s required to run the game.

Navigate to the following file path in your project directory:

lwjgl3/src/main/java/angry.birds.game.lwjgl3/Lwjgl3Launcher

Open Lwjgl3Launcher.java and run the file to start the game.

Note: This will launch the game’s main screen, where you can navigate through levels and gameplay options.

## Gameplay Instructions

Level 1: Once you reach the Level 1 screen, you can begin strategizing your attacks to take down the pigs.

Simulating Wins and Losses:
Thumbs Up: Click the thumbs-up icon located at the bottom right corner of the screen to simulate a win.
Thumbs Down: Click the thumbs-down icon to simulate a loss.

These options are available for testing purposes and allow you to progress through the game or retry levels easily.

## Developer Notes

This game is built using LWJGL 3.
The gameplay simulation options in Level 1 are temporary and will be replaced with full gameplay mechanics in later versions.

## License

Angry Birds Dead Hour is developed as a fan-based project and is not officially associated with the original Angry Birds series by Rovio Entertainment. This project is intended for educational and personal entertainment purposes only.
## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

