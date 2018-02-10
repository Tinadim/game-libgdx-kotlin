package com.reis.game.mechanics.collision

import com.reis.game.entity.GameEntity
import com.reis.game.entity.components.BodyComponent
import com.reis.game.state.events.Event
import com.reis.game.state.events.EventEmitter
import com.reis.game.util.Filter

/**
 * Created by bernardoreis on 2/10/18.
 */
class CollisionTrigger(id: Int): GameEntity(id), EventEmitter {

    init {
        val body = createBody()
        this.addComponent(body)
    }

    private fun createBody(): BodyComponent {
        val listener = object: CollisionListener {
            override val filter: Filter<GameEntity>? = null
            override fun onCollisionStarted(collision: Collision) {
                this@CollisionTrigger.fire()
            }
            override fun onCollisionEnded(collision: Collision) {}
        }
        val body = BodyComponent(this, listener)
        body.isTrigger = true
        return body
    }

    fun fire() {
        println("Firing event")
        this.emit(Event())
    }
}