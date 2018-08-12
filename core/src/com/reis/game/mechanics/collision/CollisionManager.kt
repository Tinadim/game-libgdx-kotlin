package com.reis.game.mechanics.collision

import com.reis.game.entity.GameEntity
import kotlin.collections.HashMap
import kotlin.collections.HashSet

/**
 * Created by bernardoreis on 1/7/18.
 */
class CollisionManager {

    private val activeCollisions: HashSet<Collision> = HashSet()
    private val listenersForEntity: MutableMap<GameEntity, HashSet<CollisionListener>> = HashMap()

    fun registerListener(entity: GameEntity, listener: CollisionListener) {
        val listeners = getListenersForEntity(entity)
        listeners.add(listener)
        listenersForEntity[entity] = listeners
    }

    fun removeListener(entity: GameEntity, listener: CollisionListener) {
        val listeners = getListenersForEntity(entity)
        listeners.remove(listener)
        listenersForEntity[entity] = listeners
    }

    private fun getListenersForEntity(entity: GameEntity): HashSet<CollisionListener> {
        var listeners = listenersForEntity[entity]
        if (listeners == null) {
            listeners = HashSet()
        }
        return listeners
    }

    fun registerCollisions(collisions: Collection<Collision>) {
        collisions.forEach { registerCollision(it) }
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
        notifyListenersForEntity(collision.entity, collision, false)
        notifyListenersForEntity(collision.collidedWith, collision.getCounterpart(), false)
    }

    private fun notifyListenersCollisionEnded(collision: Collision) {
        notifyListenersForEntity(collision.entity, collision, true)
        notifyListenersForEntity(collision.collidedWith, collision.getCounterpart(), true)
    }

    private fun notifyListenersForEntity(entity: GameEntity, collision: Collision, ended: Boolean) {
        val listeners = listenersForEntity[entity]
        listeners?.forEach {
            notifyListener(it, collision, ended)
        }
    }

    private fun notifyListener(listener: CollisionListener, collision: Collision, ended: Boolean) {
        val filter = listener.collisionFilter
        if (filter == null || filter.test(collision)) {
            if (ended) {
                listener.onCollisionEnded(collision)
            } else {
                listener.onCollisionStarted(collision)
            }
        }
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