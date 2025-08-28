# Jungle Game & JunGUI

This project implements the **Jungle** board game in Java. Two players can compete by moving pieces on a grid-based board. The objective is to either capture the opponent's den or eliminate all of their pieces. The game board has a 9x7 grid, with areas for water, traps, and dens. Animals are created to have unique ranks for different capturing rules and some has unique movement abilities.

JunGUI is a Java GUI application for the board game **Jungle** made mainly using Swing. It uses a Model-Delegate Paradigm, which is a simplification of Model-View-Controller. This implementation is chosen due to the small scale of this project. JunGUI allows two players to play the Jungle game on a virtual board with a user-friendly interface and graphics.

## Table of Contents

- [Jungle Features](#jungle-features)
- [JunGUI Features](#jungui-features)
- [JunGUI Assets](#jungui-assets)
- [Installation](#installation)
- [Running Tests Example Usage](#running-tests-example-usage)
- [Running the Application](#running-the-application)
- [JunGUI Gameplay Instructions](#jungui-gameplay-instructions)

## Jungle Features

- **Functional Gameplay**: Full game can be played just by using public methods from the game.
- **Piece Types and Abilities**: Pieces may have different mechanics based on their types, for example, a rat can swim.
- **Square Types and Effects**: Squares may also have different effects based on their types, for example, a trap can reduce the strength of a piece to 0.
- **Flexible Methods**: Methods are overloaded to allow flexible use for different parameter types, for example, can enter row and col or Coordinates.
- **Legal Move Validation**: Methods exist to ensure the moves that the player commits are valid.
- **OOP Encapsulation on Classes**: Method encapsulation is done when needed, for example, methods regarding Coordinates are put inside its class.

## JunGUI Features

- **Independent to Game**: Calls methods from the game in one direction, model doesn't depend on delegate.
- **Graphical User Interface**: Easy to use chess-like GUI built with Java Swing.
- **Player Name Customisation**: Can choose player name before starting and will be displayed when winning.
- **Winning Condition**: Detects and announces the winner when the game ends.
- **Local Multiplayer Gameplay**: Two people can play on one computer, on different parts of the screen.
- **Highlights Legal Moves**: On selecting piece, GUI shows the legal moves as dots.
- **Board Update**: Board updates on each move.

## JunGUI Assets

- All JunGUI assets are in the resources folder inside src.
- All images are completely original, and drawn from scratch using the app - [Procreate](https://procreate.com/).
- Traditional Chinese characters were used (with english captions for clarity) to suit the cultural background of this game (This game is a nostalgic game in childhood for many Cantonese people as they played it during those times).
- UI was created with the theme of being similar to an old style Chinese chess game in mind, once again, based on the cultural background of this game.

## Installation
Unzip the 'CS5001-p2.zip'.

```bash
unzip CS5001-p2.zip
```

Go to the 'CS5001-p2-jungle/' directory.

```bash
cd CS5001-p2-jungle/
```

## Running Tests Example Usage
Run the program with tests using 'stacscheck' from the command line, either from the original tests or the tests included in this directory:

```bash
stacscheck /cs/studres/CS5001/Coursework/p2-jungle/Tests/
```

```bash
stacscheck /path/to/file/CS5001-p2-jungle/Tests/
```

## Running the Application
Compile and run the application using:

```bash
./run.sh
```

Can also do it manually by first compiling the application:

```bash
javac -d bin -sourcepath src src/**/*.java && cp -r src/resources bin/
```

Then running the Main file:

```bash
java -cp bin jungui.Main
```

## JunGUI Gameplay Instructions
- Enter player names for both players and click start game.
- Move pieces by clicking on the piece, and clicking a legal move indicated by "black dots".
- Move the pieces over other pieces to capture them, and move the pieces to the opponent's den to win.
- Victory message will be displayed at the end and this ends the application.