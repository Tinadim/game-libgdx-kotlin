package com.reis.game.entity.ai.actions

import com.badlogic.gdx.math.Vector2
import com.reis.game.contants.ActionConstants
import com.reis.game.contants.GameConstants
import com.reis.game.entity.GameEntity
import com.reis.game.entity.components.BodyComponent
import com.reis.game.util.MathUtils

/**
 * Created by bernardoreis on 1/1/18.
 */
class Movement(private var velocity: Vector2, private var destination: Vector2? = null):
        EntityAction(ActionConstants.MOVE_PRIORITY) {

    init {
        this.selfReplaceable = true
    }

    override fun onStart(entity: GameEntity) {
        this.velocity = this.normalizeVelocity(velocity, entity)
        if (!velocity.isZero) {
            this.calcEntityOrientation(entity)
        }
    }

    override fun onUpdate(delta: Float, entity: GameEntity) {
        if (this.isStopped()) {
            this.finish()
            return
        }
        val distanceToWalk = calcDistanceToWalk(delta, entity)
        if (distanceToWalk.isZero) {
            this.finish()
        } else {
            entity.moveBy(distanceToWalk.x, distanceToWalk.y)
        }
    }

    private fun normalizeVelocity(velocity: Vector2, entity: GameEntity): Vector2 {
        var normalizedVelocity = velocity.cpy().scl(GameConstants.TILE_SIZE.toFloat() * 2)
        val destination = this.destination
        if (destination != null) {
            val distanceToDestination = Vector2(destination.x - entity.x, destination.y - entity.y)
            if (distanceToDestination.x < 0 && normalizedVelocity.x > 0) normalizedVelocity.x *= -1
            if (distanceToDestination.y < 0 && normalizedVelocity.y > 0) normalizedVelocity.y *= -1
        }
        return normalizedVelocity
    }

    fun isStopped(): Boolean {
        return this.velocity.isZero
    }

    private fun calcDistanceToWalk(delta: Float, entity: GameEntity): Vector2 {
        // TODO optimize this
        var distanceToWalk = this.velocity.cpy().scl(delta)
        val destination = this.destination
        if (destination != null) {
            val distanceToDestination = Vector2(destination.x - entity.x, destination.y - entity.y)
            if (distanceToDestination.epsilonEquals(Vector2.Zero)) return Vector2.Zero
            distanceToWalk.x = MathUtils.absMin(distanceToWalk.x, distanceToDestination.x)
            distanceToWalk.y = MathUtils.absMin(distanceToWalk.y, distanceToDestination.y)
        }
        if (distanceToWalk.x != 0f) {
            distanceToWalk.x = MathUtils.absMax(1f * Math.signum(distanceToWalk.x), distanceToWalk.x)
        }
        if (distanceToWalk.y != 0f) {
            distanceToWalk.y = MathUtils.absMax(1f * Math.signum(distanceToWalk.y), distanceToWalk.y)
        }

        val body = entity.getComponent<BodyComponent>(BodyComponent::class.java)
        if (body != null) {
            val collisionResults = body.checkCollision(distanceToWalk)
            distanceToWalk = collisionResults.distanceWalked
        }

        return distanceToWalk
    }

    private fun calcEntityOrientation(entity: GameEntity) {
        val orientation: Int
        if (Math.abs(this.velocity.x) >= Math.abs(this.velocity.y)) {
            orientation = if (this.velocity.x >= 0) GameConstants.EAST else GameConstants.WEST
        } else {
            orientation = if (this.velocity.y >= 0) GameConstants.NORTH else GameConstants.SOUTH
        }
        entity.orientation = orientation
    }
}