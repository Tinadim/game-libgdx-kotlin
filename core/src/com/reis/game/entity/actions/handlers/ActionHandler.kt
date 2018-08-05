package com.reis.game.entity.actions.handlers

import com.reis.game.entity.GameEntity

/**
 * Created by bernardoreis on 2/11/18.
 */
abstract class ActionHandler(val entity: GameEntity) {

    abstract fun handle()
}