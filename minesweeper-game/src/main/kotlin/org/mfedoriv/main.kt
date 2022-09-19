package org.mfedoriv

import org.mfedoriv.game.Minesweeper

fun main() {
    val fieldSize = 9
    var mines = 10
    if (mines > fieldSize * fieldSize) {
        println("Mines number is greater than cells number! Changed mines number to field size ($fieldSize)")
        mines = fieldSize
    }

    val sweeper = Minesweeper(fieldSize, mines)
    sweeper.startGame()
}