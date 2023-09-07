package com.ambientbytes.pager

interface IDatabase {
    fun queryAssets(userId: Long, startAt: Long?): ICursor<TimestampedAsset>

    fun queryCollections(userId: Long, startAt: Long?): ICursor<TimestampedAsset>
}
