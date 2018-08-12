package com.reis.game.entity.ai.controllers

import com.reis.game.contants.AiConstants
import com.reis.game.entity.GameEntity
import com.reis.game.prototypes.AiProto.AiData

class AiFactory {
    companion object {
        @JvmStatic
        operator fun invoke(entity: GameEntity, data: AiData): AI {
            // TODO revisit this way of creating AI instances
            val className = "${AiConstants.AI_IMPLEMENTATION_PACKAGE}.${data.aiType}"
            val constructor = Class.forName(className)
                    .getConstructor(GameEntity::class.java)
            return constructor.newInstance(entity) as AI
        }
    }
}