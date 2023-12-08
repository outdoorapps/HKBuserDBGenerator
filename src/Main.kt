import controllers.RouteController.Companion.getRoutes
import controllers.StopController.Companion.getCtbStops
import controllers.StopController.Companion.getKmbStops
import controllers.StopController.Companion.getNlbStops
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.io.Writer
import java.util.zip.GZIPOutputStream
import kotlin.text.Charsets.UTF_8
import kotlin.time.measureTime

const val EXPORT_PATH = "resources/output.json.gz"

fun main() {// todo proper log
    // 1. Get Routes todo MTRB routes
    execute("Getting KMB routes...") { getRoutes(Company.kmb) }
    execute("Getting CTB routes...") { getRoutes(Company.ctb) }
    executeWithCount("Getting NLB routes...") { getRoutes(Company.nlb) }

    // 2. Get Stops  todo NLB MTRB stops
    execute("Getting KMB stops...") { getKmbStops() }
    execute("Getting CTB stops...") { getCtbStops() }
    executeWithCount("Getting NLB stops...") { getNlbStops() }

    // 3. Get Route-stops & fare

    // 4. Write to Json.gz
    execute("Writing to \"$EXPORT_PATH\"...") {
        val output = FileOutputStream(EXPORT_PATH)
        output.use {
            val writer: Writer = OutputStreamWriter(GZIPOutputStream(it), UTF_8)
            writer.use { w ->
                w.write(SharedData.toJson())
            }
        }
    }
//        execute("Writing to \"output.json\"...") {
//        File("output.json").bufferedWriter().use {
//            it.write(SharedData.toJson())
//        }
//    }
}

fun executeWithCount(description: String, action: () -> Int) {
    print(description)
    var count: Int
    val t = measureTime {
        count = action()
    }
    println("added $count in $t")
}

fun execute(description: String, action: () -> Unit) {
    print(description)
    val t = measureTime { action() }
    println("finished in $t")
}