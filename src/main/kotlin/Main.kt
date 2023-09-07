import com.ambientbytes.pager.MergingDataSource
import com.ambientbytes.pager.Pager
import com.ambientbytes.pager.TimestampedAsset
import com.ambientbytes.pager.database.MockDatabase

//import java.sql.*

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}\n\nData source:")

    val database = MockDatabase()

    for (a in MergingDataSource(
        TimestampedAsset.AscendingComparator,
        database.queryAssets(1, null),
        database.queryCollections(1, null)
    )) {
        println("asset: ${a.id}, timestamp: ${a.timestamp}")
    }
    println()

    Pager(1, database).let {
        var pageNumber = 1
        var after: TimestampedAsset? = null
        var done = false

        do {
            it.getPage(8, after).let { page ->
                if (page.assets.isEmpty()) {
                    done = true
                } else {
                    println("Page ${pageNumber++}, skipped=${page.skipped}, discarded=${page.discarded}: [${page.assets.joinToString { a -> a.id.toString() }}]")
                    after = page.assets.last()
                }
            }
        } while (!done)
    }

//    DriverManager.getConnection("jdbc:postgresql://localhost/database").use {
//    }
}
