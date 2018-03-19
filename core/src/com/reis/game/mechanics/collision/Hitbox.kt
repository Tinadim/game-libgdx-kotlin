package com.reis.game.mechanics.collision

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.reis.game.contants.GameConstants
import com.reis.game.util.Axis
import com.reis.game.util.MapUtils

/**
 * Created by bernardoreis on 1/31/18.
 */
class Hitbox(var x: Float, var y: Float,
             var tileWidth: Int, var tileHeight: Int) {

    private var hotspots: ArrayList<Vector2>? = null
    private val shapeRenderer: ShapeRenderer = ShapeRenderer()

    fun getCenterX(): Float {
        return this.x + (this.tileWidth * GameConstants.TILE_SIZE * 0.5f)
    }

    fun getCenterY(): Float {
        return this.y + (this.tileHeight * GameConstants.TILE_SIZE * 0.5f)
    }

    fun invalidateHotspots() {
        this.hotspots = null
    }

    fun getHotspots(): List<Vector2> {
        var hotspots = this.hotspots
        if (hotspots != null) {
            return hotspots
        }

        hotspots = ArrayList(2 * (tileWidth + tileHeight))
        var x = this.x
        var y = this.y

        for (i in 0..tileWidth) {
            x += i * GameConstants.TILE_SIZE
            if (i == tileWidth) {
                x--
            }
            hotspots.add(Vector2(x, y))
        }
        for (i in 1..tileHeight) {
            y += i * GameConstants.TILE_SIZE
            if (i == tileHeight) {
                y--
            }
            hotspots.add(Vector2(x, y))
        }
        for (i in 1..tileWidth) {
            x -= i * GameConstants.TILE_SIZE
            if (i == tileWidth) {
                x++
            }
            hotspots.add(Vector2(x, y))
        }
        for (i in 1 until tileHeight) {
            y -= i * GameConstants.TILE_SIZE
            if (i == tileHeight) {
                y++
            }
            hotspots.add(Vector2(x, y))
        }
        this.hotspots = hotspots
        return hotspots
    }

    fun getHotspotsToTest(axis: Axis, side: Float): List<Vector2> {
        return if (axis == Axis.X) getHotspotsForXAxis(side) else getHotspotsForYAxis(side)
    }

    fun getHotspotsForXAxis(sign: Float): List<Vector2> {
        val minIndex = if (sign > 0) tileWidth else 2 * tileWidth + tileHeight
        val maxIndex = if (sign > 0) tileWidth + tileHeight else getHotspots().size
        return getHotspotsInRange(minIndex, maxIndex)
    }

    fun getHotspotsForYAxis(sign: Float): List<Vector2> {
        val minIndex = if (sign > 0) tileWidth + tileHeight else 0
        val maxIndex = if (sign > 0) 2 * tileWidth + tileHeight else tileWidth
        return getHotspotsInRange(minIndex, maxIndex)
    }

    private fun getHotspotsInRange(minIndex: Int, maxIndex: Int): List<Vector2> {
        val hotspots = getHotspots()
        val hotspotsToTest = ArrayList<Vector2>(maxIndex - minIndex)
        for (i in minIndex..maxIndex) {
            val hotspot = hotspots[i % hotspots.size]
            hotspotsToTest.add(Vector2(hotspot))
        }
        return hotspotsToTest
    }

    fun draw(batch: Batch, parentAlpha: Float) {
        val hotspots = getHotspots()
        batch.end()
        Gdx.gl.glEnable(GL20.GL_BLEND)
        shapeRenderer.setProjectionMatrix(batch.projectionMatrix)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.setColor(1f, 0f, 0f, 0.5f)
        hotspots.forEach {
            val tile = MapUtils.getTileForPosition(it)
            shapeRenderer.rect(tile.x * GameConstants.TILE_SIZE, tile.y * GameConstants.TILE_SIZE,
                    GameConstants.TILE_SIZE.toFloat(), GameConstants.TILE_SIZE.toFloat())
        }
        shapeRenderer.end()
        Gdx.gl.glDisable(GL20.GL_BLEND)
        batch.begin()
    }
}