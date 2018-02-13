package com.reis.game.entity.ai.states

import com.reis.game.entity.ai.controllers.AI
import com.reis.game.entity.ai.transitions.StateTransition

/**
 * Created by bernardoreis on 12/19/17.
 */
open class State constructor() {

    private var transitions: ArrayList<StateTransition>? = null

    open fun enterState(ai: AI) {}

    open fun leaveState(ai: AI, nextState: State) {}

    open fun update(ai: AI, delta: Float) {}

    fun addTransitions(transitions: ArrayList<StateTransition>) {
        transitions.forEach { this.addTransition(it) }
        sortTransitions()
    }

    fun addTransition(transition: StateTransition) {
        var transitions = this.transitions
        if (transitions == null) {
            transitions = ArrayList()
            this.transitions = transitions
        }
        transitions.add(transition)
    }

    fun checkTransitions(ai: AI): StateTransition? {
        return this.transitions?.find { it.shouldExecute(ai) }
    }

    fun sortTransitions() {
        this.transitions?.sortBy { it.getPriority() }
    }
}