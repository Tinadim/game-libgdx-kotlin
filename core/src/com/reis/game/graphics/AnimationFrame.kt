package com.reis.game.graphics

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion

/**
 * Created by berna on 18-Mar-18.
 */
data class AnimationFrame(
    private val atlasRegion: AtlasRegion,
    var frameDuration: Float = 0f,
    override var drawOffsetX: Float = 0f,
    override var drawOffsetY: Float = 0f): AtlasRegionWithOffset(atlasRegion)