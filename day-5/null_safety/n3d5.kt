fun filterNonNullIntegers(numbers: List<Int?>): List<Int> {
    return numbers.filterNotNull()
}

fun main() {
    val nullableIntegers = listOf(1, null, 3, null, 5, 6, null, 8)

    // Filter out null integers
    val nonNullIntegers = filterNonNullIntegers(nullableIntegers)

    // Print the result
    println("Original List: $nullableIntegers")
    println("Non-null Integers: $nonNullIntegers")
}
