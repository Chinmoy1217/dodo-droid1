fun main() {
    val listOfLists = listOf(
        listOf(1, 2, 3),
        listOf(2, 3, 4, 5),
        listOf(3, 4, 5, 6),
        listOf(4, 5, 6, 7)
    )
    val flattenedList = listOfLists.flatten().toSet()

    // Print the result
    println("Original List of Lists: $listOfLists")
    println("Flattened List with Duplicates Removed: $flattenedList")
}
