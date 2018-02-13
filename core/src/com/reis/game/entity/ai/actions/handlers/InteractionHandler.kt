package com.reis.game.entity.ai.actions.handlers

import com.reis.game.entity.GameEntity
import com.reis.game.entity.components.InteractionComponent

/**
 * Created by bernardoreis on 2/11/18.
 */
class InteractionHandler(entity: GameEntity, private val target: GameEntity): ActionHandler(entity) {

    override fun handle() {
        val component = entity.getComponent<InteractionComponent>(InteractionComponent::class.java)
        component?.interact(target)
    }
}