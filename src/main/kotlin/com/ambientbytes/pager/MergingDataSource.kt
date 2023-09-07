package com.ambientbytes.pager

import java.util.*
import kotlin.Comparator

/**
 * Single-use one-way read-only data source that enumerates and orders timestamped assets
 *
 * @param comparator Asset comparator that defines the order in that assets come out of the data source.
 * @param cursors Collection of cursors each enumerating a subset of data
 */
class MergingDataSource(
    comparator: Comparator<TimestampedAsset>,
    vararg cursors: ICursor<TimestampedAsset>
) : Iterable<TimestampedAsset> {

    private val queue: Queue<ICursor<TimestampedAsset>> =
        PriorityQueue { o1, o2 -> comparator.compare(o1.current, o2.current) }

    init {
        for (cursor in cursors) {
            if (cursor.moveNext()) {
                queue.offer(cursor)
            }
        }
    }

    override fun iterator(): Iterator<TimestampedAsset> = MergingIterator()

    private inner class MergingIterator : Iterator<TimestampedAsset> {
        override fun hasNext(): Boolean = !queue.isEmpty()

        override fun next(): TimestampedAsset =
            queue.poll().let {
                val value = it.current
                if (it.moveNext()) {
                    queue.offer(it)
                }
                value
            }
    }
}
