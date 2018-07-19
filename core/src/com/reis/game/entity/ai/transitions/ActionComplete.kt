package com.reis.game.entity.ai.transitions

import com.reis.game.entity.ai.controllers.AI

class ActionComplete : TransitionCondition {
    override fun evaluate(ai: AI): Boolean {
        return ai.getCurrentAction()?.isFinished() ?: true
    }
}