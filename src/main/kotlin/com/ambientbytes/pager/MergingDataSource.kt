package com.ambientbytes.pager

import java.util.*
import kotlin.Comparator

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
