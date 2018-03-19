package com.reis.game.graphics

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.math.Vector2

/**
 * Created by berna on 18-Mar-18.
 */
open class AtlasRegionWithOffset(region:AtlasRegion): AtlasRegion(region) {

    var drawOffset: Vector2 = Vector2.Zero
}