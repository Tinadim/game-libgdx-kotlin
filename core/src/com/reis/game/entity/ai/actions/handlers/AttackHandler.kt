package com.reis.game.entity.ai.actions.handlers

import com.reis.game.entity.GameEntity
import com.reis.game.entity.components.CombatComponent

/**
 * Created by bernardoreis on 2/11/18.
 */
class AttackHandler(entity: GameEntity): ActionHandler(entity) {

    override fun handle() {
        val component = entity.getComponent<CombatComponent>(CombatComponent::class.java)
        val damageSource = component?.getPrimaryDamageSource()
        if (damageSource != null) {
            component.attack(damageSource)
        }
    }
}