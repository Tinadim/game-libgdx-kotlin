package com.reis.game.entity

import com.reis.game.Main
import com.reis.game.prototypes.EntityTypeProto.EntityData


class EntityBuilder {
    // TODO change this way of accessing the template cache
    private val templateCache: EntityTemplateCache = Main.getInstance().templateCache

    @Throws(Exception::class)
    fun build(entityData: EntityData): GameEntity {
        val template = templateCache.getEntityTemplate(entityData.templateName)
        val entity = GameEntity(entityData.id)
        entity.setCoordinates(entityData.row, entityData.col)
        entity.setTileSize(entityData.width, entityData.height)
        template.apply(entity, entityData)
        return entity
    }
}