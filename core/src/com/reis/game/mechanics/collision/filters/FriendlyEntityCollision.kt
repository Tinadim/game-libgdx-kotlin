package com.reis.game.mechanics.collision.filters

import com.reis.game.entity.components.CombatComponent
import com.reis.game.mechanics.collision.Collision

/**
 * Created by bernardoreis on 2/11/18.
 */
class FriendlyEntityCollision: CollisionFilter() {

    override fun test(t: Collision): Boolean {
        val entity = t.entity
        val component = entity.getComponent<CombatComponent>(CombatComponent::class.java)
        return TriggerFilter().test(t) && (component == null || !component.isEnemyOf(t.collidedWith))
    }
}