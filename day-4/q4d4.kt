tailrec fun factorial(n: Long, acc: Long = 1): Long {
    return if (n <= 1) acc else factorial(n - 1, acc * n)
}

fun main() {
    val number = 20L
    println("Factorial of $number is: ${factorial(number)}")
}
