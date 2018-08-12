package com.reis.game.entity.ai.states.player

import com.reis.game.Main
import com.reis.game.entity.ai.controllers.AI

class DialogState(ai: AI): BasePlayerState(ai) {
    override fun executePrimaryAction() {
        // TODO change this way of accessing dialog manager
        val currentDialog = Main.getInstance().dialogManager.getDialogForEntity(ai.entity)
        currentDialog?.update()
    }
}