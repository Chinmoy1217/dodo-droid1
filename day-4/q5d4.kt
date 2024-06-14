// Abstract class 
abstract class Shape {
    abstract fun area(): Double
}

// Rectangle 
class Rectangle(private val length: Double, private val breadth: Double) : Shape() {
    override fun area(): Double {
        return length * breadth
    }

    fun perimeter(): Double {
        return 2 * (length + breadth)
    }
}

// Circle 
class Circle(private val radius: Double) : Shape() {
    override fun area(): Double {
        return Math.PI * radius * radius
    }

    fun perimeter(): Double {
        return 2 * Math.PI * radius
    }
}

fun main() {
    val shapes: List<Shape> = listOf(
        Rectangle(5.0, 10.0),
        Circle(7.0)
    )

    for (shape in shapes) {
        println("Area: ${shape.area()}")
        if (shape is Rectangle) {
            println("Perimeter: ${shape.perimeter()}")
        } else if (shape is Circle) {
            println("Perimeter: ${shape.perimeter()}")
        }
    }
}
