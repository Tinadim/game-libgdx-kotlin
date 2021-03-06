package com.reis.game.mechanics.collision

import com.reis.game.util.Filter

/**
 * Created by bernardoreis on 1/7/18.
 */
interface CollisionListener {

    val filter: Filter<Collision>?

    fun onCollisionStarted(collision: Collision)

    fun onCollisionEnded(collision: Collision)
}