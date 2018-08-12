package com.reis.game.entity.ai.controllers

import com.reis.game.Main
import com.reis.game.entity.GameEntity
import com.reis.game.entity.actions.EntityAction
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
abstract class AI constructor(val entity: GameEntity): EventListener {

    private var currentState: State? = null

    init {
        Main.getInstance().newEventProcessor.on(EventType.ACTION_STOPPED, this)
    }

    abstract fun buildStateMachine(data: AiData): State

    fun start(initialState: State) {
        setCurrentState(initialState)
    }

    open fun setCurrentState(state: State) {
        currentState?.leaveState(state)
        currentState = state
        currentState?.enterState()
    }

    fun getCurrentState(): State? {
        return currentState
    }

    fun update(delta: Float) {
        currentState?.update(delta)
        val transition = currentState?.checkTransitions()
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
        return entity.requireComponent(EntityControllerComponent::class.java)
    }

    override val eventFilter: EventFilter = object : EventFilter() {
        override fun test(event: Event): Boolean {
            return (event.trigger as EntityAction) === getCurrentAction()
        }
    }

    override fun onEvent(event: Event) {
        println("Interrupting current state...")
        val state = this.currentState
        if (state != null) {
            val interruptedState = InterruptedState(this, state, event.trigger as EntityAction)
            this.setCurrentState(interruptedState)
        }
    }
}