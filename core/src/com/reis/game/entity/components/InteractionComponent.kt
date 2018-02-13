package com.reis.game.entity.components

import com.reis.game.entity.GameEntity
import com.reis.game.entity.ai.actions.Interact

/**
 * Created by bernardoreis on 2/11/18.
 */
class InteractionComponent(entity: GameEntity): EntityComponent(entity) {

    fun interact(target: GameEntity) {
        val action = Interact(target)
        entity.getComponent<EntityControllerComponent>(EntityControllerComponent::class.java)?.addAction(action)
    }
}