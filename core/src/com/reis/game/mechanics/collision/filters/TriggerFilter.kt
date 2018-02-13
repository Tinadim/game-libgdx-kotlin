package com.reis.game.mechanics.collision.filters

import com.reis.game.entity.components.BodyComponent
import com.reis.game.mechanics.collision.Collision

/**
 * Created by bernardoreis on 2/12/18.
 */
class TriggerFilter: CollisionFilter() {
    override fun test(t: Collision): Boolean {
        return !(t.collidedWith.getComponent<BodyComponent>(BodyComponent::class.java)?.isTrigger ?: false)
    }
}