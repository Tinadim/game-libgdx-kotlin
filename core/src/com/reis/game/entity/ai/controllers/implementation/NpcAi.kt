package com.reis.game.entity.ai.controllers.implementation

import com.reis.game.entity.GameEntity
import com.reis.game.entity.ai.controllers.AI
import com.reis.game.entity.ai.states.State
import com.reis.game.prototypes.AiProto.AiData

class NpcAi(entity: GameEntity, aiData: AiData): AI(entity, aiData) {

    override fun buildStateMachine(data: AiData): State {

    }
}