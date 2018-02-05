package com.reis.game.mechanics.collision

import com.reis.game.entity.components.BodyComponent
import com.reis.game.mechanics.collision.CollisionManager.onGoingCollisions

/**
 * Created by bernardoreis on 1/7/18.
 */
object CollisionManager {

    val onGoingCollisions: HashSet<Collision> = HashSet()

    fun registerCollisions(collisions: Collection<Collision>) {
        collisions.forEach { this.registerCollision(it) }
    }

    fun registerCollision(collision: Collision) {
        val added = onGoingCollisions.add(collision)
        if (added) {
            notifyListenersCollisionStarted(collision)
        }
    }

    fun clearCollisions() {
        onGoingCollisions.clear()
    }

    private fun notifyListenersCollisionStarted(collision: Collision) {
        val collisionListener = getCollisionListener(collision)
        collisionListener?.onCollisionStarted(collision)
    }

    private fun notifyListenersCollisionEnded(collision: Collision) {
        val collisionListener = getCollisionListener(collision)
        collisionListener?.onCollisionEnded(collision)
    }

    private fun getCollisionListener(collision: Collision): CollisionListener? {
        val entity = collision.entity
        val collisionListener = entity.getComponent<BodyComponent>(BodyComponent::class.java)?.collisionListener
        val filter = collisionListener?.filter
        return if (filter == null || filter.test(collision)) collisionListener else null
    }

    private fun checkCollisionEnded(collision: Collision): Boolean {
        return !CollisionDetector.isTouching(collision.entity, collision.collidedWith)
    }

    @JvmStatic
    fun update() {
        val iterator = onGoingCollisions.iterator()
        while (iterator.hasNext()) {
            val collision = iterator.next()
            if (checkCollisionEnded(collision)) {
                notifyListenersCollisionEnded(collision)
                iterator.remove()
            }
        }
    }

}