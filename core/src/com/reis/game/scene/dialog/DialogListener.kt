package com.reis.game.scene.dialog

/**
 * Created by bernardoreis on 2/15/18.
 */
interface DialogListener {

    fun onDialogEnded(dialog: DialogWindow)

    fun onDialogStarted(dialog: DialogWindow)
}