package com.reis.game.entity.ai.controllers

import com.reis.game.entity.GameEntity
import com.reis.game.entity.ai.actions.EntityAction
import com.reis.game.entity.ai.states.State
import com.reis.game.entity.components.EntityControllerComponent
import com.reis.game.prototypes.AiProto.AiData

/**
 * Created by bernardoreis on 12/25/17.
 *
 * Decoupling the StateMachineAI from the action logic. The state machine will be used
 * to feed the action processor, and the the action priority system will decide if the
 * current action should be replaced
 */
abstract class AI constructor(private val entity: GameEntity, aiData: AiData): EntityController {

    private var currentState: State = this.buildStateMachine(aiData)

    init {
        this.currentState.enterState(this)
    }

    abstract fun buildStateMachine(data: AiData): State

    fun setCurrentState(state: State) {
        this.currentState.leaveState(this, state)
        this.currentState = state
        this.currentState.enterState(this)
    }

    fun getCurrentState(): State {
        return this.currentState
    }

    override fun update(delta: Float) {
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

    fun addAction(action: EntityAction) {
        getEntityController().addAction(action)
    }

    fun getEntityController(): EntityControllerComponent {
        return this.entity.requireComponent(EntityControllerComponent::class.java)
    }
}