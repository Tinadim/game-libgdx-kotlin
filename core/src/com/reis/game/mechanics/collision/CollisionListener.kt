package com.reis.game.mechanics.collision

import com.reis.game.mechanics.collision.filters.CollisionFilter

/**
 * Created by bernardoreis on 1/7/18.
 */
interface CollisionListener {

    val collisionFilter: CollisionFilter?

    fun onCollisionStarted(collision: Collision)

    fun onCollisionEnded(collision: Collision)
}