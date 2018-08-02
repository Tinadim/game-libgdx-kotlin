package com.reis.game.entity.templates

import com.reis.game.entity.GameEntity
import com.reis.game.prototypes.EntityTypeProto.EntityData

abstract class EntityTemplate {

    abstract fun apply(entity: GameEntity, data: EntityData)
}