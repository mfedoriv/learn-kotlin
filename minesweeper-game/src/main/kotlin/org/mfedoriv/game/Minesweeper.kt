package org.mfedoriv.game

import java.io.IOException
import java.util.*
import kotlin.random.Random

enum class Marker(val display: Char) {
    MINE('●'),
    FLAG('⚑'),
    CLOSED('·'),
    SAFE('□')
}

// values to color output text
const val RED_COLOR = "\u001b[31m" // Everything after this is in red
const val RESET_COLOR = "\u001b[0m" // Resets previous color codes

class Minesweeper(private val fieldSize: Int, private val minesNumber: Int) {

    private val field = MutableList(fieldSize) {
        MutableList(fieldSize) { Cell() }
    }

    private var isRunning = false

    init {
        generateMines()
        countMinesAroundAllCells()
    }

    private fun generateMines() {
        var minesLeft = minesNumber
        while (minesLeft > 0) {
            val row = Random.nextInt(0, fieldSize)
            val column = Random.nextInt(0, fieldSize)
            if (!field[row][column].isMine) {
                field[row][column].isMine = true
                minesLeft--
            }
        }
    }

    private fun getBoundariesAround(row: Int, column: Int): Pair<IntRange, IntRange> {
        // Count boundaries around the cell where can be mines
        /* X[row][column]
        * [ ][ ][ ]
        * [ ][X][ ]
        * [ ][ ][ ]
        * */
        var minColumn = column - 1
        var maxColumn = column + 1
        var minRow = row - 1
        var maxRow = row + 1
        // Correct boundaries if the cell is on the border of the playing field
        /* X - Cells whose check boundaries will be adjusted
        * [X][X][X][X]
        * [X][ ][ ][X]
        * [X][ ][ ][X]
        * [X][X][X][X]
        * */
        if (row == 0) {
            minRow += 1
        }
        if (row == field.size - 1) {
            maxRow -= 1
        }
        if (column == 0) {
            minColumn += 1
        }
        if (column == field[0].size - 1) {
            maxColumn -= 1
        }

        return Pair(minRow..maxRow, minColumn..maxColumn)
    }

    private fun countMinesAroundAllCells() {
        for (row in field.indices) {
            for (column in field[0].indices) {
                if (field[row][column].isMine) {
                    field[row][column].minesAround = -1
                    continue
                }
                val minesAround = countMinesAround(row, column)
                if (minesAround > 0) {
                    field[row][column].minesAround = minesAround
                }
            }
        }
    }

    private fun countMinesAround(row: Int, column: Int): Int {
        val (rowRange, columnRange) = getBoundariesAround(row, column)
        // Count mines around the cell by boundaries calculated before
        var minesAround = 0
        for (i in rowRange) {
            for (j in columnRange) {
                if (field[i][j].isMine) {
                    minesAround++
                }
            }
        }
        return minesAround
    }

    private fun revealCell(row: Int, column: Int) {
        if (row !in 0 until fieldSize || column !in 0 until fieldSize) {
            throw IndexOutOfBoundsException("Indices are out of the boundaries of the field!")
        }

        if (field[row][column].isMine && !field[row][column].isRevealed) {
            gameOver()
            return
        }

        field[row][column].isRevealed = true
        if (field[row][column].minesAround == 0) {
            floodFill(row, column)
        }
    }

    private fun floodFill(row: Int, column: Int) {
        val (rowRange, columnRange) = getBoundariesAround(row, column)

        for (i in rowRange) {
            for (j in columnRange) {
                if (!field[i][j].isMine && !field[i][j].isRevealed) {
                    revealCell(i, j)
                }
            }
        }
    }

    private fun flagCell(row: Int, column: Int): Int {
        if (row !in 0 until fieldSize || column !in 0 until fieldSize) {
            throw IndexOutOfBoundsException("Indices are out of the boundaries of the field!")
        }

        if (field[row][column].isFlagged && field[row][column].isRevealed) { // take off the flag if already placed
            field[row][column].isFlagged = !field[row][column].isFlagged
            field[row][column].isRevealed = false
            return -1
        } else {
            if (field[row][column].isRevealed) {
                throw IOException("You can\'t place flags on revealed cells!")
            } else {
                field[row][column].isFlagged = true
                field[row][column].isRevealed = true
                return 1
            }
        }
    }

    fun startGame() {
        isRunning = true
        var minesLeft = minesNumber

        println("Minesweeper game is running!")
        println("HOW TO PLAY")
        println("""Enter the ROW and COLUMN of the cell you want to Reveal (R) or Flag (F).
            |For example:
            |1. To reveal a cell on row 3 and column 5, enter the following (without brackets): [3 5 R]
            |2. To flag a cell on row 5 and column 2, enter the following (without brackets): [5 2 F]
        """.trimMargin())
        println("Enjoy!")

        while (isRunning) {
            println("—".repeat(50))
            println("Mines left: $minesLeft")
            printField()
            println("Enter the ROW and COLUMN of the cell you want to Reveal (R) or Flag (F): ")
            try {
                val scanner = Scanner(System.`in`)
                val row = scanner.nextInt() - 1 // user input numbers from 1, but real indices start from 0
                val column = scanner.nextInt() - 1
                val action = scanner.next().single().uppercaseChar()

                when (action) {
                    'F' -> {
                        minesLeft -= flagCell(row, column)
                    }
                    'R' -> {
                        revealCell(row, column)
                    }
                    else -> {
                        throw IOException("Incorrect input!")
                    }
                }

            } catch (ex: Exception) {
                println(RED_COLOR + ex.message + "Try again!" + RESET_COLOR)
            }

            if (isWinning()) {
                println("Congratulations! You won!")
                isRunning = false
            }
        }
    }

    // The game is considered won if all mines are neutralized, i.e., each mine is marked with a flag
    private fun isWinning(): Boolean {
        var defusedMines = 0
        for (row in field) {
            for (cell in row) {
                if (cell.isMine && cell.isFlagged) { defusedMines++ }
            }
        }
        return defusedMines == minesNumber
    }

    private fun gameOver() {
        println(RED_COLOR + "BOOM! Game Over!" + RESET_COLOR)
        isRunning = false

        for (row in field) {
            for (cell in row) {
                cell.isRevealed = true
                cell.isFlagged = false
            }
        }

        printField(true)
    }

    private fun printField(printMines: Boolean = false) {
        println("  |${(1..fieldSize).toList().joinToString(" ")} |")
        println("——|" + "—".repeat(fieldSize * 2) + "|")
        for ((rowIndex, row) in field.withIndex()) {
            print("${rowIndex + 1} |")
            for (cell in row) {
                if (cell.isRevealed) {
                    if (cell.isFlagged) {
                        print("${Marker.FLAG.display} ")
                    } else if (cell.minesAround > 0) {
                        print("${cell.minesAround} ")
                    } else if (cell.isMine && printMines) {
                        print("${Marker.MINE.display} ")
                    } else if(cell.isMine) {
                        print("${Marker.CLOSED.display} ")
                    } else if (cell.minesAround == 0) {
                        print("${Marker.SAFE.display} ")
                    }
                } else {
                    print("${Marker.CLOSED.display} ")
                }
            }
            println("|")
        }

        println("——|" + "—".repeat(fieldSize * 2) + "|")
    }
}