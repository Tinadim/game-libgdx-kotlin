package com.reis.game.entity.actions

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.reis.game.contants.ActionConstants
import com.reis.game.contants.GameConstants
import com.reis.game.entity.GameEntity
import com.reis.game.entity.components.BodyComponent

/**
 * Created by bernardoreis on 1/28/18.
 */
class KnockBack(private val force: Vector2, duration: Float, private val distance: Int = 0) :
        DurationAction(ActionConstants.KNOCKBACK_PRIORITY, duration) {

    override fun onStart(entity: GameEntity) {
        super.onStart(entity)
        if (distance > 0) {
            var distance = this.calcDistanceToMove()
            if (entity.hasComponent(BodyComponent::class.java)) {
                distance = checkCollisions(entity, distance)
            }
            // TODO make the duration for the movement relative to the force applied
            entity.addAction(Actions.moveBy(distance.x, distance.y, duration * 0.5f))
        }
    }

    private fun calcDistanceToMove(): Vector2 {
        return Vector2(Math.signum(force.x), Math.signum(force.y))
                .scl(distance * GameConstants.TILE_SIZE.toFloat())
    }

    private fun checkCollisions(entity: GameEntity, distance: Vector2): Vector2 {
        val body = entity.requireComponent<BodyComponent>(BodyComponent::class.java)
        val results = body.checkCollision(distance)
        return results.distanceWalked
    }
}