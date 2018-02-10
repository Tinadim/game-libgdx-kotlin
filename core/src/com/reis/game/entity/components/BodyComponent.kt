package com.reis.game.entity.components

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.reis.game.entity.GameEntity
import com.reis.game.mechanics.collision.*

/**
 * Created by bernardoreis on 1/6/18.
 */
class BodyComponent(entity: GameEntity): EntityComponent(entity) {

    var isCollidable: Boolean = true
    var isTrigger: Boolean = false
    val hitbox: Hitbox = Hitbox(entity.x, entity.y, entity.getTileWidth(), entity.getTileHeight())

    var collisionType: CollisionType = CollisionType.ACTIVE
    var collisionListener: CollisionListener? = null

    constructor(entity: GameEntity, collisionListener: CollisionListener? = null): this(entity) {
        this.collisionListener = collisionListener
    }

    constructor(entity: GameEntity, collisionType: CollisionType = CollisionType.ACTIVE,
                collisionListener: CollisionListener? = null): this(entity, collisionListener) {
        this.collisionType = collisionType
    }

    override fun update(delta: Float) {
        super.update(delta)
        if (this.collisionType == CollisionType.PASSIVE) {
            checkCollision(this.entity)
        }
    }

    fun checkCollision(entity: GameEntity) {
        val results = CollisionDetector.checkCollision(entity)
        results.collisions?.forEach { CollisionManager.registerCollision(it) }
    }

    fun checkCollision(distance: Vector2): CollisionResults {
        // TODO verify if active collision type can be removed
        val results = CollisionDetector(this.entity, distance).test()
        results.collisions?.forEach { CollisionManager.registerCollision(it) }
        return results
    }

    fun shouldIgnore(entity: GameEntity): Boolean {
        // TODO implement should ignore
        return false
    }

    fun onPositionChanged() {
        this.hitbox.invalidateHotspots()
        this.hitbox.x = this.entity.x
        this.hitbox.y = this.entity.y
        this.bindTiles()
    }

    fun onSizeChanged() {
        this.hitbox.invalidateHotspots()
        this.hitbox.tileWidth = this.entity.getTileWidth()
        this.hitbox.tileHeight = this.entity.getTileHeight()
        this.bindTiles()
    }

    fun unbindTiles() {
        iterateHotspots({
            entity, col, row -> TileEntityMapper.removeEntityFromTile(entity, row, col)
        })
    }

    fun bindTiles() {
        // TODO this check is not good enough. Need a better way of verifying if the entity
        // was already added to the scene
        if (entity.stage != null) {
            iterateHotspots({ entity, col, row ->
                TileEntityMapper.addEntityToTile(entity, row, col)
            })
        }
    }

    private fun iterateHotspots(iterator: (GameEntity, Int, Int) -> Unit) {
        for (i in 0..entity.getTileWidth()) {
            for (j in 0..entity.getTileHeight()) {
                val col = entity.getCol() + i
                val row = entity.getRow() + j
                iterator(entity, col, row)
            }
        }
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        this.hitbox.draw(batch, parentAlpha)
    }
}