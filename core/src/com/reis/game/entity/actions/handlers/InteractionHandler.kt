package com.reis.game.entity.actions.handlers

import com.reis.game.entity.GameEntity
import com.reis.game.entity.components.InteractionComponent

/**
 * Created by bernardoreis on 2/11/18.
 */
class InteractionHandler(originator: GameEntity, private val interactingWith: GameEntity): ActionHandler(originator) {

    override fun handle() {
        // TODO check if interactingWith has a dialog?
        val component = entity.getComponent<InteractionComponent>(InteractionComponent::class.java)
        component?.interact(interactingWith)
    }
}