package com.reis.game.resources

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import java.util.*
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.reis.game.prototypes.ScreenProto.ScreenData

/**
 * Created by bernardoreis on 2/16/18.
 */
class ResourceManager {
    companion object {
        const val IMAGE_PATH: String = "gfx/"
        const val SCENES_PATH: String = "scenes/"
        const val TMX_PATH: String = "${SCENES_PATH}maps/"
    }

    val dialogSkin: Skin = buildDialogSkin()
    val random: Random = Random()

    fun readFile(name: String): FileHandle {
        return Gdx.files.internal(name)
    }

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

    fun loadScreenData(name: String): ScreenData {
        val handle = readFile("$SCENES_PATH$name")
        val stream = handle.read()
        return ScreenData.parseFrom(stream)
    }

    fun loadMap(name: String): TiledMap {
        return TmxMapLoader().load(SCENES_PATH + name)
    }

    fun loadAtlas(name: String): TextureAtlas {
        val handle = Gdx.files.internal("$IMAGE_PATH$name")
        return TextureAtlas(handle)
    }
}