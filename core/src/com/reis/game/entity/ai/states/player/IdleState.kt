package com.reis.game.entity.ai.states.player

import com.reis.game.entity.ai.controllers.AI
import com.reis.game.entity.components.CombatComponent

class IdleState(ai: AI): BasePlayerState(ai) {
    override fun executePrimaryAction() {
        val component = ai.entity.getComponent<CombatComponent>(CombatComponent::class.java)
        val damageSource = component?.getPrimaryDamageSource()
        if (damageSource != null) {
            component.attack(damageSource)
        }
    }
}