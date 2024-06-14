
data class Person(val name: String, val age: Int, val address: String)

fun main() {

    val person1 = Person(name = "John Doe", age = 30, address = "123 Main St")

    val person2 = person1.copy(age = 31, address = "456 Elm St")

    println("Original person: $person1")
    println("Copied person with new age and address: $person2")
}
