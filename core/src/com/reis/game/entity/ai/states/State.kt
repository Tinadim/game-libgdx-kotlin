package com.reis.game.entity.ai.states

import com.reis.game.entity.ai.controllers.AI
import com.reis.game.entity.ai.transitions.StateTransition

/**
 * Created by bernardoreis on 12/19/17.
 *
 * Class that represents the current state of a smart entity. They should store the transitions
 * for other states and should be able of do two main tasks:
 * - Feeding the action processor associated with the entity with actions
 * - Store the state transitions that will be checked to determine if a transition should happen.
 * There's no need to check transitions manually during the state update call, as that will be
 * invoked by the AI itself.
 */
open class State(val ai: AI) {

    var transitions: ArrayList<StateTransition>? = null

    /**
     * This, ideally is where the action would be created and fed to the action processor
     */
    open fun enterState() {}

    /**
     * This method should be used for cleanups or to store data before transitioning to a new state
     */
    open fun leaveState(nextState: State) {}

    /**
     * Used for custom logic during the state update. No need to check for transitions here
     * as that is invoked by the AI itself
     */
    open fun update(delta: Float) {}

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

    fun checkTransitions(): StateTransition? {
        return this.transitions?.find { it.shouldExecute(ai) }
    }

    fun sortTransitions() {
        this.transitions?.sortBy { it.getPriority() * -1 }
    }
}