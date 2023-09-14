import me.alex_s168.ktlib.atomic.*
import me.alex_s168.ktlib.atomic.num.AtomicFloat

fun main() {

    val atomic = AtomicFloat(10f)

    atomic += 2f

    println(atomic.get())

}