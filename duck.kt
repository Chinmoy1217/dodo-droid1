fun isDuckNumber(number: String): Boolean {
    if (number.startsWith("0")) {
        return false
    }
    return number.contains("0")
}

fun main() {
    val number1 = "3210"
    val number2 = "01203"
    val number3 = "7056"

    println("$number1 is Duck number: ${isDuckNumber(number1)}")
    println("$number2 is Duck number: ${isDuckNumber(number2)}")
    println("$number3 is Duck number: ${isDuckNumber(number3)}")

}
