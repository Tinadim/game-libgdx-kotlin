package com.reis.game.entity.ai.states.player

import com.reis.game.entity.GameEntity
import com.reis.game.entity.ai.controllers.AI
import com.reis.game.entity.components.InteractionComponent

class CollisionState(ai: AI, private val interactingWith: GameEntity): BasePlayerState(ai) {
    override fun executePrimaryAction() {
        // TODO check if interactingWith has a dialog?
        val component = ai.entity.getComponent<InteractionComponent>(InteractionComponent::class.java)
        component?.interact(interactingWith)
    }
}