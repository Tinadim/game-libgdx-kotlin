package com.reis.game.mechanics.collision

import com.badlogic.gdx.math.Vector2

/**
 * Created by bernardoreis on 1/6/18.
 */
class CollisionResults {

    var distanceWalked: Vector2 = Vector2.Zero
    var collisions: ArrayList<Collision>? = null

    fun addCollision(collision: Collision) {
        if (this.collisions == null) {
            this.collisions = ArrayList()
        }
        this.collisions!!.add(collision)
    }
}