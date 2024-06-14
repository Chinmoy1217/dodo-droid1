fun calculateArea(length: Double, breadth: Double): Double {
    return length * breadth
}

fun calculateArea(radius: Double): Double {
    return Math.PI * radius * radius
}

fun main() {
    val length = 5.0
    val breadth = 10.0
    val radius = 6.0

    println("Area of rectangle: ${calculateArea(length, breadth)}")
    println("Area of circle: ${calculateArea(radius)}")
}
