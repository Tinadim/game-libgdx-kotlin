package com.reis.game.graphics

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.reis.game.Main

/**
 * Created by berna on 18-Mar-18.
 */
class TextureManager {

    private val atlasCache: HashMap<String, TextureAtlas> = HashMap()

    fun getAtlas(atlasName: String): TextureAtlas {
        return atlasCache[atlasName] ?: loadAtlas(atlasName)
    }

    private fun loadAtlas(atlasName: String): TextureAtlas {
        // TODO change this way of accessing the resource manager
        val atlas = Main.getInstance().resourceManager.loadAtlas(atlasName)
        atlasCache[atlasName] = atlas
        return atlas
    }
}