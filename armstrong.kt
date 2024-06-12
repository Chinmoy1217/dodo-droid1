fun isArmstrongNumber(number: Int): Boolean {
    var originalNumber = number
    var result = 0
    val power = number.toString().length

    while (originalNumber != 0) {
        val remainder = originalNumber % 10
        result += Math.pow(remainder.toDouble(), power.toDouble()).toInt()
        originalNumber /= 10
    }

    return result == number
}

fun main() {
    val number = 333

    val isArmstrong = isArmstrongNumber(number)

    if (isArmstrong) {
        println("$number is an Armstrong number.")
    } else {
        println("$number is not an Armstrong number.")
    }
}