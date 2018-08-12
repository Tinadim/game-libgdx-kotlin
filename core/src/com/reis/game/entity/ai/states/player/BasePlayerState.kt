package com.reis.game.entity.ai.states.player

import com.reis.game.entity.actions.Idle
import com.reis.game.entity.ai.controllers.AI
import com.reis.game.entity.ai.states.State

abstract class BasePlayerState(ai: AI): State(ai) {

    abstract fun executePrimaryAction()

    override fun update(delta: Float) {
        super.update(delta)
        // TODO: Should this logic apply to all entities?
        val currentAction = ai.getCurrentAction()
        if (currentAction == null || currentAction.isFinished()) {
             ai.addAction(Idle())
        }
    }
}