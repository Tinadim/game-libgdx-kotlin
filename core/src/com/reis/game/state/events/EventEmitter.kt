package com.reis.game.state.events

/**
 * Created by bernardoreis on 2/10/18.
 */
interface EventEmitter {

    fun emit(event: Event) {
        EventProcessor.addEvent(event)
    }
}