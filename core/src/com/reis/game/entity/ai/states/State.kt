package com.reis.game.entity.ai.states

import com.reis.game.entity.ai.AI
import com.reis.game.entity.ai.actions.Action
import com.reis.game.entity.ai.transitions.StateTransition

/**
 * Created by bernardoreis on 12/19/17.
 */
open class State constructor(private var action: Action) {

    private var transitions: ArrayList<StateTransition>? = null

    fun enterState(ai: AI) {
        this.onEnterState(ai)
        this.action.start(ai.getEntity())
    }

    open fun onEnterState(ai: AI) {}

    fun leaveState(ai: AI, nextState: State) {
        if (!this.action.isFinished()) {
            this.action.stop(ai.getEntity())
        }
        this.onLeaveState(ai, nextState)
    }

    open fun onLeaveState(ai: AI, nextState: State) {}

    fun update(ai: AI, delta: Float) {
        this.onUpdate(ai)
        this.action.update(delta, ai.getEntity())
    }

    open fun onUpdate(ai: AI) {}

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

    fun getAction(): Action {
        return this.action
    }

    protected fun setAction(action: Action) {
        this.action = action
    }
}