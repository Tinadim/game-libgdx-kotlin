package com.reis.game.entity.ai.actions

import com.badlogic.gdx.math.Vector2
import com.reis.game.contants.ActionConstants
import com.reis.game.contants.GameConstants
import com.reis.game.entity.GameEntity
import com.reis.game.entity.components.BodyComponent
import com.reis.game.entity.components.MovementComponent

/**
 * Created by bernardoreis on 1/1/18.
 */
class Movement(private var velocity: Vector2): EntityAction(ActionConstants.MOVE_PRIORITY) {

    //Local cache of movement component for the entity
    private var component: MovementComponent? = null

    init {
        this.selfReplaceable = true
    }

    override fun onStart(entity: GameEntity) {
//        this.component = entity.requireComponent(MovementComponent::class.java)
//        component?.move(velocity)
        this.velocity = this.normalizeVelocity(velocity)
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
            this.stop(entity)
        } else {
            entity.moveBy(distanceToWalk.x, distanceToWalk.y)
        }

//        val component = this.component
//        if (component != null && component.isStopped()) {
//            this.finish()
//        }
    }

    private fun normalizeVelocity(velocity: Vector2): Vector2 {
        return velocity.cpy().scl(GameConstants.TILE_SIZE.toFloat() * 2)
    }

    fun isStopped(): Boolean {
        return this.velocity.isZero
    }

    private fun calcDistanceToWalk(delta: Float, entity: GameEntity): Vector2 {
        var distanceToWalk = this.velocity.cpy().scl(delta)
        if (distanceToWalk.x != 0f) {
            distanceToWalk.x = Math.signum(distanceToWalk.x) *
                    Math.max(1f, Math.round(Math.abs(distanceToWalk.x)).toFloat())
        }
        if (distanceToWalk.y != 0f) {
            distanceToWalk.y = Math.signum(distanceToWalk.y) *
                    Math.max(1f, Math.round(Math.abs(distanceToWalk.y)).toFloat())
        }

        println("Distance to walk x: " + distanceToWalk.x)
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