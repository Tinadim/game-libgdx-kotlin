package com.reis.game.entity.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Batch
import com.reis.game.Main
import com.reis.game.entity.GameEntity
import com.reis.game.mechanics.collision.CollisionListener

/**
 * Created by bernardoreis on 12/25/17.
 */
abstract class EntityComponent(protected val entity:GameEntity) : Component {

    init {
        if (this is CollisionListener) {
            Main.getInstance().collisionManager.registerListener(entity, this)
        }
    }

    open fun update(delta: Float) {}

    open fun draw(batch: Batch, parentAlpha: Float) {}
}