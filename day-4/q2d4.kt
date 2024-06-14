fun operation(a: Int, b: Int, func: (Int, Int) -> Int): Int {
    return func(a, b)
}

fun main() {
    println("Sum: ${operation(6, 2) { x, y -> x + y }}")
    println("Difference: ${operation(6, 2) { x, y -> x - y }}")
    println("Product: ${operation(6, 2) { x, y -> x * y }}")
    println("Quotient: ${operation(6, 2) { x, y -> x / y }}")
}

