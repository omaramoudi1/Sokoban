# Sokoban Game (Java)

A simple Sokoban game written in Java with both a Graphical and a Terminal version. Each level includes a timer and the difficulty increases as you progress.

## Run
Graphical version:
  java -jar sokobanAmoudi.jar
Terminal version:
  java -jar sokobanTexteAmoudi.jar

## Features
- Graphical and terminal modes
- Timer per level
- Increasing difficulty
- Level reset and next-level system
- Simple level files (maps)

## Level Format
Example level:
#####
#@$.#
#####
Legend: # = wall, . = goal, $ = box, @ = player, (space) = floor

## Project Layout

```
src-sokobanAmoudi/
├── src/                   # Java source code
├── bin/                   # compiled class files
├── map1.txt, map2.txt…    # level files
├── sokobanAmoudi.jar      # GUI version
├── sokobanTexteAmoudi.jar # terminal version
├── .gitignore
└── README.md
```


## Author
Created by Omar Amoudi (2025) 
## License
Released under the MIT License



