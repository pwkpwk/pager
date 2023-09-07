package com.ambientbytes.pager

fun interface IOrderCheck<T> {
    fun inOrder(value: T, marker: T): Boolean
}
