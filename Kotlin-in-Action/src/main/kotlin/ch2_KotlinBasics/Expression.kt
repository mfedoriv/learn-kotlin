package ch2_KotlinBasics

interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr

// Java-style
fun eval(e: Expr): Int {
    if (e is Num) {
        val n = e as Num // явное приведение излишне
        return n.value
    }
    if (e is Sum) {
        return eval(e.right) + eval(e.left) // e уже приведена к нужному типу
    }
    throw IllegalArgumentException("Unknown expression")
}

// Kotlin-style

fun evalKotlin(e: Expr): Int =
    if (e is Num) {
        e.value
    } else if (e is Sum) {
        eval(e.right) + eval(e.left)
    } else {
        throw IllegalArgumentException("Unknown expression")
    }

fun evalKotlin2(e: Expr): Int =
    when (e) {
        is Num ->
            e.value
        is Sum ->
            eval(e.right) + eval(e.left)
        else ->
            throw IllegalArgumentException("Unknown expression")
    }

fun evalKotlinWithLogging(e: Expr): Int =
    when (e) {
        is Num -> {
            println("num:${e.value}")
            e.value // последнее выражение является результатом
        }
        is Sum -> {
            val left = evalKotlinWithLogging(e.left)
            val right = evalKotlinWithLogging(e.right)
            println("sum: $left + $right")
            left + right
        }
        else -> throw IllegalArgumentException("Unknown expression")
    }