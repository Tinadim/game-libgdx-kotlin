package com.reis.game.entity.ai.controllers

import com.reis.game.Main
import com.reis.game.entity.GameEntity
import com.reis.game.entity.ai.actions.EntityAction
import com.reis.game.entity.ai.states.InterruptedState
import com.reis.game.entity.ai.states.State
import com.reis.game.entity.components.EntityControllerComponent
import com.reis.game.event.Event
import com.reis.game.event.EventFilter
import com.reis.game.event.EventListener
import com.reis.game.event.EventType
import com.reis.game.prototypes.AiProto.AiData

/**
 * Created by bernardoreis on 12/25/17.
 *
 * Decoupling the StateMachineAI from the action logic. The state machine will be used
 * to feed the action processor, and the the action priority system will decide if the
 * current action should be replaced
 */
abstract class AI constructor(val entity: GameEntity, aiData: AiData):
        EntityController, EventListener {

    private var currentState: State = this.buildStateMachine(aiData)

    init {
        Main.getInstance().newEventProcessor.on(EventType.ACTION_STOPPED, this)
    }

    override fun start() {
        currentState.enterState(this)
    }

    abstract fun buildStateMachine(data: AiData): State

    fun setCurrentState(state: State) {
        currentState.leaveState(this, state)
        currentState = state
        currentState.enterState(this)
    }

    fun getCurrentState(): State {
        return this.currentState
    }

    override fun update(delta: Float) {
        currentState.update(this, delta)
        val transition = this.currentState.checkTransitions(this)
        val nextState = transition?.getNextState()
        if (nextState != null) {
            this.setCurrentState(nextState)
        }
    }

    fun addAction(action: EntityAction) {
        getEntityController().setAction(action)
    }

    fun getCurrentAction(): EntityAction? {
        return getEntityController().getCurrentAction()
    }

    fun getEntityController(): EntityControllerComponent {
        return this.entity.requireComponent(EntityControllerComponent::class.java)
    }

    override val filter: EventFilter = object : EventFilter() {
        override fun test(event: Event): Boolean {
            return (event.trigger as EntityAction) === getCurrentAction()
        }
    }

    override fun onEvent(event: Event) {
        println("Interrupting current state...")
        val interruptedState = InterruptedState(currentState, event.trigger as EntityAction)
        this.setCurrentState(interruptedState)
    }
}