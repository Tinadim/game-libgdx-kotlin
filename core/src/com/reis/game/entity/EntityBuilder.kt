package com.reis.game.entity

import com.reis.game.prototypes.EntityTypeProto.EntityData

object EntityBuilder {
    @JvmStatic
    fun buildEntity(data: EntityData): GameEntity {
        return GameEntity(0)
    }
}