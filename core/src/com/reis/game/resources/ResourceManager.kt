package com.reis.game.resources

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin

/**
 * Created by bernardoreis on 2/16/18.
 */
class ResourceManager {

    companion object {
        const val IMAGE_PATH: String = "gfx/"
    }

    val dialogSkin: Skin = buildDialogSkin()

    private fun buildDialogSkin(): Skin {
        val dialogSkin = Skin()
        val patch = NinePatch(getDialogBackground(), 8, 8, 8, 8)
        patch.setPadding(0f, 0f, 0f, 0f)
        dialogSkin.add("background", patch)

        val generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/roboto.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.size = 12
        val font12 = generator.generateFont(parameter)
        generator.dispose()

        val labelStyle = Label.LabelStyle()
        labelStyle.fontColor = Color.BLACK
        labelStyle.font = font12

        dialogSkin.add("default", labelStyle)
        return dialogSkin
    }

    private fun getDialogBackground(): Texture {
        return Texture(Gdx.files.internal(IMAGE_PATH + "dialog-background.png"))
    }
}