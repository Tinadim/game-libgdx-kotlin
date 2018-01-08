package com.reis.game.entity.components

import com.reis.game.entity.GameEntity
import com.reis.game.entity.ai.AI
import com.reis.game.prototypes.AiData

/**
 * Created by bernardoreis on 12/25/17.
 */
class AiComponent constructor(entity: GameEntity, aiData: AiData) : EntityComponent(entity) {

    private val ai: AI = AI.parse(aiData, entity)

    override fun update(delta: Float) {
        ai.update(delta)
    }
}