package com.reis.game.entity.ai.transitions

import com.reis.game.entity.ai.AI

/**
 * Created by bernardoreis on 12/20/17.
 */
interface TransitionCondition {

    fun evaluate(ai: AI): Boolean
}