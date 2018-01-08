package com.reis.game.entity.player

import com.reis.game.entity.ai.actions.Action
import com.reis.game.entity.ai.actions.Idle

/**
 * Created by bernardoreis on 1/6/18.
 */
object ActionProcessor {

    // TODO implement pooling

    var action: Action = Idle()

    fun addAction(action: Action?) {
        this.action = action ?: Idle()
    }

    fun getNextAction(): Action {
        if (this.action.isFinished()) {
            this.action = Idle()
        }
        return this.action
    }
}