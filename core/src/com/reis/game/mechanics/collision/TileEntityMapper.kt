package com.reis.game.mechanics.collision

import com.reis.game.entity.GameEntity
import com.reis.game.util.MapUtils

/**
 * Created by bernardoreis on 1/6/18.
 */
object TileEntityMapper {

    private val entitiesPerTile:HashMap<Int, HashSet<GameEntity>?> = HashMap()

    fun addEntityToTile(entity: GameEntity, col: Int, row: Int) {
        val tileId = MapUtils.getTileId(col, row)
        val currentEntitiesInTile = getCurrentEntitiesInTile(tileId) ?: HashSet()
        currentEntitiesInTile.add(entity)
        entitiesPerTile.put(tileId, currentEntitiesInTile)
    }

    fun removeEntityFromTile(entity: GameEntity, col: Int, row: Int) {
        val tileId = MapUtils.getTileId(col, row)
        val currentEntitiesInTile: HashSet<GameEntity>? = entitiesPerTile[tileId] ?: return
        currentEntitiesInTile!!.remove(entity)

        if (currentEntitiesInTile.size == 0) {
            entitiesPerTile.remove(tileId)
        } else {
            entitiesPerTile.put(tileId, currentEntitiesInTile)
        }
    }

    fun getCurrentEntitiesInTile(tileId: Int): HashSet<GameEntity>? {
        return entitiesPerTile[tileId]
    }

    fun getCurrentEntitiesInTile(row: Int, col: Int): HashSet<GameEntity>? {
        val tileId = MapUtils.getTileId(col, row)
        return entitiesPerTile[tileId]
    }
}