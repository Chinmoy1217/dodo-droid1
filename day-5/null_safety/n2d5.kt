fun filterStrings(list: List<Any>): List<String> {
    val stringList = mutableListOf<String>()

    for (item in list) {
        if (item is String) {
            stringList.add(item)
        }
    }

    return stringList
}

fun main() {
    val mixedList: List<Any> = listOf("apple", 1, "banana", 2, "cherry")

    val stringList = filterStrings(mixedList)

    // Print the result
    println("Original List: $mixedList")
    println("Filtered List of Strings: $stringList")
}
