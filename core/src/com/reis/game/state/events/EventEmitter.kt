package com.reis.game.state.events

import com.reis.game.Main

/**
 * Created by bernardoreis on 2/10/18.
 */
interface EventEmitter {

    fun emit(event: Event) {
        // TODO: change this "main" access
        Main.getInstance().eventProcessor.addEvent(event)
    }
}