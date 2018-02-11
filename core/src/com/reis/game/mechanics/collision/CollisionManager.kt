package com.reis.game.mechanics.collision

import com.reis.game.entity.GameEntity
import com.reis.game.entity.components.BodyComponent

/**
 * Created by bernardoreis on 1/7/18.
 */
class CollisionManager {

    val activeCollisions: HashSet<Collision> = HashSet()

    fun registerCollisions(collisions: Collection<Collision>) {
        collisions.forEach { this.registerCollision(it) }
    }

    fun registerCollision(collision: Collision) {
        val added = activeCollisions.add(collision)
        if (added) {
            notifyListenersCollisionStarted(collision)
        }
    }

    fun clearCollisions() {
        activeCollisions.clear()
    }

    private fun notifyListenersCollisionStarted(collision: Collision) {
        var collisionListener = getCollisionListener(collision.entity)
        collisionListener?.onCollisionStarted(collision)

        collisionListener = getCollisionListener(collision.collidedWith)
        collisionListener?.onCollisionStarted(collision)
    }

    private fun notifyListenersCollisionEnded(collision: Collision) {
        var collisionListener = getCollisionListener(collision.entity)
        collisionListener?.onCollisionEnded(collision)

        collisionListener = getCollisionListener(collision.collidedWith)
        collisionListener?.onCollisionEnded(collision)
    }

    private fun getCollisionListener(entity: GameEntity): CollisionListener? {
        val collisionListener = entity.getComponent<BodyComponent>(BodyComponent::class.java)?.collisionListener
        val filter = collisionListener?.filter
        return if (filter == null || filter.test(entity)) collisionListener else null
    }

    private fun checkCollisionEnded(collision: Collision): Boolean {
        return !CollisionDetector.isTouching(collision.entity, collision.collidedWith)
    }

    fun update() {
        val iterator = activeCollisions.iterator()
        while (iterator.hasNext()) {
            val collision = iterator.next()
            if (checkCollisionEnded(collision)) {
                notifyListenersCollisionEnded(collision)
                iterator.remove()
            }
        }
    }

}