package ch2_KotlinBasics

fun main(args: Array<String>) {
    println("Hello World!")

    val name = if(args.isNotEmpty()) args[0] else "Kotlin"
    println("Hello $name!")

    println("Hello, ${if (args.isNotEmpty()) args[0] else "someone"}!")

    //////////////////

    val person = Person("Bob", true)
    println(person.name)
    println(person.isMarried)

    /////////////////

    val rectangle = Rectangle(41, 43)
    println(rectangle.isSquare)
    println(createRandomRectangle().isSquare)

    /////////////

    println(Color.BLUE.rgb())
    println(Color.RED.rgb())
    println(Color.YELLOW.rgb())

    println(getMnemonic(Color.INDIGO))

    println(getWarmth(Color.VIOLET))
    println(getWarmth(Color.GREEN))
    println(getWarmth(Color.RED))

    println(mix(Color.YELLOW, Color.RED))
    println(mixOptimized(Color.YELLOW, Color.YELLOW))
    println(mixOptimized(Color.YELLOW, Color.BLUE))

    repeat(50) { print("-") }
    println()
    println("Automatic cast with when")

    println(eval(Sum(Sum(Num(1), Num(2)), Num(4))))
    println(evalKotlin(Sum(Sum(Num(1), Num(2)), Num(4))))
    println(evalKotlinWithLogging(Sum(Sum(Num(1), Num(2)), Num(4))))

    repeat(50) { print("-") }
    println()
    println("Loops")

    // while как в Java

    // For
    for (i in 1..30) {
        print(fizzBuzz(i))
    }

    println()
    for (i in 30 downTo 1 step 2) {
        print(fizzBuzz(i))
    }

    println()
    for (i in 0 until 30) { // [0, 30)
        print("$i ")
    }

}

fun max(a: Int, b: Int): Int {
    return if (a > b) a else b
}

fun max2(a: Int, b: Int): Int = if (a < b) a else b

fun max3(a: Int, b: Int) = if (a > b) a else b

