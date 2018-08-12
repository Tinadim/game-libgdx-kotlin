package com.reis.game.mechanics.collision

import com.reis.game.Main
import com.reis.game.entity.GameEntity
import com.reis.game.entity.components.BodyComponent
import com.reis.game.entity.player.Player
import com.reis.game.mechanics.collision.filters.CollisionFilter
import com.reis.game.state.events.Event
import com.reis.game.state.events.EventEmitter
import com.reis.game.state.events.EventType
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
            override val collisionFilter: CollisionFilter = object : CollisionFilter() {
                override fun test(t: Collision): Boolean {
                    return t.collidedWith is Player
                }
            }

            override fun onCollisionStarted(collision: Collision) {
                this@CollisionTrigger.fire()
            }
            override fun onCollisionEnded(collision: Collision) {}
        }
        Main.getInstance().collisionManager.registerListener(this, listener)
        val body = BodyComponent(this)
        body.isTrigger = true
        return body
    }

    fun fire() {
        this.emit(Event(EventType.TRIGGER_FIRED, this))
    }
}