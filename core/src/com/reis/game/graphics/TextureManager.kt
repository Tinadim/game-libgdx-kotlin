package com.reis.game.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.reis.game.contants.GameConstants

/**
 * Created by berna on 18-Mar-18.
 */
class TextureManager {

    private val atlasCache: HashMap<String, TextureAtlas> = HashMap()

    fun getAtlas(atlasName: String): TextureAtlas {
        return atlasCache[atlasName] ?: loadAtlas(atlasName)
    }

    private fun loadAtlas(atlasName: String): TextureAtlas {
        val handle = Gdx.files.internal("${GameConstants.GFX_PATH}$atlasName")
        val atlas = TextureAtlas(handle)
        atlasCache[atlasName] = atlas
        return atlas
    }
}