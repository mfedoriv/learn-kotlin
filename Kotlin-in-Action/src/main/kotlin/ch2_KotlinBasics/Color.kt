package ch2_KotlinBasics

import ch2_KotlinBasics.Color.*
import java.lang.Exception

enum class Color(
    val r: Int, val g: Int, val b: Int
) {
    RED(255, 0, 0), ORANGE(255, 165, 0),
    YELLOW(255, 255, 0), GREEN(0, 255, 0), BLUE(0, 0, 255),
    INDIGO(75, 0, 130), VIOLET(238, 130, 238);

    fun rgb() = (r * 256 + g) * 256 + b // что делает эта функция???
}

fun getMnemonic(color: Color) =
    when(color) {
        RED -> "Каждый"
        Color.ORANGE -> "Охотник"
        Color.YELLOW -> "Желает"
        Color.GREEN -> "Знать"
        Color.BLUE -> "Где"
        Color.INDIGO -> "Сидит"
        Color.VIOLET -> "Фазан"
    }

fun getWarmth(color: Color) = when(color) {
    Color.RED, Color.ORANGE, Color.YELLOW -> "теплый"
    Color.GREEN -> "нейтральный"
    Color.BLUE, Color.INDIGO, Color.VIOLET -> "холодный"
}

fun mix(c1: Color, c2: Color) =
    when (setOf<Color>(c1, c2)) {
        setOf(RED, YELLOW) -> ORANGE
        setOf(YELLOW, BLUE) -> GREEN
        setOf(BLUE, VIOLET) -> INDIGO
        else -> throw Exception("Грязный цвет")
    }

fun mixOptimized(c1: Color, c2: Color) =
    when {
        c1 == c2 -> c1
        (c1 == RED && c2 == YELLOW) || (c1 == YELLOW && c2 == RED) -> ORANGE
        (c1 == YELLOW && c2 == BLUE) || (c1 == BLUE && c2 == YELLOW) -> GREEN
        (c1 == BLUE && c2 == VIOLET) || (c1 == VIOLET && c2 == BLUE) -> INDIGO
        else -> throw Exception("Dirty color")
    }