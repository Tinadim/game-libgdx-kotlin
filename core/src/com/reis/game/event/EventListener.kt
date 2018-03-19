package com.reis.game.event

/**
 * Created by berna on 18-Mar-18.
 */
interface EventListener {

    val filter: EventFilter?

    fun onEvent(event: Event)
}