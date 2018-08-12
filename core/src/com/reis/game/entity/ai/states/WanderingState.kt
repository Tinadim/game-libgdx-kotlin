package com.reis.game.entity.ai.states

import com.badlogic.gdx.math.Vector2
import com.reis.game.entity.GameEntity
import com.reis.game.entity.actions.Movement
import com.reis.game.entity.ai.controllers.AI
import com.reis.game.entity.ai.transitions.TransitionCondition
import com.reis.game.entity.components.MovementComponent

class WanderingState(ai: AI, private val waypoints: Array<Vector2>): State(ai) {

    private var currentWaypointIndex: Int = 0

    override fun enterState() {
        super.enterState()
        val entity = ai.entity
        val movementComponent = entity
                .requireComponent<MovementComponent>(MovementComponent::class.java)
        movementComponent.move(movementComponent.defaultVelocity, getNextWaypoint())
    }

    private fun getNextWaypoint(): Vector2 {
        if (currentWaypointIndex == waypoints.size) {
            println("Last waypoint reached. Starting over...")
            currentWaypointIndex = 0
        }
        println("Getting next waypoint")
        return waypoints[currentWaypointIndex++]
    }

    companion object {
        @JvmStatic
        fun shouldMoveCondition(entity: GameEntity) : TransitionCondition {
            return object : TransitionCondition {
                override fun evaluate(ai: AI): Boolean {
                    return entity.hasComponent(MovementComponent::class.java)
                }
            }
        }
    }
}