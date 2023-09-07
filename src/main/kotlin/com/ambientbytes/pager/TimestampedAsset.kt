package com.ambientbytes.pager

data class TimestampedAsset(val id: Long, val timestamp: Long) {
    companion object {
        val AscendingComparator: Comparator<TimestampedAsset> =
            Comparator { o1, o2 ->
                val tsc = o2.timestamp.compareTo(o1.timestamp)
                if (tsc != 0) tsc else o1.id.compareTo(o2.id)
            }

        val DescendingComparator: Comparator<TimestampedAsset> =
            Comparator { o1, o2 ->
                val tsc = o1.timestamp.compareTo(o2.timestamp)
                if (tsc != 0) tsc else o2.id.compareTo(o1.id)
            }
    }
}
