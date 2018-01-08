package com.reis.game.mechanics.collision

/**
 * Created by bernardoreis on 1/7/18.
 */
object CollisionManager {

    val onGoingCollisions: HashSet<Collision> = HashSet()
    val listeners: ArrayList<CollisionListener> = ArrayList()

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

    fun addCollisionListener(listener: CollisionListener) {
        listeners.add(listener)
    }

    fun removeCollisionListener(listener: CollisionListener) {
        listeners.remove(listener)
    }

    private fun notifyListenersCollisionStarted(collision: Collision) {
        println("Collision started")
        this.listeners.forEach {
            val filter = it.filter
            if (filter == null || filter.test(collision)) {
                it.onCollisionStarted(collision)
            }
        }
    }

    private fun notifyListenersCollisionEnded(collision: Collision) {
        println("Collision ended")
        this.listeners.forEach {
            val filter = it.filter
            if (filter == null || filter.test(collision)) {
                it.onCollisionEnded(collision)
            }
        }
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