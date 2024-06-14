fun String.isPalindrome(): Boolean {
    val cleanedString = this.filter { it.isLetterOrDigit() }.toLowerCase()
    return cleanedString == cleanedString.reversed()
}

fun main() {
    val str1 = "A man, a plan, a canal, Panama"
    val str2 = "Hello, World!"
    val str3 = "madam"

    println("\"$str1\" is palindrome: ${str1.isPalindrome()}")
    println("\"$str2\" is palindrome: ${str2.isPalindrome()}")
    println("\"$str3\" is palindrome: ${str3.isPalindrome()}")
}
