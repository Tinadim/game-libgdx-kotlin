package com.reis.game.state.requirements

import com.reis.game.entity.GameEntity
import com.reis.game.state.events.Event
import com.reis.game.state.events.EventType

/**
 * Created by bernardoreis on 2/10/18.
 */
class TriggerFired(private val triggerId: Int): Requirement(EventType.TRIGGER_FIRED) {

    override fun update(event: Event) {
        if (event.trigger !is GameEntity) {
            return
        }
        if (event.trigger.id == triggerId) {
            this.isFulfilled = true
        }
    }
}