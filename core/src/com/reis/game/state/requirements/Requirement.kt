package com.reis.game.state.requirements

import com.reis.game.state.events.Event
import com.reis.game.state.events.EventType

/**
 * Created by bernardoreis on 2/10/18.
 */
abstract class Requirement(private val eventType: EventType) {

    var isFulfilled: Boolean = false

    fun checkEventType(event: Event): Boolean {
        return event.type == eventType
    }

    abstract fun update(event: Event)
}