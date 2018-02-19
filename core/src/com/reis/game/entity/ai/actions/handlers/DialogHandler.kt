package com.reis.game.entity.ai.actions.handlers

import com.reis.game.Main
import com.reis.game.entity.GameEntity

/**
 * Created by bernardoreis on 2/17/18.
 */
class DialogHandler(originator: GameEntity): ActionHandler(originator) {

    override fun handle() {
        val currentDialog = Main.getInstance().dialogManager.getDialogForEntity(this.entity)
        currentDialog?.update()
    }
}