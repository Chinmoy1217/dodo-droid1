fun countOccurrences(str: String, ch: Char): Int {
    var count = 0

    for (element in str) {
        if (element == ch) {
            count++
        }
    }

    return count
}

fun main() {
    val inputString = "Hello, World!"
    val character = 'o'

    val occurrenceCount = countOccurrences(inputString, character)

    val character1 = 'l'

    val occurrenceCount1 = countOccurrences(inputString, character1)
    
    println("The character '$character' occurs $occurrenceCount times in the string.")
        println("The character '$character1' occurs $occurrenceCount1 times in the string.")
}