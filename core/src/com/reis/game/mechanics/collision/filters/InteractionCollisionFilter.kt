package com.reis.game.mechanics.collision.filters

import com.reis.game.entity.components.InteractionComponent
import com.reis.game.mechanics.collision.Collision

class InteractionCollisionFilter: CollisionFilter() {
    override fun test(t: Collision): Boolean {
        val entity = t.entity
        return entity.hasComponent(InteractionComponent::class.java)
    }
}