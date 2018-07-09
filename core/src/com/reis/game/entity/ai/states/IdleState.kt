package com.reis.game.entity.ai.states

import com.reis.game.contants.ActionConstants.BASE_TURN_DURATION
import com.reis.game.contants.ActionConstants.DEFAULT_MAX_IDLE_TURNS
import com.reis.game.contants.ActionConstants.DEFAULT_MIN_IDLE_TURNS
import com.reis.game.entity.ai.actions.EntityAction
import com.reis.game.entity.ai.actions.Idle
import com.reis.game.entity.ai.controllers.AI
import com.reis.game.entity.ai.transitions.TransitionCondition
import com.reis.game.util.Utils

class IdleState: State() {
    override fun enterState(ai: AI) {
        super.enterState(ai)
        val action = createRandomIdleAction()
        ai.addAction(action)
    }

    private fun createRandomIdleAction(): EntityAction {
        val idleTurns = Utils.randomInt(DEFAULT_MIN_IDLE_TURNS, DEFAULT_MAX_IDLE_TURNS)
        val duration = idleTurns * BASE_TURN_DURATION
        return Idle(duration)
    }

    companion object {
        @JvmStatic
        fun createShouldIdleCondition() : TransitionCondition {
            return object : TransitionCondition {
                override fun evaluate(ai: AI): Boolean {
                    return Utils.randomBoolean()
                }
            }
        }
    }
}