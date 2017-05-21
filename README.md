# 2048-IA

A simple implementation of the once-popular 2048 puzzle game, as part of a student project, written in JAVA

# GAME
The game is fully functional, type "_" to start a new game, directional arrows to play, "_" again to start a new game.

# IA
Launch and exit the IA at any time using the SPACE bar
The IA is based on a modified expected-max algorithm.

## Score
The score is calculated on the pattern of the tiles. this pattern should "looks like" a snake to maximize the score (max of the snake-score on every direction is the score).

## Modelization
The IA simulates a player who choses between 4 directions. It plays against an enemy who places a 2 or 4 tile anywhere in the board.

## Predicting the future
The algorithm adapted itself to the ongoing difficulty. It creates a hierarchical views of the possibilities of every move of the player, 1 to 13 moves in the future (1 means a lot of possibilities should be calculated so the algo only anticipates one move).

The player can choose one direction between usually 4 (sometimes less). It generates 4 parent grids on which the enemy places a 2 or 4 tile. Hence ~2*[0,15] possibilities. Then mean score for every direction (chosen by the player) is calculated and the player choses the max. This is done recurrently up to 10+ rounds (one round = player+enemy turn)
