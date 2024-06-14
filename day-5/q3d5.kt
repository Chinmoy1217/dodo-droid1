fun main() {
    val strings = listOf("apple", "banana", "avocado", "orange", "pear", "plum", "grape", "pineapple")

    val groupedByFirstChar = strings.groupBy { it.first() }

    val countByFirstChar = groupedByFirstChar.mapValues { it.value.size }

    // Print the result
    println("Original List: $strings")
    println("Grouped by First Character: $groupedByFirstChar")
    println("Count of Strings by First Character: $countByFirstChar")
}
