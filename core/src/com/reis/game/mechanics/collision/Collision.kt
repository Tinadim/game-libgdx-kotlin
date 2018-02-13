package com.reis.game.mechanics.collision

import com.reis.game.entity.GameEntity

/**
 * Created by bernardoreis on 1/6/18.
 */
data class Collision constructor(val entity: GameEntity, val collidedWith: GameEntity) {

    fun getCounterpart(): Collision {
        return Collision(collidedWith, entity)
    }
}