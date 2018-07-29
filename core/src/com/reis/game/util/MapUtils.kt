package com.reis.game.util

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Vector2
import com.reis.game.contants.GameConstants
import com.badlogic.gdx.math.Rectangle


/**
 * Created by bernardoreis on 1/6/18.
 */
object MapUtils {
    @JvmStatic
    fun getTileId(col: Int, row: Int): Int {
        // val map = SceneManager.getCurrentScene().getMap()
        // val tileWidth = map.getProperties().get("width", Int::class.java)
        var tileWidth = 40
        return row * tileWidth + col
    }

    @JvmStatic
    fun getTileForPosition(position: Vector2): Vector2 {
        val row = toTileCoord(position.y)
        val col = toTileCoord(position.x)
        return Vector2(col.toFloat(), row.toFloat())
    }

    @JvmStatic
    fun getPositionForTile(col: Int, row: Int): Vector2 {
        return Vector2(toPosition(col), toPosition(row))
    }

    @JvmStatic
    fun toTileCoord(coord: Float): Int {
        return coord.toInt() / GameConstants.TILE_SIZE
    }

    @JvmStatic
    fun toPosition(tileCoord: Int): Float {
        return (tileCoord * GameConstants.TILE_SIZE).toFloat()
    }

    @JvmStatic
    fun getMapTileWidth(map: TiledMap): Int {
        val prop = map.getProperties()
        return prop.get("width", Int::class.java)
    }

    @JvmStatic
    fun getMapTileHeight(map: TiledMap): Int {
        val prop = map.getProperties()
        return prop.get("height", Int::class.java)
    }

    @JvmStatic
    fun getMapWidth(map: TiledMap): Float {
        val prop = map.getProperties()
        return prop.get("tilewidth", Float::class.java) * getMapTileWidth(map)
    }

    @JvmStatic
    fun getMapHeight(map: TiledMap): Float {
        val prop = map.getProperties()
        return prop.get("tileheight", Float::class.java) * getMapTileHeight(map)
    }

    @JvmStatic
    fun getMapBounds(map: TiledMap): Rectangle {
        return Rectangle(0f, 0f, getMapWidth(map), getMapHeight(map))
    }
}