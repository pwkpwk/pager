package com.ambientbytes.pager

interface ICursor<T> {
    val current: T
    fun moveNext(): Boolean
}
