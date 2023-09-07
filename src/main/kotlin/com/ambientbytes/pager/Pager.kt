package com.ambientbytes.pager

class Pager(private val userId: Long, private val database: IDatabase) {
    fun getPage(pageSize: Int, after: TimestampedAsset?): Page {
        var skippedItems = 0
        var discardedItems = 0
        val addedAssets = mutableSetOf<Long>()
        val list = mutableListOf<TimestampedAsset>().apply {
            var skippedHead = after == null

            for (asset in MergingDataSource(
                TimestampedAsset.AscendingComparator,
                database.queryCollections(userId, after?.timestamp),
                database.queryAssets(userId, after?.timestamp)
            )) {
                if (!skippedHead) {
                    if (after?.id == asset.id) {
                        skippedHead = true
                    } else {
                        skippedItems++
                    }
                } else {
                    if (addedAssets.contains(asset.id)) {
                        discardedItems++
                    } else {
                        add(asset)
                        addedAssets.add(asset.id)
                        if (size >= pageSize) {
                            break
                        }
                    }
                }
            }
        }

        return Page(list.toList(), skippedItems, discardedItems)
    }
}
