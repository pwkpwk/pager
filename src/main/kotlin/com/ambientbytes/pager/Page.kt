package com.ambientbytes.pager

data class Page(val assets: List<TimestampedAsset>, val skipped: Int, val discarded: Int)
