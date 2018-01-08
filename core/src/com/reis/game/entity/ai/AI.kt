package com.reis.game.entity.ai

import com.reis.game.entity.GameEntity
import com.reis.game.entity.ai.states.State
import com.reis.game.prototypes.AiData

/**
 * Created by bernardoreis on 12/25/17.
 */
class AI private constructor(private val entity: GameEntity, initialState: State) {

    private var currentState: State = initialState

    init {
        this.currentState.enterState(this)
    }

    companion object {
        fun parse(data: AiData, entity: GameEntity): AI {
            return AI(entity, data.initialState)
        }
    }

    fun setCurrentState(state: State) {
        this.currentState.leaveState(this, state)
        this.currentState = state
        this.currentState.enterState(this)
    }

    fun getCurrentState(): State {
        return this.currentState
    }

    fun update(delta: Float) {
        this.currentState.update(this, delta)
        val transition = this.currentState.checkTransitions(this)
        val nextState = transition?.getNextState()
        if (nextState != null) {
            this.setCurrentState(nextState)
        }
    }

    fun getEntity(): GameEntity {
        return this.entity
    }
}