fun getStringLength(str: String?): Int {
    return str?.length ?: -1
}

fun main() {
    val str1: String? = "Hello"
    val str2: String? = null

    println("Length of '$str1': ${getStringLength(str1)}")  // Output: Length of 'Hello': 5
    println("Length of '$str2': ${getStringLength(str2)}")  // Output: Length of 'null': -1
}
