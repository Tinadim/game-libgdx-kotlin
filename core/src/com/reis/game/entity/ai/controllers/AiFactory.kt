package com.reis.game.entity.ai.controllers

import com.reis.game.entity.GameEntity
import com.reis.game.prototypes.AiProto.AiData

class AiFactory {
    companion object {
        @JvmStatic
        operator fun invoke(entity: GameEntity, data: AiData): AI {
            val className = data.aiType
            val constructor = Class.forName(className)
                    .getConstructor(GameEntity::class.java, AiData::class.java)
            return constructor.newInstance(entity, data) as AI
        }
    }
}