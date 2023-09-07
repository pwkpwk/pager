package com.ambientbytes.pager.database

import com.ambientbytes.pager.ICursor
import com.ambientbytes.pager.IDatabase
import com.ambientbytes.pager.IOrderCheck
import com.ambientbytes.pager.TimestampedAsset
import java.lang.Exception

class MockDatabase(
    private val comparator: Comparator<TimestampedAsset>,
    private val timestampOrder: IOrderCheck<Long>
) : IDatabase {

    override fun queryAssets(userId: Long, startAt: Long?): ICursor<TimestampedAsset> =
        Cursor(individualAssets, startAt)

    override fun queryCollections(userId: Long, startAt: Long?): ICursor<TimestampedAsset> =
        Cursor(collections, startAt)

    private inner class Cursor(source: Array<TimestampedAsset>, startAt: Long?) : ICursor<TimestampedAsset> {

        private var position = -1
        private val data = if (startAt == null) source else assetsStartingAt(source, startAt)

        override val current: TimestampedAsset
            get() =
                if (position >= 0 && position < data.size) {
                    data[position]
                } else {
                    throw Exception("No current value")
                }

        override fun moveNext(): Boolean =
            if (canAdvance) {
                ++position
                true
            } else {
                false
            }

        private val canAdvance: Boolean get() = position < data.size - 1
    }

    private fun assetsStartingAt(data: Array<TimestampedAsset>, timestamp: Long): Array<TimestampedAsset> =
        mutableListOf<TimestampedAsset>().apply {
            for (a in data) {
                if (timestampOrder.inOrder(a.timestamp, timestamp)) {
                    add(a)
                }
            }
            sortWith(comparator)
        }.toTypedArray()

    fun allAssets(data: Array<TimestampedAsset>): Array<TimestampedAsset> =
        mutableListOf<TimestampedAsset>().apply {
            addAll(data)
            sortWith(comparator)
        }.toTypedArray()

    private companion object {

        val collections = arrayOf(
            TimestampedAsset(20, 85),
            TimestampedAsset(21, 85),
            TimestampedAsset(22, 85),
            TimestampedAsset(23, 85),
            TimestampedAsset(24, 85),
            TimestampedAsset(25, 85),
            TimestampedAsset(26, 85),
            TimestampedAsset(11, 65),
            TimestampedAsset(30, 65),
            TimestampedAsset(31, 65),
            TimestampedAsset(32, 65),
            TimestampedAsset(33, 65),
            TimestampedAsset(34, 65),
            TimestampedAsset(35, 65),
        )

        val individualAssets = arrayOf(
            TimestampedAsset(1, 100),
            TimestampedAsset(2, 100),
            TimestampedAsset(3, 95),
            TimestampedAsset(4, 94),
            TimestampedAsset(5, 90),
            TimestampedAsset(6, 80),
            TimestampedAsset(10, 85),
            TimestampedAsset(12, 84),
            TimestampedAsset(9, 83),
            TimestampedAsset(7, 80),
            TimestampedAsset(11, 70),
            TimestampedAsset(8, 60),
            TimestampedAsset(13, 55),
            TimestampedAsset(14, 50),
        )
    }
}
