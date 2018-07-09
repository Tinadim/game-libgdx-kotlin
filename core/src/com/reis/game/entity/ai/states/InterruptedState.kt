package com.reis.game.entity.ai.states

import com.reis.game.entity.ai.actions.EntityAction
import com.reis.game.entity.ai.controllers.AI
import com.reis.game.entity.ai.transitions.StateTransition
import com.reis.game.entity.ai.transitions.TransitionCondition

class InterruptedState(previousState: State, action: EntityAction): State() {

    init {
        val condition = object : TransitionCondition {
            override fun evaluate(ai: AI): Boolean {
                return action.isFinished()
            }
        }

        val transition = StateTransition(previousState)
        transition.addCondition(condition)

        this.addTransition(transition)
    }
}