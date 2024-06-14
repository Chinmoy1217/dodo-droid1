fun main() {
    val numbers = listOf(1, 2, 3, 2, 4, 1, 3, 5, 1, 2, 4)

    val frequencyMap = calculateFrequency(numbers)

    // Print the result
    println("Original List: $numbers")
    println("Frequency Map: $frequencyMap")
}

fun calculateFrequency(numbers: List<Int>): Map<Int, Int> {
    return numbers.groupingBy { it }.eachCount()
}
