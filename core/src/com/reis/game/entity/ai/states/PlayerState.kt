package com.reis.game.entity.ai.states

import com.reis.game.entity.ai.AI
import com.reis.game.entity.ai.actions.Idle
import com.reis.game.entity.ai.transitions.PlayerActionCoordinator
import com.reis.game.entity.ai.transitions.StateTransition
import com.reis.game.entity.player.ActionProcessor

/**
 * Created by bernardoreis on 12/31/17.
 */
class PlayerState(): State(Idle()) {

    override fun onEnterState(ai: AI) {
        val action = ActionProcessor.getNextAction()
        this.setAction(action)
        this.addTransition(createTransition())
    }

    private fun createTransition(): StateTransition {
        val condition = PlayerActionCoordinator()
        val transition = StateTransition(this)
        transition.addCondition(condition)
        return transition
    }
}