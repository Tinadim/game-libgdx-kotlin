package com.reis.game.mechanics.collision

import com.reis.game.entity.GameEntity
import java.awt.geom.Rectangle2D

/**
 * Created by bernardoreis on 1/6/18.
 */
data class Collision constructor(val entity: GameEntity, val collidedWith: GameEntity,
    val intersection: Rectangle2D) {

    fun getCounterpart(): Collision {
        return Collision(collidedWith, entity, intersection)
    }
}