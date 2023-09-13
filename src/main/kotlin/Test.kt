import me.alex_s168.ktlib.async.async

fun main() {

    async {
        repeat(2147) {
            println(it)
        }
    }.then {
        println("done")
    }

    println("aaa")

}