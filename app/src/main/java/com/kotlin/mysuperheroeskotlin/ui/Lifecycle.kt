package com.kotlin.mysuperheroeskotlin.ui

val lifeCycleLinker = LifeCycleLinker()

// "Publisher-Subscriber" pattern
// More information: https://hackernoon.com/observer-vs-pub-sub-pattern-50d3b27f838c
class LifeCycleLinker : LifecyclePublisher {

    private val receivers = ArrayList<LifecycleSubscriber>()

    override fun initialize() {
        receivers.forEach(LifecycleSubscriber::initialize)
    }

    override fun register(subscriber: LifecycleSubscriber) {
        receivers.add(subscriber)
    }

    override fun unregister(subscriber: LifecycleSubscriber) {
        receivers.remove(subscriber)
    }

    override fun update() {
        receivers.forEach(LifecycleSubscriber::update)
    }

}

interface LifecyclePublisher {
    fun initialize()
    fun register(subscriber: LifecycleSubscriber)
    fun unregister(subscriber: LifecycleSubscriber)
    fun update()
}

interface LifecycleSubscriber {
    fun initialize() {}
    fun update() {}
}