package com.reis.game.util

import com.badlogic.gdx.math.Vector2
import com.reis.game.contants.GameConstants

/**
 * Created by bernardoreis on 1/6/18.
 */
object MapUtils {

    fun getTileId(col: Int, row: Int): Int {
        // val map = SceneManager.getCurrentScene().getMap()
        // val tileWidth = map.getProperties().get("width", Int::class.java)
        var tileWidth = 40
        return row * tileWidth + col
    }

    fun getTileForPosition(position: Vector2): Vector2 {
        val row = toTileCoord(position.y)
        val col = toTileCoord(position.x)
        return Vector2(col.toFloat(), row.toFloat())
    }

    fun toTileCoord(coord: Float): Int {
        return coord.toInt() / GameConstants.TILE_SIZE
    }
}