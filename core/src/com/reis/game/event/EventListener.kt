package com.reis.game.event

/**
 * Created by berna on 18-Mar-18.
 */
interface EventListener {

    val eventFilter: EventFilter?

    fun onEvent(event: Event)
}