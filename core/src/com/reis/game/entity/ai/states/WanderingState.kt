package com.reis.game.entity.ai.states

import com.badlogic.gdx.math.Vector2
import com.reis.game.entity.ai.actions.Movement
import com.reis.game.entity.ai.controllers.AI

class WanderingState(private val waypoints: Array<Vector2>): State() {

    private var currentWaypointIndex: Int = 0

    override fun enterState(ai: AI) {
        super.enterState(ai)
        val action = Movement(, getNextWaypoint())
        ai.addAction(action)
    }

    private fun getNextWaypoint(): Vector2 {
        if (currentWaypointIndex == waypoints.size)
            currentWaypointIndex = 0
        return waypoints[currentWaypointIndex++]
    }
}