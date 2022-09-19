package org.mfedoriv.game

data class Cell(
    var isMine: Boolean = false,
    var isRevealed: Boolean = false,
    var isFlagged: Boolean = false,
    var minesAround: Int = 0
)