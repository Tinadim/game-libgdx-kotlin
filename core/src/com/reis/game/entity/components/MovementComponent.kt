package com.reis.game.entity.components

import com.badlogic.gdx.math.Vector2
import com.reis.game.contants.GameConstants
import com.reis.game.entity.GameEntity

/**
 * Created by bernardoreis on 1/1/18.
 */
class MovementComponent(entity: GameEntity): EntityComponent(entity) {

    private var velocity: Vector2 = Vector2.Zero
    private var body: BodyComponent? = entity.getComponent(BodyComponent::class.java)

    fun move(velocity: Vector2) {
        this.velocity = this.normalizeVelocity(velocity)
        this.calcEntityOrientation()
    }

    fun stop() {
        this.velocity = Vector2(0f, 0f)
    }

    private fun calcEntityOrientation() {
        val orientation: Int
        if (Math.abs(this.velocity.x) >= Math.abs(this.velocity.y)) {
            orientation = if (this.velocity.x >= 0) GameConstants.EAST else GameConstants.WEST
        } else {
            orientation = if (this.velocity.y >= 0) GameConstants.NORTH else GameConstants.SOUTH
        }
        entity.orientation = orientation
    }

    private fun calcDistanceToWalk(delta: Float): Vector2 {
        var distanceToWalk = this.velocity.cpy().scl(delta)
        val body = this.body
        if (body != null) {
            val collisionResults = body.checkCollision(distanceToWalk)
            distanceToWalk = collisionResults.distanceWalked
        }
        return distanceToWalk
    }

    override fun update(delta: Float) {
        if (this.isStopped()) {
            return
        }
        val distanceToWalk = calcDistanceToWalk(delta)
        if (distanceToWalk.isZero) {
            this.stop()
        } else {
            this.entity.moveBy(distanceToWalk.x, distanceToWalk.y)
        }
    }

    private fun normalizeVelocity(velocity: Vector2): Vector2 {
        return velocity.cpy().scl(GameConstants.TILE_SIZE.toFloat() * 2)
    }

    fun getVelocity(): Vector2 {
        return this.velocity
    }

    fun isStopped(): Boolean {
        return this.velocity.isZero
    }
}