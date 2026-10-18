fun titleLength(title: String): Int = title.length
fun possibleLength(title: String?): Int? = title?.length

fun main() {
    println(titleLength("Java")) // 4
    println(possibleLength(null)) // null
}
