package com.reis.game.entity.ai.controllers.implementation

import com.reis.game.entity.GameEntity
import com.reis.game.entity.ai.controllers.AI
import com.reis.game.entity.ai.states.IdleState
import com.reis.game.entity.ai.states.State
import com.reis.game.entity.ai.states.WanderingState
import com.reis.game.entity.ai.transitions.ActionComplete
import com.reis.game.entity.ai.transitions.StateTransition
import com.reis.game.prototypes.AiProto.AiData
import com.reis.game.util.AiUtils

class NpcAi(entity: GameEntity): AI(entity) {
    override fun buildStateMachine(data: AiData): State {
        val idle = IdleState(this)
        val wandering = WanderingState(this, AiUtils.extractWayPoints(data))

        val idleToIdle = StateTransition(idle, 0)
        val idleToWandering = StateTransition(wandering, 1)
        val wanderingToIdle = StateTransition(idle)

        idleToIdle.addCondition(ActionComplete())
        idleToWandering.addCondition(ActionComplete())
        idleToWandering.addCondition(IdleState.createShouldIdleCondition())
        idleToWandering.addCondition(WanderingState.shouldMoveCondition(entity))
        wanderingToIdle.addCondition(ActionComplete())

        idle.addTransition(idleToIdle)
        idle.addTransition(idleToWandering)
        wandering.addTransition(wanderingToIdle)

        idle.sortTransitions()
        return idle
    }

}