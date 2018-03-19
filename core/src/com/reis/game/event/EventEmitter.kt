package com.reis.game.event

import com.reis.game.Main

/**
 * Created by berna on 18-Mar-18.
 */
interface EventEmitter {

    fun emit(event: Event) {
        // TODO: change this "main" access
        Main.getInstance().newEventProcessor.processEvent(event)
    }
}