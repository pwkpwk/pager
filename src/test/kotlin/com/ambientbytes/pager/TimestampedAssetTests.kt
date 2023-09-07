package com.ambientbytes.pager

import kotlin.test.Test
import kotlin.test.assertEquals

class TimestampedAssetTests {
    @Test
    fun equalAssets_comparatorsReturn0() {
        assertEquals(0, TimestampedAsset.AscendingComparator.compare(TimestampedAsset(1, 1), TimestampedAsset(1, 1)))
        assertEquals(0, TimestampedAsset.DescendingComparator.compare(TimestampedAsset(1, 1), TimestampedAsset(1, 1)))
    }

    @Test
    fun descendingTimestampsEqualAssets_ascendingComparatorsReturnsNegative() {
        assertEquals(-1, TimestampedAsset.AscendingComparator.compare(TimestampedAsset(1, 10), TimestampedAsset(2, 5)))
    }

    @Test
    fun equalTimestampsAscendingAssets_ascendingComparatorsReturnsNegative() {
        assertEquals(-1, TimestampedAsset.AscendingComparator.compare(TimestampedAsset(1, 10), TimestampedAsset(2, 10)))
    }

    @Test
    fun ascendingTimestampsEqualAssets_ascendingComparatorsReturnsPositive() {
        assertEquals(1, TimestampedAsset.AscendingComparator.compare(TimestampedAsset(1, 5), TimestampedAsset(1, 10)))
    }

    @Test
    fun equalTimestampsDescendingAssets_ascendingComparatorsReturnsPositive() {
        assertEquals(1, TimestampedAsset.AscendingComparator.compare(TimestampedAsset(2, 5), TimestampedAsset(1, 5)))
    }

    @Test
    fun descendingTimestampsEqualAssets_descendingComparatorsReturnsPositive() {
        assertEquals(1, TimestampedAsset.DescendingComparator.compare(TimestampedAsset(1, 10), TimestampedAsset(2, 5)))
    }

    @Test
    fun equalTimestampsAscendingAssets_descendingComparatorsReturnsPositive() {
        assertEquals(1, TimestampedAsset.DescendingComparator.compare(TimestampedAsset(1, 10), TimestampedAsset(2, 10)))
    }

    @Test
    fun ascendingTimestampsEqualAssets_descendingComparatorsReturnsNegative() {
        assertEquals(-1, TimestampedAsset.DescendingComparator.compare(TimestampedAsset(1, 5), TimestampedAsset(1, 10)))
    }

    @Test
    fun equalTimestampsDescendingAssets_descendingComparatorsReturnsNegative() {
        assertEquals(-1, TimestampedAsset.DescendingComparator.compare(TimestampedAsset(2, 5), TimestampedAsset(1, 5)))
    }
}
