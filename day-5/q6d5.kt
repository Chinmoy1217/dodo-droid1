data class Person(val name: String, val age: Int)

fun main() {
    val persons = listOf(
        Person("Alice", 25),
        Person("Bob", 30),
        Person("Charlie", 20),
        Person("David", 25),
        Person("Emma", 20)
    )

    // Sort persons by age ascending, then by name alphabetically
    val sortedPersons = persons.sortedWith(compareBy({ it.age }, { it.name }))

    // Print the sorted list
    println("Sorted List:")
    sortedPersons.forEach { println("${it.name} (${it.age})") }
}
