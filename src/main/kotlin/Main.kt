import com.ambientbytes.pager.MergingDataSource
import com.ambientbytes.pager.Pager
import com.ambientbytes.pager.TimestampedAsset
import com.ambientbytes.pager.database.MockDatabase

//import java.sql.*

private fun isDescending(args: Array<String>): Boolean = args.isNotEmpty() && args[0].contentEquals("desc", true)

fun main(args: Array<String>) {
    println("Program arguments: ${args.joinToString()}\n\nData source:")

    val descending = isDescending(args)
    val comparator = if (descending) TimestampedAsset.DescendingComparator else TimestampedAsset.AscendingComparator
    val order = if (descending) TimestampedAsset.DescendingTimestampOrder else TimestampedAsset.AscendingTimestampOrder

    val database = MockDatabase(comparator, order)

    for (a in MergingDataSource(
        comparator,
        database.queryAssets(1, null),
        database.queryCollections(1, null)
    )) {
        println("asset: ${a.id}, timestamp: ${a.timestamp}")
    }
    println()

    Pager(1, database, comparator).let {
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
