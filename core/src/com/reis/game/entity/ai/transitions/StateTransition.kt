package com.reis.game.entity.ai.transitions

import com.reis.game.entity.ai.AI
import com.reis.game.entity.ai.states.State

/**
 * Created by bernardoreis on 12/20/17.
 */
class StateTransition constructor(private val nextState: State, private var priority: Int = 0) {

    private var conditions: ArrayList<TransitionCondition>? = null

    fun getNextState(): State {
        return this.nextState
    }

    fun shouldExecute(ai: AI): Boolean {
        return checkConditionsFulfilled(ai)
    }

    private fun checkConditionsFulfilled(ai: AI): Boolean {
        val conditions = this.conditions
        conditions ?: return true

        val fulfilledConditions = conditions.filter {
            condition -> condition.evaluate(ai)
        }
        return fulfilledConditions.size == conditions.size
    }

    fun addConditions(conditions: ArrayList<TransitionCondition>) {
        conditions.forEach { this.addCondition(it) }
    }

    fun addCondition(condition: TransitionCondition) {
        var conditions = this.conditions
        if (conditions == null) {
            conditions = ArrayList()
            this.conditions = conditions
        }
        conditions.add(condition)
    }

    fun getPriority(): Int {
        return this.priority
    }
}