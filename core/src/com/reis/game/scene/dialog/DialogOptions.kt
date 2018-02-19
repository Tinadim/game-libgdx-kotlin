package com.reis.game.scene.dialog

import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.Align
import com.reis.game.Main
import com.reis.game.contants.DialogConstants

/**
 * Created by bernardoreis on 2/14/18.
 */
class DialogOptions {
    var skin: Skin = Main.getInstance().resourceManager.dialogSkin // TODO fix this
    var width: Int = DialogConstants.DEFAULT_DIALOG_WIDTH
    var height: Int = DialogConstants.DEFAULT_DIALOG_HEIGHT
    var verticalAlignment: Int = Align.top
    var horizontalAlignment: Int = Align.left

    // TODO create builder
}