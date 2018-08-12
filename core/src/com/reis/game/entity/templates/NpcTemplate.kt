package com.reis.game.entity.templates

import com.reis.game.entity.GameEntity
import com.reis.game.entity.components.*
import com.reis.game.prototypes.EntityTypeProto

class NpcTemplate: EntityTemplate() {
    override fun apply(entity: GameEntity, data: EntityTypeProto.EntityData) {
        entity.addComponent(BodyComponent(entity))
        entity.addComponent(InteractionComponent(entity))
        entity.addComponent(AnimationComponent(entity, data.animationData))
        entity.addComponent(EntityControllerComponent(entity, data.aiData))
        entity.addComponent(MovementComponent(entity))
    }
}