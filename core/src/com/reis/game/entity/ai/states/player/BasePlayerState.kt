package com.reis.game.entity.ai.states.player

import com.reis.game.entity.ai.controllers.AI
import com.reis.game.entity.ai.states.State

abstract class BasePlayerState(ai: AI): State(ai) {

    abstract fun executePrimaryAction()
}