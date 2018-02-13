package com.reis.game.entity.ai.actions

import com.reis.game.contants.ActionConstants
import com.reis.game.entity.GameEntity
import com.reis.game.state.events.Event
import com.reis.game.state.events.EventEmitter
import com.reis.game.state.events.EventType

/**
 * Created by bernardoreis on 2/11/18.
 */
class Interact(private val target:GameEntity): Action(ActionConstants.INTERACTION_PRIORITY), EventEmitter {

    override fun onStart(entity: GameEntity) {
        super.onStart(entity)
        this.emit(Event(EventType.ENTITY_INTERACTION, target))
        this.adjustEntityOrientation(entity)
    }

    private fun adjustEntityOrientation(entity: GameEntity) {
        val orientation = (entity.orientation + 2) % 4
        this.target.orientation = orientation
    }
}