package com.reis.game.event

/**
 * Created by berna on 18-Mar-18.
 */
class EventProcessor {

    private val listeners: MutableMap<EventType, MutableList<EventListener>> = HashMap()

    fun on(type: EventType, listener: EventListener) {
        addListener(type, listener)
    }

    fun addListener(type: EventType, listener: EventListener) {
        var listenersForType = listeners[type]
        if (listenersForType == null) {
            listenersForType = ArrayList()
        }
        listenersForType.add(listener)
        listeners[type] = listenersForType
    }

    fun processEvent(event: Event) {
        val listenersForType = listeners[event.type]
        listenersForType?.forEach {
            val passFilter = it.filter?.test(event) ?: true
            if (passFilter) {
                it.onEvent(event)
            }
        }
    }
}