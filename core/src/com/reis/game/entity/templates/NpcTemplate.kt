package com.reis.game.entity.templates

import com.reis.game.entity.GameEntity
import com.reis.game.entity.ai.controllers.AiFactory
import com.reis.game.entity.components.*
import com.reis.game.prototypes.EntityTypeProto

class NpcTemplate: EntityTemplate() {
    override fun apply(entity: GameEntity, data: EntityTypeProto.EntityData) {
        // TODO standardize component creation. To the controller, is passed the ai instance
        // but for the animation component, the raw data is passed
        val ai = AiFactory.invoke(entity, data.aiData)
        entity.addComponent(BodyComponent(entity))
        entity.addComponent(InteractionComponent(entity))
        entity.addComponent(AnimationComponent(entity, data.animationData))
        entity.addComponent(EntityControllerComponent(entity, ai))
        entity.addComponent(MovementComponent(entity))
    }
}