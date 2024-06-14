fun main() {
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

    val result = numbers.map {
        if (it % 2 == 0) {
            it * 2   // double even numbers
        } else {
            null     // filter out odd numbers
        }
    }.filterNotNull()

    // Print the result
    println("Original List: $numbers")
    println("Filtered and Modified List: $result")
}
