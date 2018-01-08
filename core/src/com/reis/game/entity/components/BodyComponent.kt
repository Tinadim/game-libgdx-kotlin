package com.reis.game.entity.components

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.reis.game.contants.GameConstants
import com.reis.game.contants.GameConstants.TILE_SIZE
import com.reis.game.entity.GameEntity
import com.reis.game.mechanics.collision.CollisionDetector
import com.reis.game.mechanics.collision.CollisionManager
import com.reis.game.mechanics.collision.CollisionResults
import com.reis.game.mechanics.collision.TileEntityMapper
import com.reis.game.util.Axis
import com.reis.game.util.MapUtils

/**
 * Created by bernardoreis on 1/6/18.
 */
class BodyComponent(entity: GameEntity): EntityComponent(entity) {

    private var collidable: Boolean = true
    private var hotspots: ArrayList<Vector2>? = null

    private val shapeRenderer: ShapeRenderer = ShapeRenderer()

    fun checkCollision(distance: Vector2): CollisionResults {
        val results = CollisionDetector(this.entity, distance).test()
        results.collisions?.forEach { CollisionManager.registerCollision(it) }
        return results
    }

    fun shouldIgnore(entity: GameEntity): Boolean {
        return false
    }

    fun isCollidable(): Boolean {
        return this.collidable
    }

    fun invalidateHotspots() {
        this.hotspots = null
        this.bindTiles()
    }

    fun getHotspots(): List<Vector2> {
        var hotspots = this.hotspots
        if (hotspots != null) {
            return hotspots
        }

        val tileWidth = entity.getTileWidth()
        val tileHeight = entity.getTileHeight()

        hotspots = ArrayList(2 * (tileWidth + tileHeight))
        var x = entity.x
        var y = entity.y

        for (i in 0..tileWidth) {
            x += (i * TILE_SIZE).toFloat()
            if (i == tileWidth) {
                x--
            }
            hotspots.add(Vector2(x, y))
        }
        for (i in 1..tileHeight) {
            y += (i * TILE_SIZE).toFloat()
            if (i == tileHeight) {
                y--
            }
            hotspots.add(Vector2(x, y))
        }
        for (i in 1..tileWidth) {
            x -= (i * TILE_SIZE).toFloat()
            if (i == tileWidth) {
                x++
            }
            hotspots.add(Vector2(x, y))
        }
        for (i in 1 until tileHeight) {
            y -= (i * TILE_SIZE).toFloat()
            if (i == tileHeight) {
                y++
            }
            hotspots.add(Vector2(x, y))
        }
        return hotspots
    }

    fun getHotspotsToTest(axis: Axis, side: Float): List<Vector2> {
        return if (axis == Axis.X) getHotspotsForXAxis(side) else getHotspotsForYAxis(side)
    }

    fun getHotspotsForXAxis(sign: Float): List<Vector2> {
        val minIndex = if (sign > 0) entity.getTileWidth() else 2 * entity.getTileWidth() + entity.getTileHeight()
        val maxIndex = if (sign > 0) entity.getTileWidth() + entity.getTileHeight() else getHotspots().size
        return getHotspotsInRange(minIndex, maxIndex)
    }

    fun getHotspotsForYAxis(sign: Float): List<Vector2> {
        val minIndex = if (sign > 0) entity.getTileWidth() + entity.getTileHeight() else 0
        val maxIndex = if (sign > 0) 2 * entity.getTileWidth() + entity.getTileHeight() else entity.getTileWidth()
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

    fun unbindTiles() {
        iterateHotspots({
            entity, col, row -> TileEntityMapper.removeEntityFromTile(entity, row, col)
        })
    }

    fun bindTiles() {
        iterateHotspots({
            entity, col, row -> TileEntityMapper.addEntityToTile(entity, row, col)
        })
    }

    private fun iterateHotspots(iterator: (GameEntity, Int, Int) -> Unit) {
        val hotspots = getHotspots()
        hotspots.forEach {
            val row = MapUtils.toTileCoord(it.y)
            val col = MapUtils.toTileCoord(it.x)
            iterator(entity, col, row)
        }
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        val hotspots = getHotspots()
        hotspots.forEach {
            val tile = MapUtils.getTileForPosition(it)
            batch.end()
            Gdx.gl.glEnable(GL20.GL_BLEND)
            shapeRenderer.setProjectionMatrix(batch.projectionMatrix)
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
            shapeRenderer.setColor(1f, 0f, 0f, 0.5f)
            shapeRenderer.rect(tile.x * GameConstants.TILE_SIZE, tile.y * GameConstants.TILE_SIZE,
                    GameConstants.TILE_SIZE.toFloat(), GameConstants.TILE_SIZE.toFloat())
            shapeRenderer.end()
            Gdx.gl.glDisable(GL20.GL_BLEND)
            batch.begin()
        }
    }
}