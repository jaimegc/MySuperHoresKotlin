package com.kotlin.mysuperheroeskotlin.common

interface TimeProvider {
    fun time(): Long
}

class RealTimeProvider : TimeProvider {
    override fun time() = System.currentTimeMillis()
}