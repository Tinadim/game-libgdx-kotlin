package com.reis.game.state.requirements

import com.reis.game.entity.GameEntity
import com.reis.game.state.events.Event
import com.reis.game.state.events.EventType

/**
 * Created by bernardoreis on 2/10/18.
 */
class EntityInteraction(private val entityId: Int): Requirement(EventType.ENTITY_INTERACTION) {

    override fun update(event: Event) {
        if (event.trigger !is GameEntity) {
            return
        }
        if (event.trigger.id == entityId) {
            this.isFulfilled = true
        }
    }
}